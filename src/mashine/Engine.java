/**
 *  Instance for manipulating tracks, mixer and filters to calculate an ImageFrame/Frame
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import java.util.ArrayList;

import mashine.engine.Track;
import mashine.scene.Frame;

public class Engine{

	private ArrayList<Track> tracks;

	public Engine(){
		tracks = new ArrayList<Track>();

		tracks.add(new Track("A"));
		tracks.add(new Track("B"));
		tracks.add(new Track("C"));
		tracks.add(new Track("D"));
		tracks.add(new Track("E"));
		tracks.add(new Track("F"));

	}

	public void tick(){
		Frame frame = new Frame();

		for(int i = tracks.size() -1; i >= 0; i --){
			frame = Frame.mix(frame, tracks.get(i).getFrame()); 
		}

		MaShine.ui.setDisplayedFrame(frame);
		MaShine.outputs.setFrame(frame);
	}

	public ArrayList<Track> getTracks(){
		return tracks;
	}
}