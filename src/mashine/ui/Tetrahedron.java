/**
 *  Draws and rotate a small Tetrahedron
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import processing.core.*;
import mashine.MaShine;

public class Tetrahedron{
	private PApplet app;
	private int edgeLength;
	private double theta, angleX, angleY, rx, ry;

	public Tetrahedron(int iedgeLength){
		app = MaShine.m;
		edgeLength = iedgeLength;
	}

	public void draw(int x, int y, int z){
		app.translate(x, y, z);
		theta = app.frameCount/PApplet.PI;
		angleX = ((theta)*0.05);
		angleY = ((theta)*0.05);
		app.rotateY((float)angleX);
		app.rotateX((float)angleY);
		app.beginShape(PApplet.TRIANGLE_STRIP);
		app.vertex(edgeLength, edgeLength, edgeLength);
		app.vertex(-edgeLength, -edgeLength, edgeLength);
		app.vertex(-edgeLength, edgeLength, -edgeLength);
		app.vertex(edgeLength, -edgeLength, -edgeLength);
		app.vertex(edgeLength, edgeLength, edgeLength);
		app.vertex(-edgeLength, -edgeLength, edgeLength);
		app.endShape(PApplet.CLOSE);
	}

}