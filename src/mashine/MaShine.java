/**
 *  Main program for MaShine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import processing.core.*;
import processing.event.*;
import java.io.File;
import mashine.ui.FlatColor;
import java.util.HashMap;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class MaShine extends PApplet{

	private static final String[] MAIN_WINDOW = new String[] { "mashine.MaShine" };

	public Inputs inputs;
	public Outputs outputs;
	public Scene scene;
	public Bank bank;
	public UI ui;

	public static void main(String[] args) {
		PApplet.main(MAIN_WINDOW);
	}

	public void settings() {
		size(1200, 700, PApplet.P3D);
		noSmooth();
	}

	public void setup() {
		frameRate(40);
		//surface.setResizable(true);


		inputs = new Inputs(this);
		outputs = new Outputs(this);
		scene = new Scene(this);
		bank = new Bank(this);
		ui = new UI(this);

		inputs.register("mashine.test", new Do(){public void x(){println("TEST");}});
		inputs.register("mashine.save_scene", new Do(){public void x(){selectOutput("Select file to save to.", "saveTo");}});
		inputs.register("mashine.restore_scene", new Do(){public void x(){selectInput("Select file from which restore.", "restoreFrom");}});
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

	public void saveTo(File file){
		if(file != null){
			HashMap<String, Object> saveObject = new HashMap<String, Object>();
			saveObject.put("scene", scene.save());

			try{
				FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(saveObject);
				out.close();
				fileOut.close();
				println("Serialized data is saved in "+file.getAbsolutePath());
			}catch(IOException i){
				i.printStackTrace();
			}
		}
	}

	public void restoreFrom(File file){
		if(file != null){
			try
			{
				FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
				ObjectInputStream in = new ObjectInputStream(fileIn);
				HashMap<String,Object> restoredObject = (HashMap<String,Object>) in.readObject();
				in.close();
				fileIn.close();

				scene.restore(restoredObject.get("scene"));

			}catch(IOException i)
			{
				i.printStackTrace();
				return;
			}catch(ClassNotFoundException c)
			{
				System.out.println("Some class not found");
				c.printStackTrace();
				return;
			}
		}
		
	}
}
