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
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

// UIREFORM

public class DeviceEditor extends UIBox{

	private String featuresHash = "";
	private ArrayList<Element> featureElements; 
	private HashMap<String, RangeInput> featureInputs; 
	private TextInput deviceIdendifierElement; 
	private RangeInput universeElement; 
	private RangeInput addressElement;

	public DeviceEditor (MaShine m) {
		super(m, "SCENE EDITOR", 50, 50, 200, 450);

		featureElements	= new ArrayList<Element>(); 
		featureInputs	= new HashMap<String,RangeInput>(); 

		elements.add(new TextButton(this, "delete", 146, 28, 
		new Do(){public void x(){/*newDevice();*/}}
		));
		elements.add(new TextButton(this, "clone", 5, 48, 
			new Do(){public void x(){/*newDevice();*/}}
			));

		deviceIdendifierElement = new TextInput(this, "devID", 0, 28, 93);
		universeElement = new RangeInput(this, 42f, 1f, 99f, 1f, 95, 28, 20);
		addressElement = new RangeInput(this, 1f, 1f, 512f, 1f, 117, 28, 27);
		elements.add(deviceIdendifierElement);
		elements.add(universeElement);
		elements.add(addressElement);

	}

	public void drawUI(){
		HashMap<String, Device> selectedDevices = M.ui.getSelectedDevices();
		// add or remove elements

		if(!selectedDevices.isEmpty()){

			Device firstDevice = selectedDevices.values().iterator().next();
			// set common properties inputs
			if(selectedDevices.size() == 1){
				deviceIdendifierElement.enable();
			}else{
				deviceIdendifierElement.disable();
				deviceIdendifierElement.setValue("("+ selectedDevices.size() + " devices)"); 
			}

			// dress up a list of common features, order does not matter
			ArrayList<Feature> commonFeatures = firstDevice.getFeatures();
			Integer commonUniverse = firstDevice.getUniverse();
			Integer commonAddress = firstDevice.getStartAddress();
			
			for(String d : selectedDevices.keySet()){
				ArrayList<Feature> deviceFeatures = selectedDevices.get(d).getFeatures();

				if(commonUniverse != null && selectedDevices.get(d).getUniverse() != commonUniverse){
					commonUniverse = null;
				}

				if(commonAddress != null && selectedDevices.get(d).getStartAddress() != commonAddress){
					commonAddress = null;
				}

				Iterator<Feature> cfi = commonFeatures.iterator();
				while(cfi.hasNext()){
					Feature cf = cfi.next();
					boolean found = false;
					for(Feature df : deviceFeatures){
						if(df.getType() == cf.getType())
							found = true;
					}

					if(!found){
						cfi.remove();
					}
				}
			}
			

			// compare with elements already present


			// if list doesn't match in content or position; break and renew elements; else make sure input values match dev values 
			if(!featuresHash.equals(selectedDevicesHash(selectedDevices))){
				featuresHash = selectedDevicesHash(selectedDevices);

				if(selectedDevices.size() == 1){
					deviceIdendifierElement.setValue(firstDevice.getIdentifier()); 
					addressElement.setValue(firstDevice.getStartAddress());
					universeElement.setValue(firstDevice.getUniverse());
				}else{
					if(commonUniverse == null){
						universeElement.setValue(null);
						universeElement.setStringValue("_");	
					}else{
						universeElement.setValue(commonUniverse);
					}

					if(commonAddress == null){
						addressElement.setValue(null);
						addressElement.setStringValue("_");	
					}else{
						addressElement.setValue(commonAddress);
					}
				}


				featureElements = new  ArrayList<Element>();

				int offset = 67; 
				for(Feature f : commonFeatures){
					// remove button // eventual input field // (move up/down for later)
					featureElements.add(new TextButton(this, "delete", 146, offset, 
						new Do(){public void x(){removeFeature(f.getType());}}, true
						));
					offset += 17;
					if(!(f instanceof EditableFeature)){
						offset -= 17;
						for(String fi : f.getFields().keySet()){

							RangeInput e = new RangeInput(this, f.getFields().get(fi), 5, offset, 40);

							for(String d : selectedDevices.keySet()){

								Integer devFieldValue = selectedDevices.get(d).getFeatureField(f.getType() +"."+ fi);

								if(devFieldValue == null || !devFieldValue.equals(f.getFields().get(fi))){
									e.setValue(null);
									e.setStringValue("_");
									break;
								}
							}
							featureInputs.put(f.getType() +"."+ fi, e);
							featureElements.add(e);
							offset += 17;
						}
					}
				}
				// else update device objects with new values from inputs
			}else{

				if(selectedDevices.size() == 1){
					if(M.scene.validateDeviceIdentifier(deviceIdendifierElement.value())){
						M.scene.renameDevice(selectedDevices.values().iterator().next(), deviceIdendifierElement.value());
					}else{
						// deviceElement shows that new identifier is wrong
					}
				}
				for(String ff : featureInputs.keySet()){
					if(featureInputs.get(ff).value() != null){
						updateFeature(ff, Math.round(featureInputs.get(ff).value()));
					}
				}
				if(universeElement.value() != null){
					updateUniverse(Math.round(universeElement.value()));
				}
				if(addressElement.value() != null){
					updateAddress(Math.round(addressElement.value()));
				}
			}

			featureElements.sort(new UI.SortByFocus());

			// draw elements
			for(Element el : featureElements){
				el.draw();
				if(el.mouseIn()){
					el.focus();
				}else{
					if(el.hasFocus())
						el.defocus();
				}
			}

			// draw text
			int offset = 70; 
			canvas.textAlign(canvas.LEFT, canvas.TOP);
			FlatColor.fill(canvas, Colors.WHITE);
			for(Feature f : commonFeatures){
				canvas.text(f.getType(), 65, offset);
				offset +=17;
			}

		}else{
			if(!featuresHash.equals(selectedDevicesHash(selectedDevices))){
				featuresHash = selectedDevicesHash(selectedDevices);

				deviceIdendifierElement.enable();
				// prefill for new device
				deviceIdendifierElement.setValue("newDevice"); // find a valid name using M.scene
			}
		}
	}

