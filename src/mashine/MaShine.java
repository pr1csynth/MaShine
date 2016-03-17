/**
 *  Main program for MaShine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import processing.core.*;
import processing.event.*;
import mashine.ui.FlatColor;

public class MaShine extends PApplet{

	private static final String[] MAIN_WINDOW = new String[] { "mashine.MaShine" };

	public Inputs inputs;
	public Outputs outputs;
	public Scene scene;
	public UI ui;

	public static void main(String[] args) {
		PApplet.main(MAIN_WINDOW);
	}

	public void settings() {
		size(1200, 700, PApplet.P3D);
		noSmooth();
	}

	public void setup() {
		frameRate(60);
		//surface.setResizable(true);


		inputs = new Inputs(this);
		outputs = new Outputs(this);
		scene = new Scene(this);
		ui = new UI(this);

		inputs.register("mashine.test", new Do(){public void x(){println("TEST");}});
		inputs.register("mashine.save_scene", new Do(){public void x(){scene.save();}});
		inputs.register("mashine.restore_scene", new Do(){public void x(){scene.restore();}});
		inputs.link("mashine.save_scene", "keyboard.97.press");
		inputs.link("mashine.restore_scene", "keyboard.98.press");
	}

	public void draw() {
		background(55, 71, 79);
		inputs.poll();
		outputs.push();
		ui.draw();
	}

	public void keyPressed(KeyEvent e){
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
}
