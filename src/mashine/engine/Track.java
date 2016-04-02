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
import mashine.scene.Frame;

public class Track implements Serializable{

	private static final long serialVersionUID = 0x1A0F0001L;

	private String name;
	public Sequencer sequencer;
	private ArrayList<Filter> filters;

	public Track(String name){
		this.name = name;
		sequencer = new Sequencer(name, MaShine.bank.getSequence(0));
	}

	public Frame getFrame(){
		// todo : filter it
		return sequencer.getFrame();
	}
}