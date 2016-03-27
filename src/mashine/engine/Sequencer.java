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

public class Sequencer{

	private MaShine M;
	private String name;
	private Sequence sequence;
	private Frame frame;
	private boolean tweaking = false;
	private boolean manual = false;
	private int clip;
	private int offset;
	private int index;

	public Sequencer(MaShine m, String name, Sequence sequence){
		this.M = m;
		this.name = name;
		this.sequence = sequence;
		this.clip = 0;
		this.offset = 0;
		this.index = 0;

		M.inputs.register("sequencer."+name+".tweak.start", new Do(){public void x(){tweaking = true;}});
		M.inputs.register("sequencer."+name+".tweak.end", new Do(){public void x(){tweaking = false;}});
		M.inputs.register("sequencer."+name+".manual.start", new Do(){public void x(){manual = true;}});
		M.inputs.register("sequencer."+name+".manual.end", new Do(){public void x(){manual = false;}});

		M.inputs.register("sequencer."+name+".clip.less.tweak", new Do(){public void x(){if(tweaking)setClip(clip +1);}});
		M.inputs.register("sequencer."+name+".clip.more.tweak", new Do(){public void x(){if(tweaking)setClip(clip -1);}});
		M.inputs.register("sequencer."+name+".offset.less.tweak", new Do(){public void x(){if(tweaking)setOffset(offset +1);}});
		M.inputs.register("sequencer."+name+".offset.more.tweak", new Do(){public void x(){if(tweaking)setOffset(offset -1);}});

		M.inputs.register("sequencer."+name+".forward.manual", new Do(){public void x(){if(manual)setIndex(index +1);}});
		M.inputs.register("sequencer."+name+".backward.manual", new Do(){public void x(){if(manual)setIndex(index -1);}});

		M.inputs.register("sequencer."+name+".forward.auto", new Do(){public void x(){if(!manual)setIndex(index +1);}});
		M.inputs.register("sequencer."+name+".backward.auto", new Do(){public void x(){if(!manual)setIndex(index -1);}});

		M.inputs.link("sequencer."+name+".forward.auto", "minim.beat.interpolated");

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

}