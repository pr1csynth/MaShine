/**
 *  A textbutton element
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;

public class TextButton extends Button{

	private FlatColor text;
	private FlatColor button;
	private FlatColor hover;
	private String caption;
	private int marginLeft;


	public TextButton (Drawable parent, int x,  int y, int w, int h, String caption, FlatColor text, int marginLeft, FlatColor button, FlatColor hover, Do action) {
		super(parent, x, y, w, h, action);
		this.text = text;
		this.button = button;
		this.hover = hover;
		this.caption = caption;
		this.marginLeft = marginLeft;
	}

	public TextButton (Drawable parent, String caption, int x, int y, Do action){
		this(parent, x, y, 55, 15, caption, 
			Colors.MATERIAL.BLUE_GREY._800, 3,
			Colors.MATERIAL.BLUE_GREY._100,
			Colors.MATERIAL.BLUE_GREY._200,
			action
		);
	}

	public void drawContent(){

		super.drawContent();

		if(isHovered()){
			FlatColor.fill(P.canvas, hover);
		}else{
			FlatColor.fill(P.canvas, button);
		}

		P.canvas.noStroke();
		P.canvas.rect(x, y, width, height);

		P.canvas.textAlign(P.canvas.LEFT, P.canvas.CENTER);

		FlatColor.fill(P.canvas, text);
		P.canvas.text(caption,  x + marginLeft,  y + height/2);

	}
}