/**
 *  Output instance for Open Lighting Architecture APIS
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.outputs;

import mashine.*;
import ola.OlaClient;
import ola.proto.Ola.DmxData;
import ola.proto.Ola.PluginListReply;
import ola.proto.Ola.UniverseInfo;

import java.util.HashMap; 
import java.util.List; 

public class Ola extends Output{

	private OlaClient ola;

	public Ola(MaShine m){

		super(m);
		//connectToServer();
	}

	public void push(){
		if(ola != null){

			for(String k : ports.values())
				M.println(k);

		}else{
			if(M.frameCount % 120 == 0)
				connectToServer();
		}
	}

	private void connectToServer(){
		try{
			ola = new OlaClient();
			M.inputs.setState("internal.ola.status", true);
			loadUniverses();
		}catch (Exception e) {
			ola = null;
			M.inputs.setState("internal.ola.status", false);
			M.ui.status.set("OLA", "disconnected");
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
		M.ui.status.set("OLA", ports.size()+ " universes");
	}
}