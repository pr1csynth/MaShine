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
		size(1200, 700, PApplet.P3D);
		noSmooth();
	}

	public void setup() {
		m = this;
		frameRate(50);
		surface.setResizable(true);

		inputs = new Inputs();
		outputs = new Outputs();
		bank = new Bank();
		engine = new Engine();
		scene = new Scene();
		ui = new UI();

		inputs.register("mashine.test", new Do(){public void x(){println("TEST");}});
		inputs.register("mashine.save_as", new Do(){public void x(){save();}});
		inputs.register("mashine.open", new Do(){public void x(){restore();}});
		inputs.register("mashine.save", new Do(){public void x(){save(lastSavedTo);}});
		inputs.register("mashine.restore", new Do(){public void x(){restore(lastBackupFile);}});
		inputs.link("mashine.save", "keyboard.97.press");
		inputs.link("mashine.open", "keyboard.98.press");
		inputs.link("mashine.restore", "keyboard.99.press");
	}

	public void draw() {
		background(55, 71, 79);
		inputs.poll();
		engine.tick();
		ui.draw();
		outputs.push();
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

	public void save(){
		println("Requesting path to save to.");
		selectOutput("Select file to save to.", "save");
	}

	public void save(File file){
		if(file != null)
			save(file.getAbsolutePath());
	}

	public void save(String path){

		if(path.equals("")){
			save();
			return;
		}

		if(!path.equals(lastBackupFile)){
			lastSavedTo = path;
			ui.status.set("file", path.split("/")[path.split("/").length -1]);
		}

		HashMap<String, Object> saveObject = new HashMap<String, Object>();
		saveObject.put("scene", scene.save());
		saveObject.put("bank", bank.save());
		saveObject.put("inputs", inputs.save());

		try{
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(saveObject);
			out.close();
			fileOut.close();
			println("Serialized data is saved in "+ path);
		}catch(IOException i){
			i.printStackTrace();
		}
		
	}

	public void restore(){
		println("Requesting path to open from.");
		selectInput("Select file to open.", "restore");
	}

	public void restore(File file){
		if(file != null){
			restore(file.getAbsolutePath());
		}
	}

	public void restore(String path){
		lastBackupFile = "mashine_"+timestamp()+".backup.mashine";
		save(lastBackupFile);
		try
		{
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			HashMap<String,Object> restoredObject = (HashMap<String,Object>) in.readObject();
			in.close();
			fileIn.close();

			scene.restore(restoredObject.get("scene"));
			bank.restore(restoredObject.get("bank"));
			inputs.restore(restoredObject.get("inputs"));

			println("Restored from "+ path);
			ui.status.set("file", path.split("/")[path.split("/").length -1]);

		}catch(IOException i)
		{
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("Some class not found");
			c.printStackTrace();
			ui.status.set("file", "failed to load " + path.split("/")[path.split("/").length -1]);
			return;
		}
	}

	private String timestamp(){
		return year()+"-"+month()+"-"+day()+"_"+hour()+"-"+minute()+"-"+second();
	}
}
