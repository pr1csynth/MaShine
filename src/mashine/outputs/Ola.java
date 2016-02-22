/**
 *  Output instance for Open Lighting Architecture APIS
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.outputs;

import mashine.*;
import ola.OlaClient;

public class Ola extends Output{

	private OlaClient ola;

	public Ola(MaShine m){

		super(m);
		connectToServer();

	}

	public void push(){
		if(ola != null){

		}else{
			if(M.frameCount % 120 == 0)
				connectToServer();
		}
	}

	private void connectToServer(){
		try{
			ola = new OlaClient();
			M.inputs.setState("internal.ola.status", true);
		}catch (Exception e) {
			ola = null;
			M.inputs.setState("internal.ola.status", false);
		}
	}
}