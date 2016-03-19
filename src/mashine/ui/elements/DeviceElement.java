/**
 *  A device element
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;
import mashine.scene.*;
import mashine.scene.features.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;

public class DeviceElement extends Element{

	private Device d;
	private HashMap<String,EditableFeature> frameFeatures;
	private boolean selected = false;

	public DeviceElement (Drawable parent, Device device) {
		// TODO : generalize translate + push/popMatrix in elements/elements sublclass
		super(parent, device.getX(), device.getY(), device.getWidth(), device.getHeight());
		this.d = device;
	}

	public void setFrameFeatures(HashMap<String,EditableFeature> frameFeatures){
		this.frameFeatures = frameFeatures;
	}
	public void setSelected(boolean selected){
		this.selected = selected;
	}

	public void drawContent(){

		x = d.getX();
		y = d.getY();
		width = d.getWidth();
		height = d.getHeight();

		boolean drawcrossline = false;
		ArrayList<Feature> devFeatures = d.getFeatures();
		P.canvas.noStroke();

			// LHM to display all the fields contained in devices
		LinkedHashMap<String,Integer> devFields = new LinkedHashMap<String,Integer>();

		FlatColor.fill(P.canvas, Colors.MATERIAL.BLUE_GREY._600);

		for(Feature feature : devFeatures){

				// for each feature in device :
				//  - check if the same feature is in the provided frame
				//  - draw it or not
			LinkedHashMap<String,Integer> featureFields = feature.getFields();
			if(frameFeatures.containsKey(d.getIdentifier() +"."+ feature.getType())){

				feature = frameFeatures.get(d.getIdentifier() +"."+ feature.getType());

					// special case color, coordinates, ...

				if(feature instanceof ColorFeature){
					FlatColor.fill(P.canvas, ((ColorFeature)feature).getColor());
					P.canvas.noStroke();
				}else{
					for(String f : featureFields.keySet()){
						devFields.put(feature.getType() +"."+ f, featureFields.get(f));
					}
				}

			}else{
					// if not provided in frame (device edit mode, fixed fields)
					//  - draw default place holder
					//  - display fields in consequences


				if(feature instanceof ColorFeature){
						// draw the background in grey
					FlatColor.fill(P.canvas, Colors.MATERIAL.GREY._800);
					drawcrossline = true;
				}else if(feature instanceof FixedField){
					for(String f : featureFields.keySet()){
						devFields.put("(f) "+ f, featureFields.get(f));
					}				
				}else if(feature instanceof SingleField){
					for(String f : featureFields.keySet()){
						devFields.put(f, featureFields.get(f));
					}
				}else{
					for(String f : featureFields.keySet()){
						devFields.put(feature.getType() +"."+ f, null);
					}
				}
			}
		}

			// actually draws the device in the canvas

		//FlatColor.stroke(P.canvas, Colors.MATERIAL.GREY._900);
		FlatColor.stroke(P.canvas, Colors.MATERIAL.GREY._700);
		P.canvas.rect(d.getX(), d.getY(), d.getWidth(), d.getHeight());

		if(drawcrossline)
			P.canvas.line(d.getX(), d.getHeight() + d.getY(), d.getWidth() +d.getX(), d.getY());

			// identifier 
		FlatColor.fill(P.canvas, Colors.MATERIAL.GREEN.A700);
		P.canvas.textAlign(P.canvas.LEFT, P.canvas.TOP);
		P.canvas.text(d.getName(), d.getX() + 5, d.getY() + 5);

		P.canvas.noStroke();

			// all the fields
		FlatColor.fill(P.canvas, Colors.MATERIAL.GREEN._400);
		int offset = 18;

		for(String f : devFields.keySet()){
			if(offset > (d.getHeight() - 5)){

				P.canvas.textAlign(P.canvas.LEFT, P.canvas.BOTTOM);
				P.canvas.text("...", d.getX() + 5, d.getY() + d.getHeight());
				break;
			}
			Integer val = devFields.get(f);
			if(val != null){
				P.canvas.text(f+":"+val, d.getX() + 5, d.getY() + offset);
			}else{
				P.canvas.text(f, d.getX() + 5, d.getY() + offset);
			}
			offset += 13;
		}


		// patch infos
		P.canvas.textAlign(P.canvas.RIGHT, P.canvas.BOTTOM);
		FlatColor.fill(P.canvas, Colors.MATERIAL.GREEN.A700);
		P.canvas.text(d.getUniverse() +":"+ d.getStartAddress(), d.getX() + d.getWidth() - 5, d.getY() + d.getHeight() - 5);
		
		if(selected){
			FlatColor.fill(P.canvas, Colors.MATERIAL.CYAN.A700);
			P.canvas.triangle(d.getX(), d.getY(), d.getX() + 10, d.getY(), d.getX() , d.getY() + 10); 
		}

	}
}