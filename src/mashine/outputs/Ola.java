/**
 *  Output instance for Open Lighting Architecture APIS
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.outputs;

import java.util.HashMap;
import java.util.List;

import mashine.MaShine;
import mashine.Do;
import mashine.scene.Device;
import mashine.scene.Frame;
import mashine.scene.features.EditableFeature;
import mashine.scene.features.Feature;
import ola.OlaClient;
import ola.proto.Ola.UniverseInfo;
import ola.proto.Ola.DmxData;

public class Ola extends Output{

	private OlaClient ola;
	private Integer inputUniverse;
	private boolean init = false;

	public Ola(){
		//connectToServer();
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
				MaShine.inputs.setState("internal.ola.status", false);
				MaShine.ui.status.set("OLA", "disconnected");
			}

			//testReceive(3);

		}else{
			if(!init){
				init = true;
				MaShine.inputs.registerAction("mashine.outputs.reload_universes", new Do(){public void x(){connectToServer();}});
			}
			if(MaShine.m.frameCount % 120 == 0)
				connectToServer();
		}
	}

	private void connectToServer(){
		try{
			ola = new OlaClient();
			MaShine.inputs.setState("internal.ola.status", true);
			loadUniverses();
		}catch (Exception e) {
			ola = null;
			MaShine.inputs.setState("internal.ola.status", false);
			MaShine.ui.status.set("OLA", "disconnected");
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
					if(u.getInputPortCount()>0)
						inputUniverse = u.getUniverse();
				}
			}catch (Exception e) {}
		}
		MaShine.ui.status.set("OLA", ports.size()+ " universes");
	}

	public short[] getDmx(){
		if(inputUniverse == null){
			return null;
		}
		DmxData r = ola.getDmx(inputUniverse);
		return ola.convertFromUnsigned(r.getData());
	}
}