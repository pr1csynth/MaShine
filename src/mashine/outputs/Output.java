/**
 *  Mother class for outputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.outputs;

import java.util.HashMap;

import mashine.scene.Frame;

public class Output{

	protected HashMap<Integer,String> ports;

	public Output(){
	}

	public void push(Frame frame){}

	public HashMap<Integer,String> getPorts(){
		return ports;
	}
}