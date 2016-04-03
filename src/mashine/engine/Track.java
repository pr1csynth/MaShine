/**
 *  Instance to generate a filtered frame from sequencer
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import java.io.Serializable;
import java.util.ArrayList;

import mashine.MaShine;
import mashine.Do;
import mashine.scene.Frame;

public class Track implements Serializable{

	private static final long serialVersionUID = 0x1A0F0001L;

	private String name;
	public Sequencer sequencer;
	private ArrayList<Filter> filters;

	private boolean tweaked = false;

	public Track(String name){
		this.name = name;
		sequencer = new Sequencer(name, MaShine.bank.getSequence(0));
		filters = new ArrayList<Filter>();

		Filter dimmer = new Filter("track."+name, MaShine.bank.getFilter("dimmer"));
		MaShine.inputs.state("track."+name+".filter.dimmer.enabled", "_true");
		MaShine.inputs.range("track."+name+".filter.dimmer.value", "_100");

		MaShine.inputs.registerAction("track."+name+".tweak.start", new Do(){public void x(){startTweak();}});
		MaShine.inputs.registerAction("track."+name+".tweak.end", new Do(){public void x(){endTweak();}});

		filters.add(dimmer);
	}

	public Frame getFrame(){
		Frame frame = new Frame(sequencer.getFrame());
		for(Filter f : filters){
			if(f.isEnabled()){
				frame = f.filter(frame);
			}
		}
		return frame;
	}

	public boolean isTweaked(){
		return tweaked;
	}

	public void startTweak(){
		sequencer.startTweak();
		tweaked = true;
	}
	public void endTweak(){
		sequencer.endTweak();
		tweaked = false;
	}
}