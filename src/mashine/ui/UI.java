/**
 *  UI managing instance
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.Serializable;

import mashine.MaShine;
import mashine.utils.*;

import mashine.scene.Device;
import mashine.scene.DeviceGroup;
import mashine.scene.Frame;
import mashine.scene.Sequence;

import processing.core.PFont;


public class UI{

	public Status status;
	public Grid grid;

	public PFont TEXTFONT;
	public PFont TITLEFONT;
	public int TEXTSIZE;
	public int TITLESIZE;

	public UI(){
		MaShine.m.colorMode(MaShine.RGB);

		TEXTFONT = MaShine.m.loadFont("data/RobotoMono-Light-11.vlw");
		TEXTSIZE = 11;

		MaShine.m.textFont(TEXTFONT);
		MaShine.m.textSize(TEXTSIZE);

		grid = new Grid();
		status = new Status();
	}

	public void draw(){
		grid.draw();
		status.draw();
	}
}

