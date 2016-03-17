/**
 *  Collection of frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import mashine.*;
import java.util.ArrayList;
import java.io.Serializable;

public class Sequence implements Serializable{

	private static final long serialVersionUID = 0x5E0E0001L;

	private ArrayList<Frame> frames;

	public Sequence(){
		frames = new ArrayList<Frame>();
	}
	public Sequence(Frame f){
		frames = new ArrayList<Frame>();
		frames.add(f);
	}

	public Frame getFrame(int index){
		return frames.get(index);
	}

	public int getSize(){
		return frames.size();
	}

	public void addFrame(Frame f){
		frames.add(f);
	}

	public void setFrame(int i, Frame f){
		frames.set(i, f);
	}
}