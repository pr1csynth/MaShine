/**
 *  Interface to select sequences of frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  18-03-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;
import java.util.HashMap;

import mashine.MaShine;
import mashine.ui.Colors;
import mashine.ui.Element;
import mashine.ui.FlatColor;
import mashine.ui.UIBox;
import mashine.ui.elements.RangeInput;
import processing.core.PConstants;

public class ColorPalette extends UIBox{

	FlatColor color;
	HashMap<String, RangeInput> colorParamInputs;

	public ColorPalette () {
		super("COLORS", 450, 400, 250, 352);
		colorParamInputs = new HashMap<String, RangeInput>();

		colorParamInputs.put("hue", new RangeInput(this, 0f, 0f, 1f, 0.0001f, 116, 30, 128));
		colorParamInputs.put("sat", new RangeInput(this, 0f, 0f, 1f, 0.0001f, 116, 47, 128));
		colorParamInputs.put("lum", new RangeInput(this, 0f, 0f, 1f, 0.0001f, 116, 64, 128));

		colorParamInputs.put("red",   new RangeInput(this, 0f, 0f, 255f, 1f, 116, 84, 128));
		colorParamInputs.put("green", new RangeInput(this, 0f, 0f, 255f, 1f, 116, 101, 128));
		colorParamInputs.put("blue",  new RangeInput(this, 0f, 0f, 255f, 1f, 116, 118, 128));

		colorParamInputs.put("white", new RangeInput(this, 0f, 0f, 255f, 1f, 116, 138, 128));

		for(Element e : colorParamInputs.values()){
			elements.add(e);
		}

		color = new FlatColor(Colors.WHITE);
	}

	public void tick(){

		if(Math.abs(color.getHue() - colorParamInputs.get("hue").value()) > 0.00011){
			color.setHue(colorParamInputs.get("hue").value());
			updateRGBInputs();
			return;
		}
		if(Math.abs(color.getSaturation() - colorParamInputs.get("sat").value()) > 0.00011){
			color.setSaturation(colorParamInputs.get("sat").value());
			updateRGBInputs();
			return;
		}
		if(Math.abs(color.getBrightness() - colorParamInputs.get("lum").value()) > 0.00011){
			color.setBrightness(colorParamInputs.get("lum").value());
			updateRGBInputs();
			return;
		}

		if(color.getRed() != colorParamInputs.get("red").value()){
			color.setRed(Math.round(colorParamInputs.get("red").value()));
			updateHSLInputs();
			return;
		}
		if(color.getGreen() != colorParamInputs.get("green").value()){
			color.setGreen(Math.round(colorParamInputs.get("green").value()));
			updateHSLInputs();
			return;
		}
		if(color.getBlue() != colorParamInputs.get("blue").value()){
			color.setBlue(Math.round(colorParamInputs.get("blue").value()));
			updateHSLInputs();
			return;
		}
		if(color.getAlpha() != colorParamInputs.get("white").value()){
			color.setAlpha(Math.round(colorParamInputs.get("white").value()));
		}

	}

	public void drawUI(){

		ArrayList<FlatColor> colors = MaShine.bank.getColors();

		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._800);
		canvas.textAlign(PConstants.RIGHT, PConstants.TOP);
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

			int x, y;

			if(i < 154){
				x = 8 + (i % 14) * 17;
				y = 160 + (int) Math.floor(i/14)*17;
			}else{
				x = 8 + (i % 154) * 17;
				y = 138;
			}

			if(hasFocus() && 
				y-1 < mouseY() &&
				mouseY() < y+16 &&
				x-1 < mouseX() &&
				mouseX() < x+16 &&
				MaShine.inputs.getState("mouse.left.press"))
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