/**
 *  Virtual control surface;
 *
 *	@author procsynth - Antoine Pintout
 *	@since  09-04-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;
import java.lang.Math;

import mashine.MaShine;
import mashine.ui.UIBox;
import mashine.ui.FlatColor;
import mashine.ui.Colors;
import mashine.ui.elements.VerticalRangeInput;

import processing.core.PConstants;

public class ControlSurface extends UIBox{

	private final int nbSliders = 32;
	ArrayList<VerticalRangeInput> sliders;

	public ControlSurface () {
		super("CONTROL SURFACE", 100, 600, 730, 305);

		int offset = 15;
		sliders = new ArrayList<VerticalRangeInput>(nbSliders);

		for(int i = 0; i < nbSliders; i ++){
			VerticalRangeInput vri = new VerticalRangeInput(this, 0f, 0f, 1f, 0.0001f, offset, 35, 256);
			offset += 20;
			sliders.add(vri);
			elements.add(vri);
		}
	}

	public void drawUI(){
		canvas.noStroke();
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(0, 0, width, height);
	}

	public void drawFrame(){

		int offset = 23;

		FlatColor.fill(canvas, Colors.WHITE);
		canvas.textAlign(PConstants.CENTER, PConstants.CENTER);
		for(int i = 0; i < nbSliders; i ++){
			canvas.text(i+1, offset, 44);
			offset += 20;
		}

		canvas.noStroke();
		FlatColor.fill(canvas,Colors.MATERIAL.GREY._900);
		canvas.rect(0, 0, width+1, 22);
		
		FlatColor.fill(canvas,Colors.WHITE);
		canvas.textAlign(PConstants.CENTER, PConstants.TOP);
		canvas.text(title,width / 2, 7);

		for(int i = 0; i < nbSliders; i++){
			MaShine.inputs.setRange("console.slider."+String.format("%2s", i).replace(' ', '0'), sliders.get(i).value());
		}

	}

}