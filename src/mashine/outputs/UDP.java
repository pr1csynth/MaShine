/**
 *  Output to Internet
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.outputs;

import java.io.*;
import java.net.*;
import java.util.*;

import mashine.MaShine;
import mashine.scene.Frame;

public class UDP extends Output {

	private InetAddress address;
	private DatagramSocket socket;

	public UDP() {
		try{
			socket = new DatagramSocket() ;
			address = InetAddress.getByName("224.0.0.1");
		}catch (Exception e) {e.printStackTrace();}
	}

	public void push(Frame frame){
		if(socket != null){	
			try{
				byte[] buf = new byte[4096];
				String dString = "";
				for(String s : MaShine.inputs.getStateSet()){
					dString += "S:"+ s +":"+ (MaShine.inputs.getState(s) ? "1" : "0") +"\n";
				}
				buf = dString.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1703);
				socket.send(packet);
			}catch (Exception e) {e.printStackTrace();}
		}
	}
}
