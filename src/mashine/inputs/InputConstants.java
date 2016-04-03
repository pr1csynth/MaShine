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
	}
}