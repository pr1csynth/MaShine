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

	protected LinkedList<Element> elements;

	public UIBox(MaShine m, String t, int x, int y, int w, int h){
		super(m, x, y, w, h);
		title = t;
		this.x = M.min(x, M.width - width);
		this.y = M.min(y, M.height - height);
		width = M.min(w, M.width);
		height = M.min(h, M.height);

		elements = new LinkedList<Element>();
		elements.add(new Grabber(this));
		elements.add(new CloseButton(this));
	}

	protected void drawContent(){

		drawFrame();

		elements.sort(new UI.SortByFocus());

		for(Element el : elements){
			el.draw();
			el.defocus();
		}

		if(elements.getLast().mouseIn())
			elements.getLast().focus();

		drawUI();
	}

	private void drawFrame(){
		FlatColor.stroke(canvas,Colors.MATERIAL.BLUE_GREY._800);
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._600);
		canvas.rect(0, 0, width, height);
		canvas.noStroke();
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(0, 0, width, 22);

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