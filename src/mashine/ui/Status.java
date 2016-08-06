/**
 *  Status bar at the bottom of MaShine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

 package mashine.ui;

import mashine.MaShine;

import mashine.engine.*;
import processing.core.PApplet;
import processing.data.StringDict;

// TODO, use drawable

public class Status extends Block{

	private StringDict status;
	private Tetrahedron tetrahedron;

	private int beatFade = 255;

	public Status(){
		status = new StringDict();
		tetrahedron = new Tetrahedron(6);

		nodes();
	}

	private void nodes(){
		/* controls in 	//	controls out
		- tetra_fill
		- tetra_line
		- status

		/* content in 	//	content out
		
		*/
		controlIn.put("tetra_fill", new InNode(0.0));
		controlIn.put("tetra_line", new InNode(false));
		controlIn.put("status", 	new InNode(0.0));
	}

	public void draw(){
		MaShine.m.noStroke();
		if((Double)get("status") < 0)
			MaShine.m.fill(84, 110, 122);
		else
			MaShine.m.fill(0xD5, 0, 0);
		MaShine.m.rect(0, MaShine.m.height - 22, MaShine.m.width, 22);


		String s = "";
		for(String key : status.keys()){
			s += key +": "+ status.get(key) + "  ";
		}
		MaShine.m.fill(255, 255, 255);
		MaShine.m.text(s.toLowerCase(), 4, MaShine.m.height - 7);
		MaShine.m.textAlign(PApplet.RIGHT);
		MaShine.m.text(PApplet.round(MaShine.m.frameRate), MaShine.m.width - 29, MaShine.m.height - 7);
		MaShine.m.text(PApplet.round(MaShine.engine.tickRate()), MaShine.m.width - 79, MaShine.m.height - 7);
		MaShine.m.textAlign(PApplet.LEFT);

		MaShine.m.ortho();

		if((Boolean)get("tetra_line")){
			beatFade = 255;
		}

		MaShine.m.stroke(0x64, 0xFF, 0xDA, beatFade);
		MaShine.m.strokeWeight((float)1.1);
		MaShine.m.fill(0, getFloat("tetra_fill")*255);
		//tetrahedron.draw(MaShine.m.width - 13, MaShine.m.height - 11, 120);

		beatFade = Math.max(50, beatFade - 15);
	}
	
	public void set(String key, String content){
		status.set(key, content);
	}

	public void remove(String key){
		status.remove(key);
	}

}