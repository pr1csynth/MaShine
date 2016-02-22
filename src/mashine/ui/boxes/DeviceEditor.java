/**
 *  Interface to edit devices
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

// UIREFORM

public class DeviceEditor extends UIBox{


	public DeviceEditor (MaShine m) {
		super(m, "DEVICE EDITOR ", 50, 50, 200, 450);

		elements.add(new TextButton(this, "new", 5, 28, 
			new Do(){public void x(){/*cloneCurrentDevice();*/}}
		));

		elements.add(new TextButton(this, "clone", 65, 28, 
			new Do(){public void x(){/*newDevice();*/}}
		));

		elements.add(new TextInput(this, "test", 125, 28, 70));

	}

	public void drawUI(){
		HashMap<String, Device> selectedDevices = M.ui.getSelectedDevices();
		// add or remove elements
		drawUI(selectedDevices);
	}


	public void drawUI(HashMap<String, Device> selectedDevices){
		
		if(selectedDevices.size()==0){
			FlatColor.fill(canvas, Colors.WHITE);
			canvas.textAlign(M.LEFT, M.TOP);
			canvas.text("No device selected.", 0, 55); 
		}else if(selectedDevices.size() == 1){
			editOneDevice(selectedDevices.values().iterator().next());
		}else{
			editMultipleDevice(selectedDevices);
		}
	}

	public void editOneDevice(Device device){
		FlatColor.fill(canvas, Colors.WHITE);
		canvas.textAlign(M.LEFT, M.TOP);
		canvas.text(device.getIdentifier()+" selected.", 0, 55); 

		// toDo : addr, identifier, universe edit

		for(Feature f : device.getFeatures()){
			if(f instanceof EditableFeature){
				// editable : animable in sequences, just list it, to remove it
			}else{
				// uneditable : provide field by field modification possibilities
			}
		}
	}

	public void editMultipleDevice(HashMap<String,Device> devices){
		FlatColor.fill(canvas, Colors.WHITE);
		canvas.textAlign(M.LEFT, M.TOP);
		canvas.text(devices.size() + " devices selected.", 0, 55); 
	}

}