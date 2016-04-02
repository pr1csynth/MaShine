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

	public MouseInputs () {
		super();
	}

	public void mouseEvent(MouseEvent e){
		String button = "?";
		if(e.getButton() == MaShine.LEFT){
			button = "left";
		}else if(e.getButton() == MaShine.RIGHT){
			button = "right";
		}else if(e.getButton() == MaShine.CENTER){
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
		if(MaShine.inputs.getState("mouse.left.hold") && (lastClickX != MaShine.m.mouseX || lastClickY != MaShine.m.mouseY)){
			states.put("mouse.left.drag", true);
			// lastClickY = e.getY();
			// lastClickX = e.getY();
		}else if (!MaShine.inputs.getState("mouse.left.hold")) {
			states.put("mouse.left.drag", false);
		}
	}
}