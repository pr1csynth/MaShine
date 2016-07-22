/**
 *  Instance for manipulating tracks, mixer and filters to calculate an ImageFrame/Frame
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import java.util.ArrayList;
import java.util.HashMap;

import mashine.engine.Track;
import mashine.engine.Filter;
import mashine.scene.Frame;

public class Engine{

	private ArrayList<Track> tracks;
	private ArrayList<Filter> filters;

	private int filterIndex;

	public Engine(){
		tracks = new ArrayList<Track>();
		filters = new ArrayList<Filter>();

		tracks.add(new Track("A"));
		tracks.add(new Track("B"));
		tracks.add(new Track("C"));
		tracks.add(new Track("D"));
		tracks.add(new Track("E"));
		tracks.add(new Track("F"));

		String dimmerName = addFilter("dimmer");
		if(dimmerName != null){
			MaShine.inputs.state(dimmerName+".enabled", "_true");
			MaShine.inputs.range(dimmerName+".value", "_100");
		}
		addFilter("blackout");
		addFilter("whiteout");

	}

	public void tick(){
		Frame frame = new Frame();

		for(int i = 0; i < tracks.size(); i ++){
			frame = Frame.mix(frame, tracks.get(i).getFrame()); 
		}

		for(Filter f : filters){
			if(f.isEnabled()){
				frame = f.filter(frame);
			}
		}

		MaShine.ui.setDisplayedFrame(frame);
		MaShine.outputs.setFrame(frame);
	}

	public ArrayList<Track> getTracks(){return tracks;}
	public ArrayList<Filter> getFilters(){return filters;}

	public String addFilter(String type){
		if(MaShine.bank.getFilter(type) != null){
			filterIndex ++;
			String name = "mixer.filter."+hex(filterIndex);
			Filter f = new Filter(name, MaShine.bank.getFilter(type));
			filters.add(f);
			return name+"."+type;
		}
		return null;
	}

	public static String hex(int n) {
		return String.format("%2s", Integer.toHexString(n)).replace(' ', '0');
	}

	public Object save(){
		HashMap<String,Object> saveObject = new HashMap<String,Object>();
		saveObject.put("tracks", tracks);
		saveObject.put("filters", filters);
		return saveObject;
	}

	public void restore(Object restoredObject){
		HashMap<String, Object> r = (HashMap<String, Object>) restoredObject;
		filters = (ArrayList<Filter>) r.get("filters");
		tracks = (ArrayList<Track>) r.get("tracks");
		for(Track t : tracks){
			t.registerActions();
			for(Filter f : t.getFilters()){
				f.redeclare();
				f.registerActions();
			}
		}
		for(Filter f : filters){
			f.redeclare();
			f.registerActions();
		}
	}
}