/**
 *  Interface to visualize range inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;
import java.util.Collections;

import mashine.MaShine;
import mashine.ui.Colors;
import mashine.utils.FlatColor;
import mashine.ui.UIBox;

public class DataViewer extends UIBox{

	public DataViewer () {
		super("DATA VIEWER ", 360, 50, 200, 400);
	}

	public void drawUI(){
		canvas.noStroke();
		canvas.textAlign(MaShine.LEFT, MaShine.TOP);
		int offset = 30;
		ArrayList<String> rangeInputsName = new ArrayList<String>(MaShine.inputs.getRangeInputSet());
		Collections.sort(rangeInputsName);


		for(String a : rangeInputsName){
			double val = MaShine.inputs.getRange(a);

			// if 0.0 to 1.0 range
			FlatColor.fill(canvas, Colors.MATERIAL.ORANGE.A400);
			if(val <= 1 && (val % 1 != 0)){
				canvas.rect(1, offset - 3, Math.min(width-1,(int) (width * val)), 14);

			// if 0-127 range (midi) 
			}else{
				canvas.rect(1, offset - 3, Math.min(width-1,(int) (width * (double) (val/256))), 14);
			}
			FlatColor.fill(canvas, Colors.WHITE);
			canvas.text(a + " " + (float) Math.round(val*100000)/100000 , 5, offset);
			offset += 14;
		}

		setVirtualHeight(offset);
	}

}