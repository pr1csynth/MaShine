/**
 *  Mother class focusable ui elements (UIBox, Element)
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import mashine.*;

public abstract class Focusable{

	protected boolean focus = false;

	public Focusable(){
	}

	public boolean mouseIn(){
		return false;
	}

	public void focus() {
		focus = true;
		onFocus();
	}
	public void defocus() {
		focus = false;
		onDefocus();
	}

	public boolean hasFocus(){
		return focus;
	}

	public void draw(){}
	public void tick(){}

	protected void onFocus() {}
	protected void onDefocus() {}
}