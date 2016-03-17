/**
 *  UI managing instance
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import mashine.*;
import mashine.ui.*;
import mashine.ui.boxes.*;
import mashine.scene.Device;
import mashine.scene.Frame;
import processing.core.PFont;
import java.util.HashMap; 
import java.util.LinkedList; 
import java.util.Comparator; 

public class UI{

	private Menu menu;
	private HashMap<String,Focusable> uiElements;
	private LinkedList<Focusable> openedUiElements;
	private MaShine M;
	public SceneVisualizer sceneVisualizer;
	public Status status;
	public PFont TEXTFONT;
	public PFont TITLEFONT;
	public int TEXTSIZE;
	public int TITLESIZE;

	public static final class SortByFocus implements Comparator<Focusable> {
		public int compare(Focusable a, Focusable b) {
			boolean mouseInA = a.mouseIn();
			boolean mouseInB = b.mouseIn();
			if(a.hasFocus() && a.M.mousePressed)
				return 1; // b get drawn first, a on top
			else if (b.hasFocus() && a.M.mousePressed)
				return -1; // b on top
			else
				if(mouseInA == mouseInB)
					return 0;
				else 
					if(mouseInA)
						return 1; // b get drawn first, a on top
					else
						return -1; // b on top
		}
	}

	public UI(MaShine m){
		M = m;
		M.colorMode(MaShine.RGB);

		TEXTFONT = M.loadFont("RobotoMono-Light-11.vlw");
		TEXTSIZE = 11;

		M.textFont(TEXTFONT);
		M.textSize(TEXTSIZE);

		status = new Status(M);
		menu = new Menu(M);
		sceneVisualizer = new SceneVisualizer(M);

		uiElements = new HashMap<String,Focusable>();
		uiElements.put("EventViewer", new EventViewer(M));
		uiElements.put("DataViewer", new DataViewer(M));
		uiElements.put("DeviceEditor", new DeviceEditor(M));
		openedUiElements = new LinkedList<Focusable>();
	}

	public void close(String uiElementName){
		M.println("Close el from name");
		if(uiElements.containsKey(uiElementName)){
			Focusable el = uiElements.get(uiElementName);
			openedUiElements.remove(el);
			el.defocus();
		}
	}

	public void close(Focusable el){
		M.println("Close el from object");
		if(uiElements.containsValue(el)){
			openedUiElements.remove(el);
			el.defocus();
		}
	}

	public void open(String uiElementName){
		if(uiElements.containsKey(uiElementName)){
			Focusable el = uiElements.get(uiElementName);
			openedUiElements.add(el);
			//el.focus();
		}
	}

	public void draw(){
		//M.strokeWeight((float)0.5);
		//M.strokeJoin(M.MITER);
		menu.draw();
		//sceneVisualizer.setFrame(M.scene.getDefaultFrame());
		//sceneVisualizer.setFrame(new Frame());

		
		sceneVisualizer.draw();

		openedUiElements.sort(new SortByFocus());

		for(Focusable el : openedUiElements){
			el.tick();
			el.draw();
			el.defocus();
		}

		if(!openedUiElements.isEmpty())
			if(openedUiElements.getLast().mouseIn())
				openedUiElements.getLast().focus();
			else
				sceneVisualizer.focus();
		
		status.draw();
	}

	public HashMap<String, Device> getSelectedDevices(){
		return sceneVisualizer.getSelectedDevices();
	}

	public void setSelectedDevices(HashMap<String, Device> newSelection){
		sceneVisualizer.setSelectedDevices(newSelection);
	}
}

