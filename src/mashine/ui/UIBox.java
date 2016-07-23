/**
 *  Mother class movable subwindows
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import java.util.LinkedList;

import processing.core.PConstants;

import mashine.MaShine;
import mashine.ui.elements.CloseButton;
import mashine.ui.elements.Grabber;

import mashine.utils.*;

public class UIBox extends Drawable{

	protected String title;
	private int scroll;
	private int vheight;

	protected LinkedList<Element> elements;
	private Element grabber;
	private Element closeButton;

	public UIBox(String t, int x, int y, int w, int h){
		this(t, x, y, w, h, h);
	}
	public UIBox(String t, int x, int y, int w, int h, int vh){
		super(x, y, w, h);
		title = t;
		this.x = Math.min(x, MaShine.m.width - width);
		this.y = Math.min(y, MaShine.m.height - height - 23);
		width = Math.min(w, MaShine.m.width);
		height = Math.min(h, MaShine.m.height);
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

	protected void drawFrame(){
		canvas.noStroke();
		FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(0, 0, width+1, 22);
		
		FlatColor.fill(canvas,Colors.WHITE);
		canvas.textAlign(PConstants.CENTER, PConstants.TOP);
		canvas.text(title,width / 2, 7);
	}

	protected void drawUI() {}
	protected void drawFixedUI() {}

	private void scroll(){
		if(mouseIn( )&& hasFocus()){
			if(MaShine.inputs.getState("mouse.wheel.encoder.on")){
				scroll += 25;
			}else if(MaShine.inputs.getState("mouse.wheel.encoder.off")){
				scroll -= 25;
			}
			scroll = Math.max(0, Math.min(vheight-height, scroll));
		}
	}

	public void setVirtualHeight(int vh){
		vheight = Math.max(height, vh);
		scroll = Math.max(0, Math.min(vheight-height, scroll));
	}

	public void moveBox(int x, int y) {
		this.x = Math.max(0, Math.min(x, MaShine.m.width - width));
		this.y = Math.max(0, Math.min(y, MaShine.m.height - height - 23));
	}

	protected int getScroll(){
		return scroll;
	}

}