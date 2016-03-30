/**
 *  Mother class movable subwindows
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import mashine.*;
import mashine.ui.elements.*;
import processing.core.*;
import java.util.LinkedList; 

public class UIBox extends Drawable{

	protected String title;
	private int scroll;
	private int vheight;

	protected LinkedList<Element> elements;

	public UIBox(MaShine m, String t, int x, int y, int w, int h){
		this(m, t, x, y, w, h, h);
	}
	public UIBox(MaShine m, String t, int x, int y, int w, int h, int vh){
		super(m, x, y, w, h);
		title = t;
		this.x = M.min(x, M.width - width);
		this.y = M.min(y, M.height - height);
		width = M.min(w, M.width);
		height = M.min(h, M.height);
		vheight = vh;
		scroll = 0;

		elements = new LinkedList<Element>();
		elements.add(new Grabber(this));
		elements.add(new CloseButton(this));
	}

	protected void drawContent(){

		drawFrame();

		elements.sort(new UI.SortByFocus());

		canvas.pushMatrix();
		canvas.translate(0, -scroll);

		for(Element el : elements){
			el.draw();
			if(el == elements.getLast() && el.mouseIn())
				el.focus();
			else
				if(el.hasFocus())
					el.defocus();
		}

		// if(elements.getLast().mouseIn())
		// 	elements.getLast().focus();

		drawUI();
		canvas.popMatrix();

		scroll +=5;
		if(scroll > vheight-height)
			scroll = 0;
	}

	private void drawFrame(){
		FlatColor.stroke(canvas,Colors.MATERIAL.BLUE_GREY._800);
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._600);
		canvas.rect(0, 0, width, height);

		canvas.noStroke();
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(0, 0, width+1, 22);
		
		FlatColor.fill(canvas,Colors.WHITE);
		canvas.textAlign(M.CENTER, M.TOP);
		canvas.text(title,width / 2, 7);
	}

	protected void drawUI() {}

	public void moveBox(int x, int y) {
		this.x = M.max(0, M.min(x, M.width - width));
		this.y = M.max(0, M.min(y, M.height - height));
	}

}