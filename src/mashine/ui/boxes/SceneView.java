/**
 *  Instance to visualize devices of the scene
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.elements.*;
import mashine.utils.*;
import mashine.scene.*;
import mashine.scene.features.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;

import processing.core.PConstants;

public class SceneView extends Drawable {

	LinkedHashMap<Device,DeviceElement> deviceElements;
	ArrayList<Device> selectedDevices;
	Frame displayFrame;

	boolean dragging = false;
	int dragStartX = 0;
	int dragStartY = 0;

	public SceneView(){
		super(0, 40, MaShine.m.displayWidth, MaShine.m.displayHeight - 40);
		deviceElements = new LinkedHashMap<Device, DeviceElement>();
		selectedDevices = new ArrayList<Device>();
		displayFrame = new Frame();
	}

	public void drawContent(){

		canvas.clear();

		Frame frame = displayFrame;

		ArrayList<Device> devices = MaShine.scene.getDevices();
		HashMap<String,EditableFeature> frameFeatures = frame.getFeatures();

		boolean selectionMade = false;

		for(Device d : devices){
			// add new DeviceElement if not already present
			if(!deviceElements.containsKey(d)){
				deviceElements.put(d, new DeviceElement(this, d));
			}

			DeviceElement de = deviceElements.get(d);

			// selected it maybe
			boolean selected = selectedDevices.contains(d);

			if(MaShine.inputs.getState("keyboard.16.hold") && de.isClicked()){
				if(selected){
					selectedDevices.remove(d);
					selected = false;	
				}else{
					selectedDevices.add(d);
					selected = true;
				}
				selectionMade = true;		
			}else if(de.isClicked()){
				selectedDevices.clear();
				selected = true;
				selectionMade = true;		
				selectedDevices.add(d);
			}

			// draw it
			de.setSelected(selected);
			de.setFrameFeatures(frameFeatures);
			de.draw();			
		}

		if(hasFocus() && !selectionMade && MaShine.inputs.getState("mouse.left.press") && !dragging){
			selectedDevices.clear();
		}

		if(hasFocus() && !selectionMade  && MaShine.inputs.getState("mouse.left.drag") && !dragging){
			dragging = true;
			dragStartX = mouseX();
			dragStartY = mouseY();
		}

		if(!MaShine.inputs.getState("mouse.left.drag") && dragging && hasFocus()){
			dragging = false;

			if(!MaShine.inputs.getState("keyboard.16.hold")){
				selectedDevices.clear();
			}

			for(Device d : devices){
				int dx = d.getX();
				int dy = d.getY();
				int dw = d.getWidth();
				int dh = d.getHeight();

				if(
					Math.min(mouseX(), dragStartX) < dx && dx < Math.max(mouseX(), dragStartX) &&
					Math.min(mouseY(), dragStartY) < dy && dy < Math.max(mouseY(), dragStartY) ||
					Math.min(mouseX(), dragStartX) < (dx + dw) && (dx + dw) < Math.max(mouseX(), dragStartX) &&
					Math.min(mouseY(), dragStartY) < (dy + dh) && (dy + dh) < Math.max(mouseY(), dragStartY) ||
					Math.min(mouseX(), dragStartX) < dx && dx < Math.max(mouseX(), dragStartX) &&
					Math.min(mouseY(), dragStartY) < (dy + dh) && (dy + dh) < Math.max(mouseY(), dragStartY) ||
					Math.min(mouseX(), dragStartX) < (dx + dw) && (dx + dw) < Math.max(mouseX(), dragStartX) &&
					Math.min(mouseY(), dragStartY) < dy && dy < Math.max(mouseY(), dragStartY)
				){
					//d.setSelected(true);
					selectedDevices.add(d);
				}else{
					if(!MaShine.inputs.getState("keyboard.16.hold")){
						//d.setSelected(false);
					}
				}
			}
		}

		if(dragging){
			canvas.rectMode(PConstants.CORNERS);
			FlatColor.fill(canvas, Colors.MATERIAL.GREEN.A200.withAlpha(20));
			FlatColor.stroke(canvas, Colors.MATERIAL.CYAN.A200);
			canvas.rect(dragStartX, dragStartY, mouseX(), mouseY());
			canvas.rectMode(PConstants.CORNER);
		}

		// remove DeviceElement if Device not here anymore
		for(Iterator<Map.Entry<Device, DeviceElement>> it = deviceElements.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Device, DeviceElement> entry = it.next();
			if(!devices.contains(entry.getKey())) {
				it.remove();
			}
		}

	}

	public ArrayList<Device> getSelectedDevices(){
		return selectedDevices;
	}

	public void setSelectedDevices(ArrayList<Device> newSelection){
		selectedDevices = newSelection;
	}

	public void clearSelectedDevices(){
		selectedDevices.clear();
	}

	public void reloadElements(){
		deviceElements.clear();
	}

	public void setFrame(Frame f){
		displayFrame = f;
	}

}