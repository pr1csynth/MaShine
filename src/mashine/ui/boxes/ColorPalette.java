/**
 *  Interface to select sequences of frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  18-03-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.elements.*;
import mashine.scene.*;
import java.util.HashMap;
import java.util.Collections;

public class ColorPalette extends UIBox{

	FlatColor color;
	HashMap<String, RangeInput> colorParamInputs;

	public ColorPalette (MaShine m) {
		super(m, "COLORS", 950, 400, 260, 350);
		colorParamInputs = new HashMap<String, RangeInput>();

		colorParamInputs.put("hue", new RangeInput(this, 0f, 0f, 1f, 0.01f, 127, 30, 128));
		colorParamInputs.put("sat", new RangeInput(this, 0f, 0f, 1f, 0.01f, 127, 47, 128));
		colorParamInputs.put("lum", new RangeInput(this, 0f, 0f, 1f, 0.01f, 127, 64, 128));

		colorParamInputs.put("red",   new RangeInput(this, 0f, 0f, 255f, 1f, 127, 84, 128));
		colorParamInputs.put("green", new RangeInput(this, 0f, 0f, 255f, 1f, 127, 101, 128));
		colorParamInputs.put("blue",  new RangeInput(this, 0f, 0f, 255f, 1f, 127, 118, 128));

		colorParamInputs.put("white", new RangeInput(this, 0f, 0f, 255f, 1f, 127, 138, 128));

		for(Element e : colorParamInputs.values()){
			elements.add(e);
		}

		elements.add(new TextButton(this, "select", 8, 138, 
			new Do(){public void x(){updateReference();}}
		));

		color = new FlatColor(Colors.WHITE);
	}

	public void tick(){

		if(M.abs(color.getHue() - colorParamInputs.get("hue").value()) > 0.011){
			color.setHue(colorParamInputs.get("hue").value());
			updateRGBInputs();
			return;
		}
		if(M.abs(color.getSaturation() - colorParamInputs.get("sat").value()) > 0.011){
			color.setSaturation(colorParamInputs.get("sat").value());
			updateRGBInputs();
			return;
		}
		if(M.abs(color.getBrightness() - colorParamInputs.get("lum").value()) > 0.011){
			color.setBrightness(colorParamInputs.get("lum").value());
			updateRGBInputs();
			return;
		}

		if(color.getRed() != colorParamInputs.get("red").value()){
			color.setRed(M.round(colorParamInputs.get("red").value()));
			updateHSLInputs();
			return;
		}
		if(color.getGreen() != colorParamInputs.get("green").value()){
			color.setGreen(M.round(colorParamInputs.get("green").value()));
			updateHSLInputs();
			return;
		}
		if(color.getBlue() != colorParamInputs.get("blue").value()){
			color.setBlue(M.round(colorParamInputs.get("blue").value()));
			updateHSLInputs();
			return;
		}
		if(color.getAlpha() != colorParamInputs.get("white").value()){
			color.setAlpha(M.round(colorParamInputs.get("white").value()));
		}

	}

	public void drawUI(){

		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._800);
		canvas.textAlign(canvas.RIGHT, canvas.TOP);
		canvas.text("hue",        250, 30 + 3);
		canvas.text("saturation", 250, 47 + 3);
		canvas.text("luminance",  250, 64 + 3);
		canvas.text("red",        250, 84 + 3);
		canvas.text("green",      250,101 + 3);
		canvas.text("blue",       250,118 + 3);
		canvas.text("white",      250,138 + 3);

		FlatColor.fill(canvas, color.withAlphaAsWhite());
		canvas.noStroke();
		canvas.rect(8, 30, 103, 103);
	}

	private void updateRGBInputs(){
		colorParamInputs.get("red").setValue(color.getRed());
		colorParamInputs.get("green").setValue(color.getGreen());
		colorParamInputs.get("blue").setValue(color.getBlue());
	}
	private void updateHSLInputs(){
		colorParamInputs.get("hue").setValue(color.getHue());
		colorParamInputs.get("sat").setValue(color.getSaturation());
		colorParamInputs.get("lum").setValue(color.getBrightness());
	}

	public FlatColor getSelectedColor(){
		return color;
	}

	public void setSelectedColor(FlatColor c){
		color = c;
	}

	public void updateReference(){
		color = new FlatColor(color);
	}

}