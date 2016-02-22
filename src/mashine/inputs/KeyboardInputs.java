/**
 *  Handle keyboard inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import mashine.*;
import processing.event.*;
import java.util.HashMap;

public class KeyboardInputs extends InputSource{

	public String lastTyped;


	public KeyboardInputs (MaShine m) {
		super(m);
	}

	public void keyEvent(KeyEvent e){
		if(e.getAction() == KeyEvent.PRESS){
			states.put("keyboard."+e.getKeyCode()+".press" , true);
			states.put("keyboard."+e.getKeyCode()+".hold" , true);
			lastTyped = "" + M.key;
			M.println(M.key + " -> " + e.getKeyCode());
		}else{
			states.put("keyboard."+e.getKeyCode()+".release" , true);
			states.put("keyboard."+e.getKeyCode()+".hold" , false);
		}
	}

	public void clear() {
		for(String s : states.keySet()){
			if(!s.contains("hold"))
			states.put(s, false);
		}

		lastTyped = "";
	}

}