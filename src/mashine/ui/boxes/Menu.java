/**
 *  Unused now...
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.elements.*;
import java.util.ArrayList;

public class Menu extends Drawable{
	
	private ArrayList<Element> bigButtons;
	private ArrayList<Element> elements;

	
	public Menu(MaShine m){
		super(m, 0, 0, m.displayWidth, 40);
		bigButtons = new ArrayList<Element>();
		elements = new ArrayList<Element>();
		focus = true;

		Do switchToScene = new Do(){public void x(){M.ui.open("DeviceEditor");}};
		Do switchToAnimate = new Do(){public void x(){M.ui.open("SequenceEditor");M.ui.open("SequenceSelector");M.ui.open("ColorPalette");}};
		Do switchToLive = new Do(){public void x(){/*  OPEN SCENE TOOLS HERE  */}};
		Do openDataViewer = new Do(){public void x(){M.ui.open("DataViewer");}};
		Do openEventViewer = new Do(){public void x(){M.ui.open("EventViewer");}};
		Do openLinker = new Do(){public void x(){M.ui.open("Linker");}};

		Do save = new Do(){public void x(){M.inputs.getAction("mashine.save").x();}};
		Do saveAs = new Do(){public void x(){M.inputs.getAction("mashine.save_as").x();}};
		Do open = new Do(){public void x(){M.inputs.getAction("mashine.open").x();}};
		Do restore = new Do(){public void x(){M.inputs.getAction("mashine.restore").x();}};

		bigButtons.add(new TextButton(this, 15, 0, 120, 32, "PATCH", 
			Colors.WHITE, 60,
			Colors.MATERIAL.CYAN._600,
			Colors.MATERIAL.CYAN._700,
			switchToScene));
		bigButtons.add(new TextButton(this, 140, 0, 120, 32, "ANIMATE", 
			Colors.WHITE, 60,
			Colors.MATERIAL.DEEP_PURPLE._600,
			Colors.MATERIAL.DEEP_PURPLE._700,
			switchToAnimate));
		bigButtons.add(new TextButton(this, 265, 0, 120, 32, "LIVE", 
			Colors.WHITE, 60,
			Colors.MATERIAL.RED._600,
			Colors.MATERIAL.RED._700,
			switchToLive));
		bigButtons.add(new TextButton(this, 390,  0, 70, 15, "event in", 
			Colors.WHITE, 10,
			Colors.MATERIAL.TEAL._600,
			Colors.MATERIAL.TEAL._800,
			openEventViewer));
		bigButtons.add(new TextButton(this, 390, 17, 70, 15, "data  in", 
			Colors.WHITE, 10,
			Colors.MATERIAL.TEAL._600,
			Colors.MATERIAL.TEAL._800,
			openDataViewer));
		bigButtons.add(new TextButton(this, 465, 0, 70, 15, "links", 
			Colors.WHITE, 10,
			Colors.MATERIAL.TEAL._600,
			Colors.MATERIAL.TEAL._800,
			openLinker));

		bigButtons.add(new TextButton(this, 600, 0, 70, 15, "save", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			save));
		bigButtons.add(new TextButton(this, 600, 17, 70, 15, "save as", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			saveAs));

		bigButtons.add(new TextButton(this, 675, 0, 70, 15, "open", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			open));
		bigButtons.add(new TextButton(this, 675, 17, 70, 15, "restore", 
			Colors.WHITE, 10,Colors.MATERIAL.YELLOW._600,Colors.MATERIAL.YELLOW._800,
			restore));
	}

	public void drawContent(){
		canvas.noStroke();
		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(0, 0, width, height);
		//canvas.rect(M.mouseX, M.mouseY, 20, 20);

		for(Element bb : bigButtons){
			bb.draw();
		}
	}

}