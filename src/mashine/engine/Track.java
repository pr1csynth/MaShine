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
	private int filterIndex; 

	private boolean tweaked = false;

	public Track(String name){
		this.name = name;
		sequencer = new Sequencer(name, MaShine.bank.getSequence(0));
		filters = new ArrayList<Filter>();

		String filterName = addFilter("dimmer");
		if(filterName != null){
			MaShine.inputs.state(filterName+".enabled", "_true");
			MaShine.inputs.range(filterName+".value", "_100");
		}
		registerActions();
	}

	public void registerActions(){
		MaShine.inputs.registerAction("track."+name+".tweak.start", new Do(){public void x(){startTweak();}});
		MaShine.inputs.registerAction("track."+name+".tweak.end", new Do(){public void x(){endTweak();}});
		sequencer.registerActions();
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

	public String addFilter(String scriptName){
		if(MaShine.bank.getFilters().contains(scriptName)){
			filterIndex ++;
			String name = "track."+this.name+".filter."+hex(filterIndex);
			Filter f = new Filter(name, scriptName);
			filters.add(f);
			return name+"."+scriptName;
		}
		return null;
	}

	public ArrayList<Filter> getFilters(){return filters;}
	public String getName(){return name;}

	public boolean isTweaked(){return tweaked;}
	public void startTweak(){sequencer.startTweak(); tweaked = true;}
	public void endTweak(){sequencer.endTweak();tweaked = false;}

	public static String hex(int n) {
		return String.format("%2s", Integer.toHexString(n)).replace(' ', '0');
	}
}