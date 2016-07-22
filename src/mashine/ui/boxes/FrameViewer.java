/**
 *  Interface to view frame properties
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui.boxes;

import mashine.ui.UIBox;

public class FrameViewer extends UIBox{

	public FrameViewer () {
		super("FRAME VIEWER", 50, 50, 300, 600);
	}

	public void drawUI(){
		// canvas.noStroke();
		// int offset = 30;
		// canvas.textAlign(canvas.LEFT, canvas.TOP);
		// ArrayList<String> stateInputsName = new ArrayList(M.inputs.getStateSet());
		// Collections.sort(stateInputsName);

		// for(String a : stateInputsName){
		// 	if(M.inputs.getState(a)){
		// 		highlight.put(a, 255);
		// 	}

		// 	if(!highlight.containsKey(a)){
		// 		highlight.put(a, 0);
		// 	}
			
		// 	FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400.withAlpha(highlight.get(a)));
		// 	canvas.rect(1, offset - 3, width - 1, 14);

		// 	FlatColor.fill(canvas,Colors.WHITE);
		// 	canvas.text(a, 5, offset);
		// 	offset += 14;
		// }

		// for(String h : highlight.keySet()){
		// 	Integer newVal;
		// 	if(h.contains("encod") || h.contains("beat") ){
		// 		newVal = highlight.get(h) - 35;
		// 	}else{
		// 		newVal = highlight.get(h) - 10;
		// 	}
		// 	newVal = M.max(0, newVal);
		// 	highlight.put(h, newVal);
		//}


		//canvas.rect(mouseX(), mouseY(), 20, 20);
	}

}