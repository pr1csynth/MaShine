/**
 *  Mother class UI elements
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import mashine.*;

public class Element extends Focusable{

	protected Drawable P;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean enabled = true;

	public Element (Drawable parent, int x,  int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		P = parent;
	}

	public void draw(){
		drawContent();
	}

	public void disable(){
		enabled = false;
	}

	public void enable(){
		enabled = true;
	}

	public void drawContent(){}

	/* Override Focusable.mouseIn() */
	public boolean mouseIn(){
		return enabled && (MaShine.inputs.getState("mouse.left.hold") && focus && P.hasFocus()) || (
				P.mouseX() >= x &&
				P.mouseX() < x + width &&
				P.mouseY() >= y &&
				P.mouseY() < y + height);
	}

	public boolean isDragged() {
		return MaShine.inputs.getState("mouse.left.drag") && mouseIn() && P.hasFocus();
	}

	public boolean isClicked() {
		return MaShine.inputs.getState("mouse.left.press") && mouseIn() && P.hasFocus();
	}
	public boolean isReleased() {
		return MaShine.inputs.getState("mouse.left.release") && mouseIn() && P.hasFocus();
	}

	public boolean isHovered() {
		return mouseIn() && P.hasFocus();
	}
	
	public boolean isEnabled() {
		return enabled;
	}


}