/**
 *  Collection of frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import java.io.Serializable;
import java.util.ArrayList;

public class Sequence {
	
	private ArrayList<Frame> frames;

	public Sequence(){
		frames = new ArrayList<Frame>();
		frames.add(new Frame());
	}
	public Sequence(Frame f){
		frames = new ArrayList<Frame>();
		frames.add(f);
	}

	public Frame getFrame(int index){
		if(index >= frames.size())
			index = frames.size() -1;
		return frames.get(index);
	}

	public int getSize(){
		return frames.size();
	}

	public void addFrame(Frame f){
		frames.add(f);
	}

	public void deleteFrame(int index){
		if(index < frames.size() && index >= 0){
			frames.remove(index);
		}
		if(frames.size() == 0){
			addFrame(new Frame());
		}
	}

	public void setFrame(int i, Frame f){
		frames.set(i, f);
	}
}