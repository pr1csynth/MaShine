/**
 *  A checkbox element
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;
import mashine.utils.*;

public class Checkbox extends Element{

	boolean state = false;

	private FlatColor on;
	private FlatColor off;
	private FlatColor hover;

	private Do onEnable;
	private Do onDisable;

	public Checkbox (Drawable parent, int x,  int y, FlatColor off, FlatColor on, FlatColor hover, Do onEnable, Do onDisable) {
		super(parent, x, y, 14, 14);
		this.on = on;
		this.off = off;
		this.hover = hover;
		this.onEnable = onEnable;
		this.onDisable = onDisable;
	}	
	public Checkbox (Drawable parent, int x,  int y, Do onEnable, Do onDisable) {
		this(parent, x, y, Colors.MATERIAL.BLUE_GREY._200,
			Colors.MATERIAL.BLUE_GREY._800,
			Colors.MATERIAL.BLUE_GREY._100,onEnable, onDisable);

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
			if(state){
				onEnable.x();
			}else{
				onDisable.x();
			}
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