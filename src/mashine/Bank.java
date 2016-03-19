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
import java.util.LinkedHashMap;
import java.util.HashMap;

public class Bank{

	private MaShine M;
	private LinkedHashMap<String,Sequence> sequences;
	private HashMap<String,Filter> filters;

	public Bank(MaShine m){
		M = m;
		sequences = new LinkedHashMap<String,Sequence>();
		filters = new HashMap<String,Filter>();
	}

	public void newSequence(String name){}
	public void delSequence(String name){}
	public void renameSequence(String oldName, String newName){}

	public LinkedHashMap<String,Sequence> getSequences(){
		return sequences;
	}

	public Sequence getSequence(String name){
		return sequences.get(name);
	}

	public int getSequencesSize(){
		return sequences.size();
	}
}