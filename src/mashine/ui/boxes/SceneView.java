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
import mashine.scene.*;
import mashine.scene.features.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;

public class SceneView extends Drawable {

	LinkedHashMap<Device,DeviceElement> deviceElements;
	ArrayList<Device> selectedDevices;
	Frame displayFrame;

	public SceneView(){
		super(10, 50, MaShine.m.displayWidth - 10, MaShine.m.displayHeight - 50);
		deviceElements = new LinkedHashMap<Device, DeviceElement>();
		selectedDevices = new ArrayList<Device>();
		displayFrame = new Frame();
	}

	public void drawContent(){

		canvas.clear();

		Frame frame = displayFrame;

		ArrayList<Device> devices = MaShine.scene.getDevices();
		HashMap<String,EditableFeature> frameFeatures = frame.getFeatures();

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
			}else if(de.isClicked()){
				selectedDevices.clear();
				selected = true;
				selectedDevices.add(d);
			}

			// draw it
			de.setSelected(selected);
			de.setFrameFeatures(frameFeatures);
			de.draw();
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