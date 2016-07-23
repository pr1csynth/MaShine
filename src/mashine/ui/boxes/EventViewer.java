/**
 *  Interface to link actions to inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mashine.MaShine;
import mashine.ui.Colors;
import mashine.utils.FlatColor;
import processing.core.PConstants;

public class EventViewer extends UIBox{

	private HashMap<String,Integer> highlight;

	public EventViewer () {
		super("EVENT VIEWER", 50, 50, 200, 400);
		highlight = new HashMap<String, Integer>();
	}

	public void drawUI(){
		canvas.noStroke();
		int offset = 30;
		canvas.textAlign(PConstants.LEFT, PConstants.TOP);
		ArrayList<String> stateInputsName = new ArrayList<String>(MaShine.inputs.getStateInputSet());
		Collections.sort(stateInputsName);

		for(String a : stateInputsName){
			if(MaShine.inputs.getState(a)){
				highlight.put(a, 255);
			}

			if(!highlight.containsKey(a)){
				highlight.put(a, 0);
			}
			
			FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400.withAlpha(highlight.get(a)));
			canvas.rect(1, offset - 3, width - 1, 14);

			FlatColor.fill(canvas,Colors.WHITE);
			canvas.text(a, 5, offset);
			offset += 14;
		}

		setVirtualHeight(offset);

		for(String h : highlight.keySet()){
			Integer newVal;
			if(h.contains("encod") || h.contains("beat") ){
				newVal = highlight.get(h) - 45;
			}else{
				newVal = highlight.get(h) - 10;
			}
			newVal = Math.max(0, newVal);
			highlight.put(h, newVal);
		}


		//canvas.rect(mouseX(), mouseY(), 20, 20);
	}

}