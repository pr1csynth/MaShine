/**
 *  Main program for MaShine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import mashine.engine.Engine;
import mashine.scene.Scene;
import mashine.ui.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class MaShine extends PApplet{

	public static String VERSION = "devel";

	private static final String[] MAIN_WINDOW = new String[] { "mashine.MaShine" };

	public static Engine engine;
	public static Scene scene;
	public static Bank bank;
	public static UI ui;
	public static MaShine m;

	public Boolean dragging = false;

	public static void main(String[] args) {
		PApplet.main(MAIN_WINDOW);
	}

	public void settings() {
		//size(1920, 1080, PApplet.P3D);
		size(1920, 1080, PApplet.P2D);
		//noSmooth();
	}

	public void setup() {
		println("MaShine "+ VERSION + " / procsynth");

		m = this;
		frameRate(50);
		surface.setResizable(true);

		bank = new Bank();
		scene = new Scene();
		engine = new Engine();
		ui = new UI();
	}

	public void draw() {
		background(0x26, 0x32, 0x38); // BLUE_GREY._900
		ui.draw();
		if(dragging){
			ui.grid.drag(new PVector(mouseX - pmouseX, mouseY - pmouseY)); 
		}
	}

	public void keyPressed(KeyEvent e){
		if (key == ESC) {key = 0;} // prevent window from closing
		//inputs.passKeyEvent(e);
	}

	public void keyReleased(KeyEvent e){
		//inputs.passKeyEvent(e);
	}

	public void mousePressed(){
		dragging = true;
	}

	public void mouseReleased(){
		dragging = false;
		ui.grid.click();
	}

	public void mouseWheel(MouseEvent e){
		ui.grid.zoom(e.getCount());
	}

	private String timestamp(){
		return year()+"-"+month()+"-"+day()+"_"+hour()+"-"+minute()+"-"+second();
	}
}
