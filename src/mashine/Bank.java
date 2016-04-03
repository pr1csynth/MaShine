/**
 *  Collection of sequences and filters
 *
 *	@author procsynth - Antoine Pintout
 *	@since  19-03-2016`
 */
package mashine;

import java.util.ArrayList;
import java.util.HashMap;

import mashine.engine.Filter;
import mashine.scene.Sequence;
import mashine.scene.Frame;
import mashine.scene.features.*;
import mashine.ui.Colors;
import mashine.ui.FlatColor;

public class Bank{

	private ArrayList<Sequence> sequences;
	private HashMap<String, Filter> filters;
	private ArrayList<FlatColor> colors;

	public Bank(){
		sequences = new ArrayList<Sequence>();
		filters = new HashMap<String, Filter>();
		colors = new ArrayList<FlatColor>();

		sequences.add(new Sequence("unamed sequence"));

		for(int i = 0; i < 154; i++){
			colors.add(new FlatColor(0xFF, 0x00, 0x00)
				.withHue((i % 14)/(float)14.0)
				.withBrightness((float) Math.floor((167-i)/14)/11)
				.withAlpha(0));
		}

		colors.add(Colors.WHITE.withAlpha(255));
		for(int i = 0; i < 5; i++){
			colors.add(Colors.BLACK.withAlpha(0));
		}

		filters.put("dimmer", new Filter("dimmer", new Filter.Robot(){
			public void setup(Filter filter){
				filter.declare("dimmer", Filter.RANGE);
			}

			public Frame f(Filter filter, Frame frame){
				Frame filteredFrame = new Frame(frame);
				for(EditableFeature f : frame.getFeatures().values()){
					if(f instanceof ColorFeature){
						//FlatColor
						//f.link()
					}
				}
				return filteredFrame;
			}
		}));

	}

	public void addSequence(Sequence seq){
		sequences.add(seq);
	}
	public void deleteSequence(Sequence seq){
		sequences.remove(seq);
	}

	public ArrayList<Sequence> getSequences(){
		return sequences;
	}

	public Sequence getSequence(int index){
		return sequences.get(index);
	}

	public int getSequencesSize(){
		return sequences.size();
	}

	public ArrayList<FlatColor> getColors(){
		return colors;
	}

	public Filter getFilter(String f){
		return filters.get(f);
	}

	public Object save(){
		HashMap<String,Object> saveObject = new HashMap<String,Object>();
		saveObject.put("sequences", sequences);
		saveObject.put("colors", colors);
		saveObject.put("filters", filters);
		return saveObject;
	}

	public void restore(Object restoredObject){
		HashMap<String,Object> r = (HashMap<String,Object>) restoredObject;
		sequences = (ArrayList<Sequence>) r.get("sequences");
		colors = (ArrayList<FlatColor>) r.get("colors");
		filters = (HashMap<String, Filter>) r.get("filters");
		MaShine.ui.setSelectedSequence(sequences.get(0));
	}
}