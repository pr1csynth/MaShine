/**
 *  Move an UIBox arround
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;

public class Grabber extends Element {

	protected UIBox P;

	public Grabber (UIBox parent) {
		super(parent, 0, 0, parent.width-22, 22);
		P = parent;
	}

	public void drawContent(){

		FlatColor.stroke(P.canvas,Colors.MATERIAL.BLUE_GREY._600);
		P.canvas.line( 6,  6, 18, 6);
		P.canvas.line( 6, 10, 18, 10);
		P.canvas.line( 6, 14, 18, 14);

		if(isDragged()){
			// TODO, no induced move
			P.moveBox(M.mouseX - P.width/2, M.mouseY - 11 );
		}
	}
}