/**
 *  Mother class to generate a frame from a sequence
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import java.io.Serializable;

import mashine.Do;
import mashine.MaShine;
import mashine.scene.Frame;
import mashine.scene.Sequence;

public class Sequencer implements Serializable{

	private static final long serialVersionUID = 0x53050001L;

	private String name;
	private Sequence sequence;
	private boolean tweaking = false;
	private boolean manual = false;
	private boolean loop = true;
	private int clip;
	private int offset;
	private int index;

	public Sequencer(String name, Sequence sequence){
		this.name = name;
		this.sequence = sequence;
		this.clip = 0;
		this.offset = 0;
		this.index = 0;

		MaShine.inputs.link("sequencer."+name+".forward.auto", "minim.beat.interpolated");
		registerActions();
	}

	public void registerActions(){
		MaShine.inputs.registerAction("sequencer."+name+".manual.start", new Do(){public void x(){manual = true;}});
		MaShine.inputs.registerAction("sequencer."+name+".manual.end", new Do(){public void x(){manual = false;}});

		MaShine.inputs.registerAction("sequencer."+name+".reset", new Do(){public void x(){setIndex(clip);}});
		MaShine.inputs.registerAction("sequencer."+name+".loop.toggle", new Do(){public void x(){loop = !loop;}});

		MaShine.inputs.registerAction("sequencer."+name+".clip.less.tweak", new Do(){public void x(){if(tweaking)setClip(clip +1);}});
		MaShine.inputs.registerAction("sequencer."+name+".clip.more.tweak", new Do(){public void x(){if(tweaking)setClip(clip -1);}});
		MaShine.inputs.registerAction("sequencer."+name+".offset.less.tweak", new Do(){public void x(){if(tweaking)setOffset(offset +1);}});
		MaShine.inputs.registerAction("sequencer."+name+".offset.more.tweak", new Do(){public void x(){if(tweaking)setOffset(offset -1);}});

		MaShine.inputs.registerAction("sequencer."+name+".forward.manual", new Do(){public void x(){if(manual)setIndex(index +1);}});
		MaShine.inputs.registerAction("sequencer."+name+".backward.manual", new Do(){public void x(){if(manual)setIndex(index -1);}});

		MaShine.inputs.registerAction("sequencer."+name+".forward.auto", new Do(){public void x(){if(!manual)setIndex(index +1);}});
		MaShine.inputs.registerAction("sequencer."+name+".backward.auto", new Do(){public void x(){if(!manual)setIndex(index -1);}});
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
				if(loop)
					v = offset;
				else
					v = sequence.getSize() - 1;
			}
		}else{
			if(v < offset){
				v = offset + clip - 1;
			}

			if(v >= offset + clip ){
				if(loop)
					v = offset;
				else
					v = (offset + clip) - 1;
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
	public void setSequence(Sequence sequence){
		this.sequence = sequence;
		setIndex(0);
	}

	public String getName(){
		return name;
	}

	public int getClip(){return clip;}
	public int getOffset(){return offset;}
	public int getIndex(){return index;}

	public void startTweak(){
		MaShine.ui.open("SequenceSelector");
		tweaking = true;
	}
	public void endTweak(){
		MaShine.ui.close("SequenceSelector");
		tweaking = false;
	}

	public boolean isTweaked(){
		return tweaking;
	}

	public boolean isManual(){
		return manual;
	}

}