/**
 *  Mother class to generate a frame from a sequence
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import java.io.Serializable;

import mashine.utils.Do;
import mashine.MaShine;
import mashine.scene.Frame;
import mashine.scene.Sequence;

public class Sequencer extends Block{

	private int index;
	private Sequence sequence;

	public Sequencer(){
		this(0, 0);
	}

	public Sequencer(int x, int y){
		super(x, y);
		this.index = 0;	

		nodes();
	}

	private void nodes(){
		/* controls in 	//	controls out
		- forward			- changed
		- backward 			- seq_size
		- reset 			- index
		- loop 				- at_end

		/* content in 	//	content out
		- sequence 			- frame
		
		*/

		controlIn.put("forward", 	new InNode<Boolean>(false));
		controlIn.put("backward", 	new InNode<Boolean>(false));
		controlIn.put("reset",	 	new InNode<Boolean>(false));
		controlIn.put("loop",	 	new InNode<Boolean>(true));

		contentIn.put("sequence", 	new InNode<Sequence>(new Sequence()));

		controlOut.put("changed",	new OutNode<Boolean>(this, false));
		controlOut.put("at_end",	new OutNode<Boolean>(this, false));
		controlOut.put("seq_size",	new OutNode<Double>(this, 0.0));
		controlOut.put("index",		new OutNode<Double>(this, 0.0));

		contentOut.put("frame", 	new OutNode<Frame>(this, new Frame()));
	}

	public void tick(){
		int lastIndex = index;
		sequence = (Sequence) get("sequence");
		setIndex(index);
		if((Boolean)get("forward")) setIndex(index+1);
		if((Boolean)get("backward")) setIndex(index-1);
		if((Boolean)get("reset")) setIndex(0);

		set("changed", lastIndex != index);
		set("index", index);
		set("seq_size", sequence.getSize());
		set("at_end", index == sequence.getSize()-1);
		set("frame", sequence.getFrame(index));
	}

	private void setIndex(int v){
		if(v < 0){
			v = sequence.getSize() - 1;
		}else if(v >= sequence.getSize()){
			if((Boolean)get("loop"))
				v = 0;
			else
				v = sequence.getSize() - 1;
		}
		index = v;
	}
}