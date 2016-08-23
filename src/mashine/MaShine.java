/**
 *  Main program for MaShine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class MaShine extends PApplet{


	public static String VERSION = "devel";

	private static final String[] MAIN_WINDOW = new String[] { "mashine.MaShine" };
	private String lastSavedTo = "";
	private String lastBackupFile = "";

	public static Inputs inputs;
	public static Outputs outputs;
	public static Engine engine;
	public static Scene scene;
	public static Bank bank;
	public static UI ui;
	public static MaShine m;

	public static void main(String[] args) {
		PApplet.main(MAIN_WINDOW);
	}

	public void settings() {
		size(1920, 1080, PApplet.P3D);
		noSmooth();
	}

	public void setup() {
		println("MaShine "+ VERSION + " / procsynth");

		m = this;
		frameRate(50);
		surface.setResizable(true);

		outputs = new Outputs();
		inputs = new Inputs();
		bank = new Bank();
		scene = new Scene();
		engine = new Engine();
		ui = new UI();
	}

	public void importFilters(File folder){if(folder != null) bank.filters.importFolder(folder);}

	public void draw() {
		background(55, 71, 79);
		inputs.poll();
		engine.tick();
		ui.draw();
		outputs.push();
	}

	public void keyPressed(KeyEvent e){
		if (key == ESC) {key = 0;} // prevent window from closing
		inputs.passKeyEvent(e);
	}

	public void keyReleased(KeyEvent e){
		inputs.passKeyEvent(e);
	}

	public void mouseWheel(MouseEvent e){
		inputs.passMouseEvent(e);
	}
	public void mousePressed(MouseEvent e) {
		inputs.passMouseEvent(e);
	}
	public void mouseReleased(MouseEvent e) {
		inputs.passMouseEvent(e);
	}

	private String timestamp(){
		return year()+"-"+month()+"-"+day()+"_"+hour()+"-"+minute()+"-"+second();
	}
}
