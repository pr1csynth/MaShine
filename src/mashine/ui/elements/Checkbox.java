/**
 *  A checkbox element
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;

public class Checkbox extends Element{

	boolean state = false;

	private FlatColor on;
	private FlatColor off;
	private FlatColor hover;

	public Checkbox (Drawable parent, int x,  int y, FlatColor off, FlatColor on, FlatColor hover) {
		super(parent, x, y, 15, 15);
		this.on = on;
		this.off = off;
		this.hover = hover;
	}	
	public Checkbox (Drawable parent, int x,  int y) {
		this(parent, x, y, Colors.MATERIAL.BLUE_GREY._200,
			Colors.MATERIAL.BLUE_GREY._800,
			Colors.MATERIAL.BLUE_GREY._100);

	}

	public void drawContent(){

		if(state){
			FlatColor.fill(P.canvas, on);
		}else{
			if(isHovered()){
				FlatColor.fill(P.canvas, hover);
			}else{
				FlatColor.fill(P.canvas, off);
			}
		}

		if(isReleased()){
			state = !state;
		}

		FlatColor.stroke(P.canvas, off);
		P.canvas.rect(x, y, width, height);
	}

	public boolean getState(){
		return state;
	}

	public void setState(boolean s){
		state = s;
	}
}