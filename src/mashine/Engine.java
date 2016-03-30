/**
 *  Instance for manipulating tracks, mixer and filters to calculate an ImageFrame/Frame
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import mashine.scene.*;
import mashine.engine.*;
import java.util.ArrayList;

public class Engine{

	public MaShine M;

	private ArrayList<Track> tracks;

	public Engine(MaShine m){
		this.M = m;
		tracks = new ArrayList<Track>();

		tracks.add(new Track(M, "A"));
		tracks.add(new Track(M, "B"));
		tracks.add(new Track(M, "C"));
		tracks.add(new Track(M, "D"));
		tracks.add(new Track(M, "E"));
		tracks.add(new Track(M, "F"));

	}

	public void tick(){
		Frame frame = new Frame();

		for(int i = tracks.size() -1; i >= 0; i --){
			frame = Frame.mix(frame, tracks.get(i).getFrame()); 
		}

		M.ui.setDisplayedFrame(frame);
		M.outputs.setFrame(frame);
	}

	public ArrayList<Track> getTracks(){
		return tracks;
	}
}