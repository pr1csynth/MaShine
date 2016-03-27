/**
 *  Status bar at the bottom of MaShine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

 package mashine.ui;

import mashine.*;
import mashine.ui.elements.*;
import processing.core.*;
import processing.data.StringDict;
import mashine.ui.Tetrahedron;

// TODO, use drawable

public class Status{

	private MaShine M;
	private StringDict status;
	private Tetrahedron tetrahedron;

	private int beatFade = 255;

	public Status(MaShine m){
		M = m;
		status = new StringDict();
		tetrahedron = new Tetrahedron(6, M);
	}

	public void draw(){
		M.noStroke();
		if(M.inputs.getState("internal.ola.status"))
			M.fill(84, 110, 122);
		else
			M.fill(0xD5, 0, 0);
		M.rect(0, M.height - 22, M.width, 22);


		String s = "";
		for(String key : status.keys()){
			s += key +": "+ status.get(key) + "  ";
		}
		M.fill(255, 255, 255);
		M.text(s, 4, M.height - 8);

		M.textAlign(PApplet.RIGHT);
		M.text(PApplet.round(M.frameRate), M.width - 29, M.height - 7);
		M.textAlign(PApplet.LEFT);

		M.ortho();

		if(M.inputs.getState("minim.beat.interpolated")){
			beatFade = 255;
		}

		M.stroke(0x64, 0xFF, 0xDA, beatFade);
		M.strokeWeight((float)1.1);
		M.fill(0, M.inputs.getRange("minim.rms")*255);
		tetrahedron.draw(M.width - 13, M.height - 11, 120);

		beatFade = M.max(50, beatFade - 15);
	}
	
	public void set(String key, String content){
		status.set(key, content);
	}

	public void remove(String key){
		status.remove(key);
	}

}