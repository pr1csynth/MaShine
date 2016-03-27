/**
 *  Instance to generate a filtered frame from sequencer
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import mashine.*;
import mashine.scene.*;

import java.util.ArrayList;

public class Track{

	private MaShine M;
	private String name;
	public Sequencer sequencer;
	private ArrayList<Filter> filters;

	public Track(MaShine m, String name){
		this.M = m;
		this.name = name;

		sequencer = new Sequencer(M, name, M.bank.getSequence(0));

	}

	public Frame getFrame(){
		// todo : filter it
		return sequencer.getFrame();
	}
}