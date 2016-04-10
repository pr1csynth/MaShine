/**
 *  Interface to edit devices
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;
import java.util.HashMap;

import mashine.Do;
import mashine.MaShine;
import mashine.Scene;
import mashine.UI;
import mashine.scene.Device;
import mashine.scene.features.EditableFeature;
import mashine.scene.features.Feature;
import mashine.scene.features.FixedField;
import mashine.scene.features.SingleField;
import mashine.ui.Colors;
import mashine.ui.Element;
import mashine.ui.FlatColor;
import mashine.ui.UIBox;
import mashine.ui.elements.RangeInput;
import mashine.ui.elements.TextButton;
import mashine.ui.elements.TextInput;
import processing.core.PConstants;

// UIREFORM

public class DeviceEditor extends UIBox{

	private String featuresHash = "";
	private ArrayList<Element> featureElements; 
	private HashMap<String, RangeInput> featureInputs; 
	private TextInput deviceNameElement; 
	private TextInput genericFieldInputElement; 
	private RangeInput universeElement; 
	private RangeInput addressElement;

	private RangeInput xPosElement;
	private RangeInput yPosElement;
	private RangeInput widthElement;
	private RangeInput heightElement;

	public DeviceEditor () {
		super("SCENE EDITOR", 50, 50, 200, 450);

		featureElements	= new ArrayList<Element>(); 
		featureInputs	= new HashMap<String,RangeInput>(); 

		elements.add(new TextButton(this, "delete", 146, 28, 
	new Do(){public void x(){removeSelectedDevices();}}
	));
		elements.add(new TextButton(this, "clone", 5, 48, 
	new Do(){public void x(){cloneSelectedDevices();}}
	));

		elements.add(new TextButton(this, "fixed", 146, height - 20, 
	new Do(){public void x(){addFixedField();}}
	));
		elements.add(new TextButton(this, "single", 146, height - 37, 
	new Do(){public void x(){addSingleField();}}
	));

		int offset = 57;
		for(final String featureClass : Scene.FEATURES.keySet()){
			String shortName = featureClass.substring(featureClass.lastIndexOf('.') + 1);
			elements.add(new TextButton(this, shortName, 146, height - offset, 
				new Do(){public void x(){addFeature(featureClass);}}
				));
			offset += 17;
		}

		deviceNameElement = new TextInput(this, "devName", 0, 28, 93);
		universeElement = new RangeInput(this, 42f, 0f, 99f, 1f, 95, 28, 20);
		addressElement = new RangeInput(this, 1f, 1f, 512f, 1f, 117, 28, 27);
		genericFieldInputElement = new TextInput(this, "generic", 43, height - 20, 100);
		
		xPosElement = new RangeInput(this, 0f, 0f, 1000f, 1f, 0, height - 74, 60);
		yPosElement = new RangeInput(this, 0f, 0f, 700f, 1f, 62, height - 74, 60);
		widthElement = new RangeInput(this, 60f, 60f, 300f, 1f, 0, height - 57, 60);
		heightElement = new RangeInput(this, 15f, 15f, 600f, 1f, 62, height - 57, 60);

		elements.add(deviceNameElement);
		elements.add(universeElement);
		elements.add(addressElement);
		elements.add(genericFieldInputElement);

		elements.add(xPosElement);
		elements.add(yPosElement);
		elements.add(widthElement);
		elements.add(heightElement);

	}

	public void tick(){
		moveSelectedDevices();
	}

	public void drawUI(){
		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		// add or remove elements


			canvas.textAlign(PConstants.LEFT, PConstants.TOP);
			FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._300);
			canvas.text("id", 74, 45);
			canvas.text("un", 94, 45);
			canvas.text("addr", 116, 45);

		if(!selectedDevices.isEmpty()){

			Device firstDevice = selectedDevices.get(0);
			// set common properties inputs
			if(selectedDevices.size() == 1){
				deviceNameElement.enable();
			}else{
				deviceNameElement.disable();
				deviceNameElement.setValue("("+ selectedDevices.size() + " devices)"); 
			}

			// dress up a list of common features, order does not matter
			ArrayList<Feature> commonFeatures = Device.commonFeatures(selectedDevices);

			Integer commonUniverse = firstDevice.getUniverse();
			Integer commonAddress = firstDevice.getStartAddress();
			Integer commonXPos = firstDevice.getX();
			Integer commonYPos = firstDevice.getY();
			Integer commonWidth = firstDevice.getWidth();
			Integer commonHeight = firstDevice.getHeight();
			
			for(Device d : selectedDevices){

				if(commonUniverse != null && d.getUniverse() != commonUniverse){
					commonUniverse = null;
				}

				if(commonAddress != null && d.getStartAddress() != commonAddress){
					commonAddress = null;
				}

				if(commonXPos != null && d.getX() != commonXPos){commonXPos = null;}
				if(commonYPos != null && d.getY() != commonYPos){commonYPos = null;}
				if(commonWidth != null && d.getWidth() != commonWidth){commonWidth = null;}
				if(commonHeight != null && d.getHeight() != commonHeight){commonHeight = null;}

			}
			

			// compare with elements already present


			// if list doesn't match in content or position; break and renew elements; else make sure input values match dev values 
			if(!featuresHash.equals(selectedDevicesHash(selectedDevices))){
				featuresHash = selectedDevicesHash(selectedDevices);

				if(selectedDevices.size() == 1){
					deviceNameElement.setValue(firstDevice.getName()); 
					addressElement.setValue(firstDevice.getStartAddress());
					universeElement.setValue(firstDevice.getUniverse());
					xPosElement.setValue(firstDevice.getX());
					yPosElement.setValue(firstDevice.getY());
					widthElement.setValue(firstDevice.getWidth());
					heightElement.setValue(firstDevice.getHeight());
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
					}else{addressElement.setValue(commonAddress);}


					if(commonXPos == null){
						xPosElement.setValue(null);
						xPosElement.setStringValue("_");	
					}else{xPosElement.setValue(commonXPos);}					
					if(commonYPos == null){
						yPosElement.setValue(null);
						yPosElement.setStringValue("_");	
					}else{yPosElement.setValue(commonYPos);}					
					if(commonWidth == null){
						widthElement.setValue(null);
						widthElement.setStringValue("_");	
					}else{widthElement.setValue(commonWidth);}					
					if(commonHeight == null){
						heightElement.setValue(null);
						heightElement.setStringValue("_");	
					}else{heightElement.setValue(commonHeight);}

					
				}


				featureElements = new ArrayList<Element>();

				int offset = 67; 
				for(final Feature f : commonFeatures){
					// remove button // eventual input field // (move up/down for later)
					featureElements.add(new TextButton(this, "delete", 146, offset, 
						new Do(){public void x(){removeFeature(f.getType());}}, true
						));
					offset += 17;
					if(!(f instanceof EditableFeature)){
						offset -= 17;
						for(String fi : f.getFields().keySet()){

							RangeInput e = new RangeInput(this, f.getFields().get(fi), 5, offset, 40);

							for(Device d : selectedDevices){

								Integer devFieldValue = (d).getFeatureField(f.getType() +"."+ fi);

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
						MaShine.scene.renameDevice(selectedDevices.get(0), deviceNameElement.value());
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
				if(xPosElement.value() != null){
					updateXPos(Math.round(xPosElement.value()));
				}				
				if(yPosElement.value() != null){
					updateYPos(Math.round(yPosElement.value()));
				}				
				if(widthElement.value() != null){
					updateWidth(Math.round(widthElement.value()));
				}				
				if(heightElement.value() != null){
					updateHeight(Math.round(heightElement.value()));
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
			canvas.textAlign(PConstants.LEFT, PConstants.TOP);
			FlatColor.fill(canvas, Colors.WHITE);
			for(Feature f : commonFeatures){
				canvas.text(f.getType(), 65, offset);
				offset +=17;
			}

		}else{
			if(!featuresHash.equals(selectedDevicesHash(selectedDevices))){
				featuresHash = selectedDevicesHash(selectedDevices);

				deviceNameElement.enable();
				// prefill for new device
				deviceNameElement.setValue("newDevice"); // find a valid name using MaShine.scene
			}
		}
	}

	private void removeFeature(String featureId){
		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		featuresHash = "";
		for(Device d : selectedDevices){
			(d).removeFeature(featureId);
		}
	}

	private void cloneSelectedDevices(){
		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		ArrayList<Device> newSelectedDevices = new ArrayList<Device>();
		for(Device d : selectedDevices){
			Device newDev = new Device(d, d.getName());

			MaShine.scene.addDevice(newDev);
			newSelectedDevices.add(newDev);		
		}

		MaShine.ui.setSelectedDevices(newSelectedDevices);
	}

	private void removeSelectedDevices(){
		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		for(Device d : selectedDevices){
			MaShine.scene.removeDevice((d));
		}
	}

	private void updateFeature(String featureField, int value){
		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		for(Device d : selectedDevices){
			(d).updateFeature(featureField, value);
		}
	}

	private void addFeature(String featureClassName){
		if(Scene.FEATURES.containsKey(featureClassName)){
			ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
			for(Device d : selectedDevices){
				try{
					(d).addFeature((Feature) Scene.FEATURES.get(featureClassName).newInstance());
				}catch(Exception ignore){}
			}
			featuresHash = "";
		}
	}

	private void addFixedField(){
		String fieldName = genericFieldInputElement.value();
		if(fieldName != null){
			ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
			for(Device d : selectedDevices){
				(d).addFeature(new FixedField(fieldName));
			}
			featuresHash = "";
		}
	}

	private void addSingleField(){
		String fieldName = genericFieldInputElement.value();
		if(fieldName != null){
			ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
			for(Device d : selectedDevices){
				d.addFeature(new SingleField(fieldName));
			}
			featuresHash = "";
		}
	}

	private void updateUniverse(int universe){
		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		for(Device d : selectedDevices){
			(d).setUniverse(universe);
		}
	}
	private void updateAddress(int address){
		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		for(Device d : selectedDevices){
			(d).setStartAddress(address);
		}
	}

	private void updateXPos(int x){for(Device d : MaShine.ui.getSelectedDevices()){(d).setX(x);}}
	private void updateYPos(int x){for(Device d : MaShine.ui.getSelectedDevices()){(d).setY(x);}}
	private void updateWidth(int x){for(Device d : MaShine.ui.getSelectedDevices()){(d).setWidth(x);}}
	private void updateHeight(int x){for(Device d : MaShine.ui.getSelectedDevices()){(d).setHeight(x);}}

	private String selectedDevicesHash(ArrayList<Device> selectedDevices){
		String hash = "_" + selectedDevices.size();
		for(Device d : selectedDevices){
			hash += d.getIdentifier();
		}
		return hash;
	}

	private void moveSelectedDevices(){

		ArrayList<Device> selectedDevices = MaShine.ui.getSelectedDevices();
		if(MaShine.inputs.getState("keyboard.38.hold") && !MaShine.inputs.getState("keyboard.16.hold")){
			for(Device d : selectedDevices){
				(d).moveUp();
			}
		}		
		if(MaShine.inputs.getState("keyboard.40.hold") && !MaShine.inputs.getState("keyboard.16.hold")){
			for(Device d : selectedDevices){
				(d).moveDown();
			}
		}		
		if(MaShine.inputs.getState("keyboard.37.hold") && !MaShine.inputs.getState("keyboard.16.hold")){
			for(Device d : selectedDevices){
				(d).moveLeft();
			}
		}		
		if(MaShine.inputs.getState("keyboard.39.hold") && !MaShine.inputs.getState("keyboard.16.hold")){
			for(Device d : selectedDevices){
				(d).moveRight();
			}
		}
	}

}