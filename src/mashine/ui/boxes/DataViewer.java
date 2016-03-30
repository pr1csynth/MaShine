/**
 *  Interface to visualize range inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import java.util.*;
import java.lang.Math;

public class DataViewer extends UIBox{

	public DataViewer (MaShine m) {
		super(m, "DATA VIEWER ", 360, 50, 300, 600);

	}

	public void drawUI(){
		canvas.noStroke();
		canvas.textAlign(M.LEFT, M.TOP);
		int offset = 30;
		ArrayList<String> rangeInputsName = new ArrayList(M.inputs.getRangeInputSet());
		Collections.sort(rangeInputsName);


		for(String a : rangeInputsName){
			float val = M.inputs.getRange(a);

			// if 0.0 to 1.0 range
			FlatColor.fill(canvas, Colors.MATERIAL.ORANGE.A400);
			if(val <= 1 && (val % 1 != 0)){
				canvas.rect(1, offset - 3, M.min(width-1,(int) (width * val)), 14);

			// if 0-127 range (midi) 
			}else{
				canvas.rect(1, offset - 3, M.min(width-1,(int) (width * (float) (val/256))), 14);
			}
			FlatColor.fill(canvas, Colors.WHITE);
			canvas.text(a + " " + (float) Math.round(val*100000)/100000 , 5, offset);
			offset += 14;
		}
	}

}