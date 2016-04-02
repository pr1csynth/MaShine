/**
 *  Collection of frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Sequence implements Serializable{

	private static final long serialVersionUID = 0x5E0E0001L;

	private ArrayList<Frame> frames;
	private String name;
	private String id;

	public Sequence(String name){
		frames = new ArrayList<Frame>();
		frames.add(new Frame());
		this.name = name;
		this.id = UUID.randomUUID().toString();
	}
	public Sequence(String name, Frame f){
		frames = new ArrayList<Frame>();
		frames.add(f);
		this.name = name;
		this.id = UUID.randomUUID().toString();
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

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

	public String getIdentifier(){
		return id;
	}
}