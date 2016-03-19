/**
 *  Interface to edit sequences of animation
 *
 *	@author procsynth - Antoine Pintout
 *	@since  18-03-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.elements.*;
import mashine.scene.*;
import mashine.scene.features.*;

public class SequenceEditor extends UIBox{

	public SequenceEditor (MaShine m) {
		super(m, "ANIM EDITOR", 650, 50, 270, 450);

		elements.add(new Checkbox(this, 5, 30));
	}

	public void tick(){
	}

	public void drawUI(){

	}

	public void newSequence(){
		Sequence newSeq = new Sequence();
		M.bank.addSequence(newSeq);
		M.ui.setSelectedSequence(newSeq);
	}
}