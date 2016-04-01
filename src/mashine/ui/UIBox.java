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
	private Element grabber;
	private Element closeButton;

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
		grabber = new Grabber(this);
		closeButton = new CloseButton(this);
		elements.add(grabber);
		elements.add(closeButton);
	}

	protected void drawContent(){

		FlatColor.stroke(canvas,Colors.MATERIAL.BLUE_GREY._800);
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._600);
		canvas.rect(0, 0, width, height);

		scroll();

		canvas.pushMatrix();
		canvas.translate(0, -scroll);
		drawUI();
		canvas.popMatrix();
		drawFixedUI();
		
		elements.sort(new UI.SortByFocus());


		for(Element el : elements){
			if(el != closeButton && el != grabber)
				el.draw();
			if(el == elements.getLast() && el.mouseIn())
				el.focus();
			else
				if(el.hasFocus())
					el.defocus();
		}

		drawFrame();
		closeButton.draw();
		grabber.draw();

	}

	private void drawFrame(){
		canvas.noStroke();
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(0, 0, width+1, 22);
		
		FlatColor.fill(canvas,Colors.WHITE);
		canvas.textAlign(M.CENTER, M.TOP);
		canvas.text(title,width / 2, 7);
	}

	protected void drawUI() {}
	protected void drawFixedUI() {}

	private void scroll(){
		if(mouseIn( )&& hasFocus()){
			if(M.inputs.getState("mouse.wheel.encoder.on")){
				scroll += 25;
			}else if(M.inputs.getState("mouse.wheel.encoder.off")){
				scroll -= 25;
			}
			scroll = M.max(0, M.min(vheight-height, scroll));
		}
	}

	public void setVirtualHeight(int vh){
		vheight = M.max(height, vh);
		scroll = M.max(0, M.min(vheight-height, scroll));
	}

	public void moveBox(int x, int y) {
		this.x = M.max(0, M.min(x, M.width - width));
		this.y = M.max(0, M.min(y, M.height - height));
	}

	protected int getScroll(){
		return scroll;
	}

}