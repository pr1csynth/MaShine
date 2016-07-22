/**
 *  Mother class for all inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import java.util.HashMap;

public class InputConstants extends InputSource {

	public InputConstants () {
		super();
		states.put("_false", false);
		states.put("_true", true);
		ranges.put("_100",1.0);
		ranges.put("_75",0.75);
		ranges.put("_50",0.5);
		ranges.put("_25",0.25);
		ranges.put("_0",0.0);
	}
}