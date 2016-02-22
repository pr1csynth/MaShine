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

	public Element (Drawable parent, int x,  int y, int w, int h) {
		super(parent.M);
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		P = parent;
	}

	public void draw(){
		drawContent();
	}

	public void drawContent(){}

	/* Override Focusable.mouseIn() */
	public boolean mouseIn(){
		return (M.inputs.getState("mouse.left.hold") && focus && P.hasFocus()) || (
				P.mouseX() >= x &&
				P.mouseX() < x + width &&
				P.mouseY() >= x &&
				P.mouseY() < y + height);
	}

	public boolean isDragged() {
		return M.inputs.getState("mouse.left.hold") && mouseIn() && P.hasFocus();
	}

	public boolean isClicked() {
		return M.inputs.getState("mouse.left.press") && mouseIn() && P.hasFocus();
	}

	public boolean isHovered() {
		return mouseIn() && P.hasFocus();
	}


}