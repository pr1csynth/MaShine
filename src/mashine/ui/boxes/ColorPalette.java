/**
 *  Interface to select sequences of frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  18-03-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.Colors;
import mashine.ui.elements.*;
import mashine.scene.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class ColorPalette extends UIBox{

	FlatColor color;
	HashMap<String, RangeInput> colorParamInputs;

	public ColorPalette (MaShine m) {
		super(m, "COLORS", 450, 400, 250, 352);
		colorParamInputs = new HashMap<String, RangeInput>();

		colorParamInputs.put("hue", new RangeInput(this, 0f, 0f, 1f, 0.01f, 116, 30, 128));
		colorParamInputs.put("sat", new RangeInput(this, 0f, 0f, 1f, 0.01f, 116, 47, 128));
		colorParamInputs.put("lum", new RangeInput(this, 0f, 0f, 1f, 0.01f, 116, 64, 128));

		colorParamInputs.put("red",   new RangeInput(this, 0f, 0f, 255f, 1f, 116, 84, 128));
		colorParamInputs.put("green", new RangeInput(this, 0f, 0f, 255f, 1f, 116, 101, 128));
		colorParamInputs.put("blue",  new RangeInput(this, 0f, 0f, 255f, 1f, 116, 118, 128));

		colorParamInputs.put("white", new RangeInput(this, 0f, 0f, 255f, 1f, 116, 138, 128));

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

		ArrayList<FlatColor> colors = M.bank.getColors();

		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._800);
		canvas.textAlign(canvas.RIGHT, canvas.TOP);
		canvas.text("hue",        240, 30 + 3);
		canvas.text("saturation", 240, 47 + 3);
		canvas.text("luminance",  240, 64 + 3);
		canvas.text("red",        240, 84 + 3);
		canvas.text("green",      240,101 + 3);
		canvas.text("blue",       240,118 + 3);
		canvas.text("white",      240,138 + 3);

		FlatColor.fill(canvas, color.withAlphaAsWhite());
		canvas.noStroke();
		canvas.rect(8, 30, 103, 103);

		int i = 0;

		for(FlatColor c : colors){
			canvas.noStroke();
			FlatColor.fill(canvas,c.withAlphaAsWhite());
			int x = 8 + (i % 14) * 17;
			int y = 160 + M.floor(i/14)*17;

			if(hasFocus() && 
				y-1 < mouseY() &&
				mouseY() < y+16 &&
				x-1 < mouseX() &&
				mouseX() < x+16 &&
				M.inputs.getState("mouse.left.press"))
			{
				color = c;
				updateInputs();
			}

			canvas.rect(x, y, 15, 15);
			if(c == color){
				FlatColor.stroke(canvas, Colors.WHITE);
				canvas.noFill();
				canvas.rect(x,y,14,14);
			}
			
			i++;
		}
	}

	private void updateInputs(){
		updateRGBInputs();
		updateHSLInputs();
		colorParamInputs.get("white").setValue(color.getAlpha());
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