/**
 *  Collection of sequences and filters
 *
 *	@author procsynth - Antoine Pintout
 *	@since  19-03-2016`
 */
package mashine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.io.Serializable;

import java.util.Scanner;

import mashine.utils.*;
import mashine.engine.*;
import mashine.scene.Sequence;
import mashine.scene.Frame;
import mashine.scene.Device;
import mashine.scene.DeviceGroup;
import mashine.scene.features.*;
import mashine.ui.Colors;

import processing.core.PConstants;

public class Bank implements Serializable{

	private ArrayList<Sequence> sequences;
	private ArrayList<FlatColor> colors;
	public ScriptManager filters;

	public Bank(){
		sequences = new ArrayList<Sequence>();
		colors = new ArrayList<FlatColor>();

		sequences.add(new Sequence());

		for(int i = 0; i < 154; i++){
			colors.add(new FlatColor(0xFF, 0x00, 0x00)
				.withHue((i % 14)/(float)14.0)
				.withSaturation((float) Math.floor((167-i)/14)/11)
				.withAlpha(0));
		}

		colors.add(Colors.WHITE.withAlpha(255));
		for(int i = 0; i < 5; i++){
			colors.add(Colors.BLACK.withAlpha(0));
		}

		filters = null;

		try{
			String[] filtersList = new Scanner(getClass().getResourceAsStream("/javascript/filters.list"), "utf-8").useDelimiter("\\A").next().split("\n");
			for(int i = 0; i < filtersList.length; i++) filtersList[i] = "/javascript/filters/"+filtersList[i];
			filters = new ScriptManager(filtersList, "/javascript/filters.js");
		}catch(Exception e){e.printStackTrace();}

	}

	public void addSequence(Sequence seq){sequences.add(seq);}
	public void deleteSequence(Sequence seq){sequences.remove(seq);}
	public ArrayList<Sequence> getSequences(){return sequences;}
	public Sequence getSequence(int index){return sequences.get(index);}
	public int getSequencesSize(){return sequences.size();}

	public ArrayList<FlatColor> getColors(){return colors;}

	public List<String> getFilters(){return filters.getScripts();}
}