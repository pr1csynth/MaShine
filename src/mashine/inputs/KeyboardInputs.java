/**
 *  Handle keyboard inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import mashine.MaShine;
import processing.event.KeyEvent;

public class KeyboardInputs extends InputSource implements Learnable{

	public String lastTyped;
	private String lastState;

	public KeyboardInputs () {
		super();
	}

	public void keyEvent(KeyEvent e){
		if(e.getAction() == KeyEvent.PRESS){
			states.put("keyboard."+e.getKeyCode()+".press" , true);
			states.put("keyboard."+e.getKeyCode()+".hold" , true);
			lastTyped = "" + MaShine.m.key;
			lastState = "keyboard."+e.getKeyCode()+".press";
		}else{
			states.put("keyboard."+e.getKeyCode()+".release" , true);
			states.put("keyboard."+e.getKeyCode()+".hold" , false);
			lastState = "keyboard."+e.getKeyCode()+".release";
		}
	}

	public void clear() {
		for(String s : states.keySet()){
			if(!s.contains("hold"))
			states.put(s, false);
		}

		lastTyped = "";
		lastState = null;
	}

	public String getLastState(){return lastState;}
	public String getLastRange(){return null;}

}