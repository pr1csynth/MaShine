/**
 *  Unused now...
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;

import mashine.Do;
import mashine.MaShine;
import mashine.ui.Colors;
import mashine.ui.Drawable;
import mashine.ui.Element;
import mashine.ui.FlatColor;
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

		Do switchToScene = new Do(){public void x(){MaShine.ui.open("DeviceEditor");}};
		Do switchToAnimate = new Do(){public void x(){MaShine.ui.open("SequenceEditor");MaShine.ui.open("SequenceSelector");MaShine.ui.open("ColorPalette");}};
		Do switchToLive = new Do(){public void x(){/*  OPEN SCENE TOOLS HERE  */}};
		Do openDataViewer = new Do(){public void x(){MaShine.ui.open("DataViewer");}};
		Do openEventViewer = new Do(){public void x(){MaShine.ui.open("EventViewer");}};
		Do openLinker = new Do(){public void x(){MaShine.ui.open("Linker");}};

		Do save = new Do(){public void x(){MaShine.inputs.getAction("mashine.save").x();}};
		Do saveAs = new Do(){public void x(){MaShine.inputs.getAction("mashine.save_as").x();}};
		Do open = new Do(){public void x(){MaShine.inputs.getAction("mashine.open").x();}};
		Do restore = new Do(){public void x(){MaShine.inputs.getAction("mashine.restore").x();}};

		bigButtons.add(new TextButton(this, 115, 0, 120, 32, "PATCH", 
			Colors.WHITE, 60,
			Colors.MATERIAL.CYAN._600,
			Colors.MATERIAL.CYAN._700,
			switchToScene));
		bigButtons.add(new TextButton(this, 240, 0, 120, 32, "ANIMATE", 
			Colors.WHITE, 60,
			Colors.MATERIAL.DEEP_PURPLE._600,
			Colors.MATERIAL.DEEP_PURPLE._700,
			switchToAnimate));
		bigButtons.add(new TextButton(this, 365, 0, 120, 32, "LIVE", 
			Colors.WHITE, 60,
			Colors.MATERIAL.RED._600,
			Colors.MATERIAL.RED._700,
			switchToLive));
		bigButtons.add(new TextButton(this, 502,  0, 70, 15, "event in", 
			Colors.WHITE, 10,
			Colors.MATERIAL.TEAL._600,
			Colors.MATERIAL.TEAL._800,
			openEventViewer));
		bigButtons.add(new TextButton(this, 502, 17, 70, 15, "data  in", 
			Colors.WHITE, 10,
			Colors.MATERIAL.TEAL._600,
			Colors.MATERIAL.TEAL._800,
			openDataViewer));
		bigButtons.add(new TextButton(this, 575, 0, 70, 15, "links", 
			Colors.WHITE, 10,
			Colors.MATERIAL.TEAL._600,
			Colors.MATERIAL.TEAL._800,
			openLinker));

		bigButtons.add(new TextButton(this, 650, 0, 70, 15, "save", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			save));
		bigButtons.add(new TextButton(this, 650, 17, 70, 15, "save as", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			saveAs));

		bigButtons.add(new TextButton(this, 722, 0, 70, 15, "open", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			open));
		bigButtons.add(new TextButton(this, 722, 17, 70, 15, "restore", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			restore));
			mashineImage = MaShine.m.loadImage("mashine.png");
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