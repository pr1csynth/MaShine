/**
 *  Unused now...
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;

import mashine.MaShine;
import mashine.utils.*;
import mashine.ui.Colors;
import mashine.ui.Drawable;
import mashine.ui.elements.Element;
import mashine.ui.elements.TextButton;
import processing.core.PImage;

public class Menu extends Drawable{
	
	private ArrayList<Element> bigButtons;
	private PImage mashineImage;

	
	public Menu(){
		super(0, 0, MaShine.m.displayWidth, 40);
		bigButtons = new ArrayList<Element>();
		new ArrayList<Element>();
		focus = true;

		Do switchToScene = new Do(){public void x(){MaShine.ui.open("DeviceEditor");MaShine.ui.open("DeviceSelector");}};
		Do switchToAnimate = new Do(){public void x(){MaShine.ui.open("DeviceSelector");MaShine.ui.open("SequenceEditor");MaShine.ui.open("SequenceSelector");MaShine.ui.open("ColorPalette");}};
		Do switchToLive = new Do(){public void x(){MaShine.ui.open("FilterSelector");}};

		bigButtons.add(new TextButton(this, 115, 0, 120, 32, "PATCH", 
			Colors.MATERIAL.BLUE_GREY._900, 60,
			Colors.MATERIAL.TEAL.A200,
			Colors.MATERIAL.TEAL.A700,
			switchToScene));
		bigButtons.add(new TextButton(this, 240, 0, 120, 32, "ANIMATE", 
			Colors.MATERIAL.BLUE_GREY._900, 60,
			Colors.MATERIAL.TEAL.A200,
			Colors.MATERIAL.TEAL.A700,
			switchToAnimate));
		bigButtons.add(new TextButton(this, 365, 0, 120, 32, "LIVE", 
			Colors.MATERIAL.BLUE_GREY._900, 60,
			Colors.MATERIAL.YELLOW.A400,
			Colors.MATERIAL.YELLOW.A700,
			switchToLive));

			mashineImage = MaShine.m.loadImage("data/mashine.png");
	}

	public void drawContent(){
		canvas.noStroke();
		FlatColor.background(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.image(mashineImage, 5, 8);

		for(Element bb : bigButtons){
			bb.draw();
		}
	}

}