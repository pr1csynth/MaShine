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
import java.util.List;
import java.util.Collections;

public class SequenceSelector extends UIBox{

	private Sequence selectedSequence;

	public SequenceSelector () {
		super("SEQUENCES", 950, 50, 150, 350);

		selectedSequence = MaShine.bank.getSequence(0);

		elements.add(new TextButton(this, "new", 0, 310, 55, 
			new Do(){public void x(){newSequence();}}
		));

		elements.add(new TextButton(this, "delete", 96, 330, 55, 
			new Do(){public void x(){deleteSequence();}}
		));
		elements.add(new TextButton(this, "up", 0, 330, 30, 
			new Do(){public void x(){moveUpSequence();}}
		));
		elements.add(new TextButton(this, "down", 32, 330, 40, 
			new Do(){public void x(){moveDownSequence();}}
		));
	}

	public void tick(){
	}

	public void drawUI(){

		canvas.noStroke();
		int offset = 30;
		int index = 0;
		canvas.textAlign(canvas.LEFT, canvas.TOP);
		
		if(MaShine.bank.getSequencesSize() != 0){
			List<Sequence> sequences = MaShine.bank.getSequences();

			for(Sequence s : sequences){
				if(s == selectedSequence){
					FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
				}else{
					if(index % 2 == 0){
						FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._700);
					}else{
						canvas.noFill();
					}
				}
				canvas.rect(1, offset - 3, width - 1, 14);

				if(hasFocus() && offset - 3 < mouseY() && mouseY() < offset + 11 && MaShine.inputs.getState("mouse.left.press")){
					selectedSequence = s;
				}

				FlatColor.fill(canvas,Colors.WHITE);
				canvas.text(s.getName() + " ("+s.getSize()+")", 5, offset);
				offset += 14;
				index ++;
			}
		}else{

			FlatColor.fill(canvas,Colors.WHITE);
			canvas.text("No sequences yet.", 5, offset);

		}
	}

	private void newSequence(){
		selectedSequence = new Sequence("unamed sequence");
		MaShine.bank.addSequence(selectedSequence);
	}

	private void deleteSequence(){
		MaShine.bank.deleteSequence(selectedSequence);
		if(MaShine.bank.getSequencesSize() == 0){
			MaShine.bank.addSequence(new Sequence("unamed sequence"));
		}
		selectedSequence = MaShine.bank.getSequence(0);
	}

	private void nextSequence(){}
	private void prevSequence(){}
	private void moveUpSequence(){
		List<Sequence> sequences = MaShine.bank.getSequences();
		int i = sequences.indexOf(selectedSequence);
		if(i != 0)
			Collections.swap(sequences, i, i - 1);
	}
	private void moveDownSequence(){
		List<Sequence> sequences = MaShine.bank.getSequences();
		int i = sequences.indexOf(selectedSequence);
		if(i != sequences.size() -1)
			Collections.swap(sequences, i, i + 1);
	}
	public Sequence getSelectedSequence(){return selectedSequence;}
	public void setSelectedSequence(Sequence s){selectedSequence = s;}
}