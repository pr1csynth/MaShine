/**
 *  A button element
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;

public class Button extends Element{

	private Do action;

	public Button (Drawable parent, int x,  int y, int w, int h, Do action) {
		super(parent, x, y, w, h);
		this.action = action;
	}

	public void drawContent(){
		if(isClicked()){
			action.x();
		}
	}
}