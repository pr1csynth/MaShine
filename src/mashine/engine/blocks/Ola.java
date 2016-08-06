/**
 *  Output instance for Open Lighting Architecture APIS
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import mashine.MaShine;
import mashine.engine.*;
import mashine.utils.Do;
import mashine.scene.Device;
import mashine.scene.Frame;
import mashine.scene.features.EditableFeature;
import mashine.scene.features.Feature;
import ola.OlaClient;
import ola.proto.Ola.UniverseInfo;
import ola.proto.Ola.DmxData;

public class Ola extends Block{

	private OlaClient ola;
	private Map<Integer,String> ports;

	public Ola(){
		//connectToServer();
		ports = new HashMap<Integer,String>();
		nodes();
	}

	private void nodes(){
		/* controls in 	//	controls out
		- reload			- connected
				 			- universes

		/* content in 	//	content out
		- frame 			- frame
		
		*/

		controlIn.put("reload", 	new InNode(false));

		contentIn.put("frame", 		new InNode(new Frame()));

		controlOut.put("connected",	new OutNode(this, false));
		controlOut.put("universes",	new OutNode(this, 0));

		contentOut.put("frame", 	new OutNode(this, new Frame()));
	}

	public void tick(){
		Frame f = (Frame)get("frame");
		set("frame", f);
		push(f);
		if((Boolean)get("reload")){
			loadUniverses();
		}
	}

	public void push(Frame frame){
		if(ola != null){
			HashMap<Integer,short[]> dmxData = new HashMap<Integer,short[]>();
			for(int u : ports.keySet())
				dmxData.put(u, new short[512]);

			List<Device> devices = MaShine.scene.getDevices();

			// build arrays

			for(Device d : devices){
				int u = d.getUniverse();

				if(dmxData.containsKey(u)){
					int i = d.getStartAddress();

					for(Feature f : d.getFeatures()){
						// look in the frame
						if(f instanceof EditableFeature){
							Feature ff = frame.getFeature(d, f);
							if(null == ff){
								for(int j = 0; j < f.getFootprint(); j++){
									dmxData.get(u)[i-1] = 0;
									i++;
								}
							}else{
								for(short v : ff.toArray()){
									dmxData.get(u)[i-1] = v;
									i++;
								}
							}
						// look in the device
						}else{
							for(short v : f.toArray()){
								dmxData.get(u)[i-1] = v;
								i++;
							}
						}
					}
				}
			}

			// send array to OLA
			try{			
				for(Integer u : dmxData.keySet()){
					ola.streamDmx(u, dmxData.get(u));
				}
			}catch(Exception e){
				ola = null;
				set("connected", false);
			}

			//testReceive(3);

		}else{
			if(MaShine.m.frameCount % 120 == 0){
				connectToServer();
			}
		}
	}

	private void connectToServer(){
		try{
			ola = new OlaClient();
			set("connected", true);
			loadUniverses();
		}catch (Exception e) {
			ola = null;
			set("connected", false);
		}
	}

	private void loadUniverses(){
		ports = new HashMap<Integer,String>();
		for(int i = 0; i < 64; i ++){
			try{
				List<UniverseInfo> r = ola.getUniverseInfo(i).getUniverseList();
				for(UniverseInfo u : r){
					if(u.getOutputPortCount()>0)
						ports.put(u.getUniverse(), u.getName());
				}
			}catch (Exception e) {}
		}
		set("universes", ports.size());
	}
}