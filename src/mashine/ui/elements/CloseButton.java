/**
 *  A button that close a UIBox
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;
import mashine.utils.*;

public class CloseButton extends Button{

	protected UIBox P;

	public CloseButton (final UIBox parent) {
		// UIREFORM
		super(parent, parent.width-22, 0, 22, 22, new Do(){public void x(){MaShine.ui.close(parent);}});
		P = parent;
	}

	public void drawContent(){
		super.drawContent();
		P.canvas.noStroke();
		
		if(mouseIn()){
			FlatColor.stroke(P.canvas,Colors.MATERIAL.RED._800);
		}else{
			FlatColor.stroke(P.canvas,Colors.MATERIAL.BLUE_GREY._600);
		}

		P.canvas.noFill();
		P.canvas.line(P.width - 16, 6, P.width - 6, 16);
		P.canvas.line(P.width - 6, 6, P.width - 16, 16);
	}
}