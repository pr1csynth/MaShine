/**
 *  Interface to select and apply filters;
 *
 *	@author procsynth - Antoine Pintout
 *	@since  18-03-2016`
 */

package mashine.ui.boxes;

import java.util.Collections;
import java.util.Map;
import java.util.List;

import mashine.Do;
import mashine.MaShine;
import mashine.engine.Track;
import mashine.engine.Filter;
import mashine.ui.Colors;
import mashine.ui.FlatColor;
import mashine.ui.UIBox;
import mashine.ui.elements.TextButton;
import processing.core.PConstants;

public class FilterSelector extends UIBox{

	private String selectedModel = "";

	public FilterSelector () {
		super("FILTERS", 400, 600, 300, 230);

		MaShine.inputs.registerAction("ui.filt_select.next", new Do(){public void x(){}});
		MaShine.inputs.registerAction("ui.filt_select.prev", new Do(){public void x(){}});

		elements.add(new TextButton(this, "add >", 90, height-15, 55, 
			new Do(){public void x(){}}
			));

		elements.add(new TextButton(this, "delete", width - 54, height-15, 55, 
			new Do(){public void x(){}}
			));
		elements.add(new TextButton(this, "up", 155, height-15, 30, 
			new Do(){public void x(){}}
			));
		elements.add(new TextButton(this, "down", 187, height-15, 40, 
			new Do(){public void x(){}}
			));
	}

	public void tick(){
	}

	public void drawFixedUI(){
		canvas.noStroke();
		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(1, height - 16, width -1, 16);
		FlatColor.stroke(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.line(149, 20, 149, height-1);
		canvas.noStroke();
	}

	public void drawUI(){
		canvas.noStroke();
		int offset = 30;
		int index = 0;
		canvas.textAlign(PConstants.LEFT, PConstants.TOP);

		Map<String, Filter> filterModels = MaShine.bank.getFilters();

		for(String m : filterModels.keySet()){
			if(m == selectedModel){
				FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
			}else{
				if(index % 2 == 0){
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._700);
				}else{
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._600);
				}
			}
			canvas.rect(1, offset - 3, 148, 14);

			if(isClicked(offset)){
				setSelectedModel(m);
			}

			FlatColor.fill(canvas,Colors.WHITE);
			canvas.text(m, 5, offset);
			offset += 14;
			index ++;
		}
	}

	public void setSelectedModel(String model){
		selectedModel = model;
	}

	private boolean isClicked(int offset){
		return mouseY() > 27 &&
			hasFocus() &&
			offset - 3 - getScroll() < mouseY() &&
			mouseY() < offset + 11 - getScroll() &&
			MaShine.inputs.getState("mouse.left.press");
	}
}