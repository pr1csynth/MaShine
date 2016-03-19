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
import java.util.Map;

public class SequenceSelector extends UIBox{

	private Sequence selectedSequence;

	public SequenceSelector (MaShine m) {
		super(m, "SEQUENCES", 950, 50, 150, 350);
	}

	public void tick(){
	}

	public void drawUI(){

		canvas.noStroke();
		int offset = 30;
		int index = 0;
		canvas.textAlign(canvas.LEFT, canvas.TOP);
		
		if(M.bank.getSequencesSize() != 0){
			Map<String,Sequence> sequences = M.bank.getSequences();


			for(String s : sequences.keySet()){
				if(selectedSequence == selectedSequence){
					FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
				}else{
					if(index % 2 == 1){
						FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._700);
					}else{
						canvas.noFill();
					}
				}
				canvas.rect(1, offset - 3, width - 1, 14);

				FlatColor.fill(canvas,Colors.WHITE);
				canvas.text(s, 5, offset);
				offset += 14;
				index ++;
			}
		}else{

			FlatColor.fill(canvas,Colors.WHITE);
			canvas.text("No sequences yet.", 5, offset);

		}
	}

	private void newSequence(){}
	private void deleteSequence(){}
	private void nextSequence(){}
	private void prevSequence(){}
	public Sequence getSelectedSequence(){
		return selectedSequence;
	}
}