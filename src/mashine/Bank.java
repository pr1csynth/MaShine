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
import mashine.scene.features.*;
import java.util.ArrayList;

public class Bank{

	private MaShine M;
	private ArrayList<Sequence> sequences;
	private ArrayList<Filter> filters;

	public Bank(MaShine m){
		M = m;
		sequences = new ArrayList<Sequence>();
		filters = new ArrayList<Filter>();
		sequences.add(new Sequence("unamed sequence"));
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
}