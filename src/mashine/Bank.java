/**
 *  Collection of sequences and filters
 *
 *	@author procsynth - Antoine Pintout
 *	@since  19-03-2016`
 */
package mashine;

import mashine.scene.Frame;
import mashine.scene.*;
import mashine.engine.*;
import mashine.ui.Colors;
import mashine.ui.FlatColor;
import mashine.scene.features.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Bank{

	private MaShine M;
	private ArrayList<Sequence> sequences;
	private ArrayList<Filter> filters;
	private ArrayList<FlatColor> colors;

	public Bank(MaShine m){
		M = m;
		sequences = new ArrayList<Sequence>();
		filters = new ArrayList<Filter>();
		colors = new ArrayList<FlatColor>();
		sequences.add(new Sequence("unamed sequence"));

		for(int i = 0; i < 154; i++){
			colors.add(Colors.MATERIAL.RED.A400.withHue((i % 14)/(float)14).withBrightness(M.floor((167-i)/14)/(float)11).withAlpha(0));
		}
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
		filters = (ArrayList<Filter>) r.get("filters");
		M.ui.setSelectedSequence(sequences.get(0));
	}
}