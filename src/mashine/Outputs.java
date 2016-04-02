/**
 *  Mother class for all outputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import java.util.HashMap;
import mashine.outputs.*;
import mashine.scene.*;

public class Outputs{

	private HashMap<String,Output> outputs;
	private Frame frame;

	public Outputs(){
		outputs = new HashMap<String,Output>();
		outputs.put("OLA", new Ola());
	}

	public void push(){
		if(null != frame){
			for(Output o : outputs.values()){
				o.push(frame);
			}
			frame = null;
		}
	}

	public void setFrame(Frame frame){
		if (null == this.frame) {
			this.frame = frame;
		}
	}

}