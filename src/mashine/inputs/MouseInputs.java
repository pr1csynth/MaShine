/**
 *  Handle keyboard inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import mashine.*;
import processing.event.*;

public class MouseInputs extends InputSource{

	private int lastClickX = 0;
	private int lastClickY = 0;

	public MouseInputs (MaShine m) {
		super(m);
	}

	public void mouseEvent(MouseEvent e){
		String button = "?";
		if(e.getButton() == M.LEFT){
			button = "left";
		}else if(e.getButton() == M.RIGHT){
			button = "right";
		}else if(e.getButton() == M.CENTER){
			button = "center";
		}

		if(e.getAction() == e.RELEASE){
			states.put("mouse."+ button + ".release", true);
			states.put("mouse."+ button + ".hold", false);
			states.put("mouse."+ button + ".drag", false);
		}else if(e.getAction() == e.PRESS){
			states.put("mouse."+ button + ".press", true);
			states.put("mouse."+ button + ".hold", true);
			lastClickY = e.getY();
			lastClickX = e.getX();
		}else if(e.getAction() == e.WHEEL){
			if(e.getCount() > 0){
				states.put("mouse.wheel.encoder.on", true);
			}else{
				states.put("mouse.wheel.encoder.off", true);
			}
		}


	}

	public void clear() {
		for(String s : states.keySet()){
			if(!s.contains("hold") && !s.contains("drag"))
				states.put(s, false);
		}
	}

	public void tick(){
		if(M.inputs.getState("mouse.left.hold") && (lastClickX != M.mouseX || lastClickY != M.mouseY)){
			states.put("mouse.left.drag", true);
			// lastClickY = e.getY();
			// lastClickX = e.getY();
		}else if (!M.inputs.getState("mouse.left.hold")) {
			states.put("mouse.left.drag", false);
		}
	}
}