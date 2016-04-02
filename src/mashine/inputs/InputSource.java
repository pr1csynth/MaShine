/**
 *  Mother class for all inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import mashine.*;
import java.util.HashMap; 

public class InputSource {

	protected HashMap<String,Boolean> states;
	protected HashMap<String,Double> ranges;

	public InputSource () {
		states = new HashMap<String,Boolean>();
		ranges = new HashMap<String,Double>();
	}

	public void tick(){}
	public void clear(){}

	public HashMap<String,Boolean> pollStates() {
		return states;
	}

	public HashMap<String,Double> pollRanges() {
		return ranges;
	}

}