/**
 *  Interface to link actions to inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  30-03-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import java.util.*;

public class Linker extends UIBox{


	public Linker (MaShine m) {
		super(m, "LINKS", 50, 50, 300, 500, 1500);
	}

	public void drawUI(){
		canvas.noStroke();
		canvas.textAlign(canvas.LEFT, canvas.TOP);
		HashMap<String,String> actionLinks = M.inputs.getActionLinks();
		HashMap<String,String> rangeLinks = M.inputs.getRangeLinks();

		ArrayList<String> actionSet = new ArrayList<String>(M.inputs.getActionSet());
		ArrayList<String> rangeSet = new ArrayList<String>(M.inputs.getRangeSet());
		Collections.sort(actionSet);
		Collections.sort(rangeSet);

		int offset = 30;
		int index = 0;
		for(String a : actionSet){
			
			if(index % 2 == 0){
				FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._700);
				canvas.rect(1, offset - 3, width - 1, 14);
			}

			FlatColor.fill(canvas,Colors.WHITE);
			canvas.text(a, 5, offset);
			offset += 14;
			index ++;
		}

		offset += 10;

		for(String a : rangeSet){
			
			if(index % 2 == 0){
				FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._700);
				canvas.rect(1, offset - 3, width - 1, 14);
			}

			FlatColor.fill(canvas,Colors.WHITE);
			canvas.text(a, 5, offset);
			offset += 14;
			index ++;
		}

		setVirtualHeight(offset);
	}

}