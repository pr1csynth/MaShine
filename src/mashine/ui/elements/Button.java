/**
 *  A button element
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;
import mashine.utils.*;

public class Button extends Element{

	private Do action;
	private boolean actionOnRelease = false;

	public Button (Drawable parent, int x,  int y, int w, int h, Do action) {
		super(parent, x, y, w, h);
		this.action = action;
	}	
	public Button (Drawable parent, int x,  int y, int w, int h, Do action, boolean actionOnRelease) {
		super(parent, x, y, w, h);
		this.action = action;
		this.actionOnRelease = actionOnRelease;
	}

	public void drawContent(){
		if(!actionOnRelease && isClicked()){
			action.x();
		}else if(actionOnRelease && isReleased()){
			action.x();
		}
	}
}