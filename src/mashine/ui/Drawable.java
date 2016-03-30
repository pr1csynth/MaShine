package mashine.ui;

import mashine.*;
import processing.core.PGraphics;

public abstract class Drawable extends Focusable{

	public PGraphics canvas;
	private PGraphics parent;
	protected int x, y; 
	public int width, height;
	private boolean init = false;

	public Drawable(MaShine m, int x, int y, int width, int height){
		this(m, m.g, x, y, width, height);
	}

	public Drawable(MaShine m, PGraphics parent, int x, int y, int width, int height){
		super(m);
		canvas = M.createGraphics(width + 1, height + 1, M.P2D);

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
			canvas.textFont(M.ui.TEXTFONT);
			canvas.textSize(M.ui.TEXTSIZE);
			init = true;
		}


		drawContent();

		canvas.endDraw();
		//parent.beginDraw();

		parent.image(canvas, x, y);
		
	}

	protected void drawContent(){
		M.println("empty drawContent called");
	}

	public void tick(){}


	public boolean mouseIn(){
		return  M.mouseX >= x &&
				M.mouseX < x + width &&
				M.mouseY >= y &&
				M.mouseY < y + height;
	}

	public int mouseX() {
		return M.mouseX - x;
	}
	public int mouseY() {
		return M.mouseY - y;
	}

}