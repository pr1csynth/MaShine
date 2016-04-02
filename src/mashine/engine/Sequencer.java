/**
 *  Mother class to generate a frame from a sequence
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import mashine.*;
import mashine.scene.*;
import java.lang.Math;
import java.io.Serializable;

public class Sequencer implements Serializable{

	private static final long serialVersionUID = 0x53050001L;

	private String name;
	private Sequence sequence;
	private Sequence selectedSequence;
	private Frame frame;
	private boolean tweaking = false;
	private boolean manual = false;
	private int clip;
	private int offset;
	private int index;

	public Sequencer(String name, Sequence sequence){
		this.name = name;
		this.sequence = sequence;
		this.clip = 0;
		this.offset = 0;
		this.index = 0;

		MaShine.inputs.register("sequencer."+name+".tweak.start", new Do(){public void x(){startTweak();}});
		MaShine.inputs.register("sequencer."+name+".tweak.end", new Do(){public void x(){endTweak();}});
		MaShine.inputs.register("sequencer."+name+".manual.start", new Do(){public void x(){manual = true;}});
		MaShine.inputs.register("sequencer."+name+".manual.end", new Do(){public void x(){manual = false;}});

		MaShine.inputs.register("sequencer."+name+".reset", new Do(){public void x(){setIndex(clip);}});

		MaShine.inputs.register("sequencer."+name+".clip.less.tweak", new Do(){public void x(){if(tweaking)setClip(clip +1);}});
		MaShine.inputs.register("sequencer."+name+".clip.more.tweak", new Do(){public void x(){if(tweaking)setClip(clip -1);}});
		MaShine.inputs.register("sequencer."+name+".offset.less.tweak", new Do(){public void x(){if(tweaking)setOffset(offset +1);}});
		MaShine.inputs.register("sequencer."+name+".offset.more.tweak", new Do(){public void x(){if(tweaking)setOffset(offset -1);}});

		MaShine.inputs.register("sequencer."+name+".forward.manual", new Do(){public void x(){if(manual)setIndex(index +1);}});
		MaShine.inputs.register("sequencer."+name+".backward.manual", new Do(){public void x(){if(manual)setIndex(index -1);}});

		MaShine.inputs.register("sequencer."+name+".forward.auto", new Do(){public void x(){if(!manual)setIndex(index +1);}});
		MaShine.inputs.register("sequencer."+name+".backward.auto", new Do(){public void x(){if(!manual)setIndex(index -1);}});

		MaShine.inputs.link("sequencer."+name+".forward.auto", "minim.beat.interpolated");

	}

	public void setClip(int v){
		if(v == 0){
			clip = 0;
		}else{
			clip = Math.max(1, Math.min(v, sequence.getSize() - offset));
		}
	};
	public void setOffset(int v){
		offset = Math.max(0, Math.min(v, sequence.getSize() - 2));
		setClip(clip);
	};
	public void setIndex(int v){
		setOffset(offset);
		if(clip == 0){
			if(v < offset){
				v = sequence.getSize() - 1;
			}
			if(v >= sequence.getSize()){
				v = offset;
			}
		}else{
			if(v < offset){
				v = offset + clip - 1;
			}

			if(v >= offset + clip ){
				v = offset;
			}
		}
		index = v;
	};

	public Frame getFrame(){
		return sequence.getFrame(index);
	}

	public Sequence getSequence(){
		return sequence;
	}

	public String getName(){
		return name;
	}

	public int getClip(){return clip;}
	public int getOffset(){return offset;}
	public int getIndex(){return index;}

	public void startTweak(){
		MaShine.ui.open("SequenceSelector");
		selectedSequence = MaShine.ui.getSelectedSequence(); 
		tweaking = true;
	}
	public void endTweak(){
		if(selectedSequence != MaShine.ui.getSelectedSequence()){
			sequence = MaShine.ui.getSelectedSequence();
			setIndex(0);
		}
		tweaking = false;
	}

	public boolean isTweaked(){
		return tweaking;
	}

	public boolean isManual(){
		return manual;
	}

}