	public void editOneDevice(Device device){
		FlatColor.fill(canvas, Colors.WHITE);
		canvas.textAlign(M.LEFT, M.TOP);
		canvas.text(device.getIdentifier()+" selected.", 5, 50);

		int offset = 65; 

		// toDo : addr, identifier, universe edit

		for(Feature fe : device.getFeatures()){
			
			if(fe instanceof EditableFeature){
				// editable : animable in sequences, just list it, to remove it
				canvas.text(fe.getType(), 30, offset);
				offset += 15;
			}else{
				for(String fi : fe.getFields().keySet()){
					canvas.text(fe.getType() +"."+ fi, 70, offset);
					offset+=15;
				}
			}
		}
	}

	private void removeFeature(String featureId){
		HashMap<String, Device> selectedDevices = M.ui.getSelectedDevices();
		M.println("REMOVE "+ featureId + " from " + selectedDevices.size() + " devices");
		featuresHash = "";
		for(String d : selectedDevices.keySet()){
			selectedDevices.get(d).removeFeature(featureId);
		}

	}

	private void updateFeature(String featureField, int value){
		HashMap<String, Device> selectedDevices = M.ui.getSelectedDevices();
		for(String d : selectedDevices.keySet()){
			selectedDevices.get(d).updateFeature(featureField, value);
		}
	}
	private void updateUniverse(int universe){
		HashMap<String, Device> selectedDevices = M.ui.getSelectedDevices();
		for(String d : selectedDevices.keySet()){
			selectedDevices.get(d).setUniverse(universe);
		}
	}
	private void updateAddress(int address){
		HashMap<String, Device> selectedDevices = M.ui.getSelectedDevices();
		for(String d : selectedDevices.keySet()){
			selectedDevices.get(d).setStartAddress(address);
		}
	}

	private String selectedDevicesHash(HashMap<String,Device> selectedDevices){
		String newFeaturesHash = "_" + selectedDevices.size();
		for(String d : selectedDevices.keySet()){
			newFeaturesHash += d;
		}
		return newFeaturesHash;
	}

}