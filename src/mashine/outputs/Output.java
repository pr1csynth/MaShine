/**
 *  Mother class for outputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.outputs;

import mashine.*;
import mashine.scene.*;
import java.util.HashMap; 

public class Output{

	protected MaShine M;
	protected HashMap<Integer,String> ports;

	public Output(MaShine m){
		M = m;
	}

	public void push(Frame frame){}

	public HashMap<Integer,String> getPorts(){
		return ports;
	}
}