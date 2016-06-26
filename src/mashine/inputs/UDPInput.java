/**
 *  Inputs from Internet
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import java.io.*;
import java.net.*;
import java.util.*;

import mashine.MaShine;

public class UDPInput extends InputSource {

	public UDPInput() {
		super();
		try{
			new ReceiveThread().start();
			MaShine.println("LISTENING ON UDP 224.0.0.1:1703");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	public class ReceiveThread extends Thread {

		InetAddress group;

		protected MulticastSocket socket = null;

		public ReceiveThread() throws IOException {
			this("UDPInput");
		}

		public ReceiveThread(String name) throws IOException {
			super(name);
			socket = new MulticastSocket(1703);
			group = InetAddress.getByName("224.0.0.1");
			socket.joinGroup(group);
		}

		public void run() {
			DatagramPacket packet;
			Boolean running = true;
			while (running) {
				try{
					byte[] buf = new byte[256];
					packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					String received = new String(packet.getData());
					Map<String, Boolean> newStates = parseStates(received);
					Map<String, Double> newRanges = parseRanges(received);
					synchronized(states){states.putAll(newStates);}
					synchronized(ranges){ranges.putAll(newRanges);}
				}catch(IOException e){
					running = false;
					e.printStackTrace();
					
					try{
						socket.leaveGroup(group);
						socket.close();
					}catch(Exception ignore){}
				}
			}
		}
	}

	public Map<String,Boolean> parseStates(String data){
		String[] d = data.split("\n");
		Map<String,Boolean> m = new HashMap<String,Boolean>();
		for(String s : d){
			String[] l = s.split(":");
			if(l.length == 3 && l[0].equals("s")){
				m.put("udp."+ l[1], l[2].equals("1"));
			}
		}
		return m;
	}
	public Map<String,Double> parseRanges(String data){
		String[] d = data.split("\n");
		Map<String,Double> m = new HashMap<String,Double>();
		for(String s : d){
			try{
				String[] l = s.split(":");
				if(l.length == 3 && l[0].equals("r")){
					m.put("udp."+ l[1], new Double(l[2]));
				}
			}catch (Exception ignore) {}
		}
		return m;
	}
}