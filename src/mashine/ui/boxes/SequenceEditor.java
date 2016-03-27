/**
 *  Interface to edit sequences of animation
 *
 *	@author procsynth - Antoine Pintout
 *	@since  18-03-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.elements.*;
import mashine.scene.*;
import mashine.scene.features.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class SequenceEditor extends UIBox{

	private HashMap<String,Element> ui;
	private Sequence selectedSequence;
	private FlatColor selectedColor;
	private Frame currentFrame;
	private int currentFrameIndex = 0;
	private int lastFrameIndex = 0;
	private String lastSelectedDeviceHash = "";
	private boolean sendFrameOnOutputs = false;

	private HashMap<String, RangeInput> featureInputs; 
	private HashMap<String, Checkbox> featureEnables; 
	private ArrayList<Element> featureElements;

	public SequenceEditor (MaShine m) {
		super(m, "ANIM EDITOR", 650, 50, 270, 450);

		selectedSequence = M.bank.getSequence(0);

		ui = new HashMap<String,Element>();

		ui.put("seqName", new TextInput(this, selectedSequence.getName(), 0, 28, 120));

		ui.put("deleteFrame", new TextButton(this, "delete", 216, 45, 
			new Do(){public void x(){deleteFrame();}}
			));		
		ui.put("prevFrame", new TextButton(this, "<-", 0, 45, 
			new Do(){public void x(){prevFrame();}}
			));		
		ui.put("nextFrame", new TextButton(this, "->", 57, 45, 
			new Do(){public void x(){nextFrame();}}
			));		
		ui.put("copyFrame", new TextButton(this, "copy", 114, 45, 40, 
			new Do(){public void x(){copyFrame();}}
			));
		ui.put("newFrame", new TextButton(this, "+", 156, 45, 16, 
			new Do(){public void x(){newFrame();}}
			));
		ui.put("indexInput", new RangeInput(this, 1f, 1f, 1f, 1f, 174, 45, 30));

		ui.put("sendOnOutputs", new Checkbox(this, width - 14, height - 20, 
			new Do(){public void x(){sendFrameOnOutputs = false;}},
			new Do(){public void x(){sendFrameOnOutputs = true;}}
			));

		for (String el : ui.keySet()){
			elements.add(ui.get(el));
		}
	}

	public void tick(){
		if(selectedSequence != M.ui.getSelectedSequence()){
			selectedSequence = M.ui.getSelectedSequence();
			((TextInput) ui.get("seqName")).setValue(selectedSequence.getName());
			currentFrameIndex = selectedSequence.getSize()-1;
			((RangeInput)ui.get("indexInput")).setMax(currentFrameIndex +1);
			((RangeInput)ui.get("indexInput")).setValue(currentFrameIndex +1);
			lastSelectedDeviceHash = "";
		}else{
			selectedSequence.setName(((TextInput) ui.get("seqName")).value());
		}

		if(currentFrameIndex != lastFrameIndex){
			lastFrameIndex = currentFrameIndex;
			((RangeInput) ui.get("indexInput")).setValue(currentFrameIndex + 1);
		}else{
			currentFrameIndex = M.floor(((RangeInput) ui.get("indexInput")).value()) -1;
		}

		currentFrame = selectedSequence.getFrame(currentFrameIndex);
		M.ui.setDisplayedFrame(currentFrame);
		if(sendFrameOnOutputs){
			M.outputs.setFrame(currentFrame);
		}

		if(selectedColor != M.ui.getSelectedColor()){
			selectedColor = M.ui.getSelectedColor();
			linkColorToFeatureForCurrentFrame(selectedColor);
		}

		if(!lastSelectedDeviceHash.equals(selectedDevicesHash(M.ui.getSelectedDevices()))){

			ArrayList<Device> selectedDevices = M.ui.getSelectedDevices();
			lastSelectedDeviceHash = selectedDevicesHash(selectedDevices);
			ArrayList<Feature> commonFeatures = Device.commonFeatures(selectedDevices);
			//renew elements

			featureElements = new ArrayList<Element>();
			featureInputs = new HashMap<String,RangeInput>();
			featureEnables = new HashMap<String,Checkbox>();

			int offset = 70;

			for(Feature f : commonFeatures){

				if(f instanceof EditableFeature){


					Checkbox c = new Checkbox(this, width - 20, offset, 
						new Do(){public void x(){enableFeatureForCurrentFrame((EditableFeature)f);}},
						new Do(){public void x(){disableFeatureForCurrentFrame((EditableFeature)f);}}
						);
					boolean featureInFrame = true;

					for(Device d : selectedDevices){
						Feature df = currentFrame.getFeature(d,f);
						if(df == null){
							featureInFrame = false;
						}
					}

					c.setState(featureInFrame);
					featureEnables.put(f.getType(), c);
					featureElements.add(c);

					if(f instanceof ColorFeature){
						offset += 17;
					}else{
						for(String fi : f.getFields().keySet()){

							RangeInput e = new RangeInput(this, f.getFields().get(fi), 5, offset, 40);

							if(!featureInFrame)
								e.disable();

							Feature firstFeature = currentFrame.getFeature(selectedDevices.get(0), f);

							if(firstFeature != null){

								Integer commonFieldValue = firstFeature.getField(fi);

								e.setValue(commonFieldValue);
								// TODO : get most significant field value from frame for selected devices

								for(Device d : selectedDevices){
									Feature devFeature = currentFrame.getFeature(d, f);
									if(devFeature == null || commonFieldValue == null || commonFieldValue != devFeature.getField(fi)){
										e.setValue(null);
										e.setStringValue("_");
										break;							
									}
								}
							}else{
								e.setValue(null);
								e.setStringValue("_");	
							}

							featureInputs.put(f.getType() +"."+ fi, e);
							featureElements.add(e);
							offset += 17;
						}
					}

					offset +=3;
				}
			}
		}else{
			for(String ri : featureInputs.keySet()){
				if(featureInputs.get(ri).isEnabled()){
					if(featureInputs.get(ri).value() != null){
						updateFeatureFieldForCurrentFrame(ri, M.floor(featureInputs.get(ri).value()));
					}	
				}
			}
		}
	}

	public void drawUI(){

		ArrayList<Feature> commonFeatures = Device.commonFeatures(M.ui.getSelectedDevices());
		int offset = 73; 
		int index = 0;

		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._700);
		canvas.noStroke();
		canvas.rect(0, height - 20, width - 15, 15);

		FlatColor.fill(canvas, Colors.WHITE);
		canvas.textAlign(canvas.RIGHT, canvas.TOP);
		canvas.text("Send edited frame on outputs", width - 19, height - 16);
		
		if(selectedSequence.getSize() < 2){
			canvas.text(selectedSequence.getSize() +" frame", 205, 32);
		}else{
			canvas.text(selectedSequence.getSize() +" frames", 205, 32);
		}


		// draw background

		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._700);

		for(Feature f : commonFeatures){
			if(f instanceof EditableFeature){
				int diff = 0;

				if(f instanceof ColorFeature){
					diff = 20;
				}else{
					diff = 17 * f.getFields().size() + 3;
				}
				if(index % 2 == 0){
					canvas.rect(1, offset - 6, width -1, diff + 1);
				}
				offset += diff;
				index ++;
			}
		}

		offset = 73; 

		canvas.textAlign(canvas.LEFT, canvas.TOP);
		FlatColor.fill(canvas, Colors.WHITE);
		for(Feature f : commonFeatures){
			if(f instanceof EditableFeature){		

				canvas.textAlign(canvas.RIGHT, canvas.TOP);
				canvas.text(f.getType(), width - 25, offset);

				if(f instanceof ColorFeature){
					// canvas.textAlign(canvas.LEFT, canvas.TOP);
					// canvas.text("unlinked", 5, offset );
					offset += 17;
				}else{		
					if(f.getFields().size() != 1){
						for(String fi : f.getFields().keySet()){
							canvas.textAlign(canvas.LEFT, canvas.TOP);
							canvas.text(fi, 50, offset);
							offset +=17;
						}
					}else{
						offset +=17;
					}
				}

				offset +=3;
			}
		}
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

	}

	public void enableFeatureForCurrentFrame(EditableFeature feature){
		M.println("Enabling "+ feature.getType());
		for(Device d : M.ui.getSelectedDevices()){
			currentFrame.addFeature(d,feature);
		}

		linkColorToFeatureForCurrentFrame(selectedColor);

		for(String el : featureInputs.keySet()){
			M.println(el);
			if(el.split("\\.")[0].equals(feature.getType()))
				featureInputs.get(el).enable();
		}
	}

	public void disableFeatureForCurrentFrame(EditableFeature feature){
		M.println("Disabling "+ feature.getType());
		for(Device d : M.ui.getSelectedDevices()){
			currentFrame.removeFeature(d, feature);
		}

		for(String el : featureInputs.keySet()){
			M.println(el);
			if(el.split("\\.")[0].equals(feature.getType()))
				featureInputs.get(el).disable();
		}
	}

	public void updateFeatureFieldForCurrentFrame(String featureField, int value){
		for(Device d : M.ui.getSelectedDevices()){
			currentFrame.updateFeature(d, featureField.split("\\.")[0],featureField.split("\\.")[1], value);
		}
	}

	public void linkColorToFeatureForCurrentFrame(FlatColor color){
		Map<String,EditableFeature> frameFeatures = currentFrame.getFeatures();
		ArrayList<Device> selectedDevices = M.ui.getSelectedDevices();
		for(String featureKey : frameFeatures.keySet()){
			String id = featureKey.split("\\.")[0];
			if(frameFeatures.get(featureKey) instanceof ColorFeature){
				for(Device d : selectedDevices){
					if(d.getIdentifier().equals(id)){
						((ColorFeature) frameFeatures.get(featureKey)).link(color);
						break;
					}
				}
			}
		}
	}


	public void prevFrame(){
		currentFrameIndex --;
		if(currentFrameIndex < 0){
			currentFrameIndex = selectedSequence.getSize() + currentFrameIndex;
		}
		((RangeInput)ui.get("indexInput")).setValue(currentFrameIndex +1);
		lastSelectedDeviceHash = "";
	}
	public void nextFrame(){
		currentFrameIndex ++;
		currentFrameIndex = currentFrameIndex % selectedSequence.getSize();
		((RangeInput)ui.get("indexInput")).setValue(currentFrameIndex +1);
		lastSelectedDeviceHash = "";
	}
	public void deleteFrame(){
		selectedSequence.deleteFrame(currentFrameIndex);
		currentFrameIndex = selectedSequence.getSize()-1;
		((RangeInput)ui.get("indexInput")).setMax(currentFrameIndex +1);
		((RangeInput)ui.get("indexInput")).setValue(currentFrameIndex +1);
		lastSelectedDeviceHash = "";
	}
	public void newFrame(){
		selectedSequence.addFrame(new Frame());
		currentFrameIndex = selectedSequence.getSize()-1;
		((RangeInput)ui.get("indexInput")).setMax(currentFrameIndex +1);
		((RangeInput)ui.get("indexInput")).setValue(currentFrameIndex +1);
		lastSelectedDeviceHash = "";
	}
	public void copyFrame(){
		selectedSequence.addFrame(new Frame(currentFrame));
		currentFrameIndex = selectedSequence.getSize()-1;
		((RangeInput)ui.get("indexInput")).setMax(currentFrameIndex +1);
		((RangeInput)ui.get("indexInput")).setValue(currentFrameIndex +1);
		lastSelectedDeviceHash = "";
	}

	private String selectedDevicesHash(ArrayList<Device> dev){
		String hash = "_" + dev.size();
		for(Device d : dev){
			hash += d.getIdentifier();
		}
		return hash;
	}
}