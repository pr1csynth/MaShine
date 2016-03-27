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

		tracks.add(new Track(M, "a"));
	}

	public void tick(){
		Frame frame = tracks.get(0).getFrame();
		M.ui.setDisplayedFrame(frame);
		M.outputs.setFrame(frame);
	}

	public ArrayList<Track> getTracks(){
		return tracks;
	}
}