package mashine.ui;

import mashine.*;
import processing.core.PGraphics;

public abstract class Drawable extends Focusable{

	public PGraphics canvas;
	private PGraphics parent;
	protected int x, y; 
	public int width, height;
	private boolean init = false;

	public Drawable(int x, int y, int width, int height){
		this(MaShine.m.g, x, y, width, height);
	}

	public Drawable(PGraphics parent, int x, int y, int width, int height){
		super();
		canvas = MaShine.m.createGraphics(width + 1, height + 1, MaShine.P2D);

		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(){

		//parent.endDraw();
		canvas.beginDraw();
		//canvas.clip(x, scroll, width+1, height+1);

		if(!init){
			canvas.textFont(MaShine.ui.TEXTFONT);
			canvas.textSize(MaShine.ui.TEXTSIZE);
			init = true;
		}


		drawContent();

		canvas.endDraw();
		//parent.beginDraw();

		parent.image(canvas, x, y);
		
	}

	protected void drawContent(){}

	public void tick(){}


	public boolean mouseIn(){
		return  MaShine.m.mouseX >= x &&
				MaShine.m.mouseX < x + width &&
				MaShine.m.mouseY >= y &&
				MaShine.m.mouseY < y + height;
	}

	public int mouseX() {
		return MaShine.m.mouseX - x;
	}
	public int mouseY() {
		return MaShine.m.mouseY - y;
	}

}