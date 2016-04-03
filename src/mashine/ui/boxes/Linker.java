/**
 *  Interface to link actions to inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  30-03-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import processing.core.PConstants;

import mashine.Do;
import mashine.MaShine;
import mashine.ui.Colors;
import mashine.ui.FlatColor;
import mashine.ui.UIBox;
import mashine.ui.elements.TextButton;
import mashine.ui.elements.TextInput;

public class Linker extends UIBox{

	private TextInput filterInput;
	private TextInput linkInput;
	private String learnedAction = "";
	private String learnedState = "";
	private String learnedRange = "";
	private String selectedAction = "";
	private String selectedState = "";
	private String selectedRange = "";
	private boolean learningAll = false;

	public Linker () {
		super("LINKS", 50, 50, 300, 500, 1500);

		filterInput = new TextInput(this, "", 0, 55, 100, "\\.");
		linkInput = new TextInput(this, "", 0, 28, 189, "\\.");
		elements.add(filterInput);
		elements.add(linkInput);
		elements.add(new TextButton(this, "learn", width-54, 45, 55, 
			new Do(){public void x(){link();}}
			));
		elements.add(new TextButton(this, "learn all", width-141, 45, 85, 
			new Do(){public void x(){linkAll();}}
			));
		elements.add(new TextButton(this, "unlink", width-54, 28, 55, 
			new Do(){public void x(){unlink();}}
			));
		elements.add(new TextButton(this, "link", width-111, 28, 55, 
			new Do(){public void x(){commitLink();}}
			));
	}

	public void drawFixedUI(){
		FlatColor.fill(canvas, Colors.MATERIAL.GREY._800);
		canvas.rect(1, 22, width-1, 45);
	}

	public void link(){
		learnedAction = selectedAction;
		learnedState = selectedState;
		learnedRange = selectedRange;
		linkInput.setValue("LEARNING...");
		learningAll = false;
	}
	public void linkAll(){
		if(!filterInput.value().equals("")){
			learnedAction = "";
			learnedState = "";
			learnedRange = "";
			learningAll = true;
			linkInput.setValue("LEARNING ALL...");
		}
	}
	public void unlink(){
		MaShine.inputs.unlink(selectedState);
		MaShine.inputs.unrange(selectedRange);
		MaShine.inputs.unstate(selectedState);
	}

	public void commitLink(){
		if(!isLearning()){
			if(selectedAction != ""){
				if(null != linkInput.value()){
					MaShine.inputs.link(selectedState, linkInput.value());
				}else{
					MaShine.inputs.unlink(selectedState);
				}
			}else if(selectedRange != ""){
				if(null != linkInput.value()){
					MaShine.inputs.range(selectedRange, linkInput.value());
				}else{
					MaShine.inputs.unrange(selectedRange);
				}
			}else if(selectedState != ""){
				if(null != linkInput.value()){
					MaShine.inputs.state(selectedState, linkInput.value());
				}else{
					MaShine.inputs.unstate(selectedState);
				}
			}
			
		}
	}

	public void drawUI(){
		ArrayList<String> actionSet = new ArrayList<String>(MaShine.inputs.getActionSet());
		ArrayList<String> stateSet = new ArrayList<String>(MaShine.inputs.getStateSet());
		ArrayList<String> rangeSet = new ArrayList<String>(MaShine.inputs.getRangeSet());

		if(learnedAction != ""){
			if(null != MaShine.inputs.getLastState()){
				MaShine.inputs.link(learnedAction, MaShine.inputs.getLastState());
				learnedAction = "";
				linkInput.setValue(MaShine.inputs.getLastState());
			}
		}else if(learnedState != ""){
			if(null != MaShine.inputs.getLastState()){
				MaShine.inputs.state(learnedState, MaShine.inputs.getLastState());
				learnedState = "";
				linkInput.setValue(MaShine.inputs.getLastState());
			}
		}else if(learnedRange != ""){
			if(null != MaShine.inputs.getLastRange()){
				MaShine.inputs.range(learnedRange, MaShine.inputs.getLastRange());
				learnedRange = "";
				linkInput.setValue(MaShine.inputs.getLastRange());
			}
		}else if(learningAll){
			if(null != MaShine.inputs.getLastState()){
				for(String a : actionSet){
					if(a.contains(filterInput.value())){
						MaShine.inputs.link(a, MaShine.inputs.getLastState());
					}
				}
				for(String a : stateSet){
					if(a.contains(filterInput.value())){
						MaShine.inputs.state(a, MaShine.inputs.getLastState());
					}
				}
				linkInput.setValue(MaShine.inputs.getLastState());
				learningAll = false;
			}else if(null != MaShine.inputs.getLastRange()){
				for(String a : rangeSet){
					if(a.contains(filterInput.value())){
						MaShine.inputs.range(a, MaShine.inputs.getLastState());
					}
				}
				linkInput.setValue(MaShine.inputs.getLastRange());
				learningAll = false;
			}
		}

		canvas.noStroke();
		canvas.textAlign(PConstants.LEFT, PConstants.TOP);

		HashMap<String,String> actionLinks = MaShine.inputs.getActionLinks();
		HashMap<String,String> stateLinks = MaShine.inputs.getStateLinks();
		HashMap<String,String> rangeLinks = MaShine.inputs.getRangeLinks();

		Collections.sort(actionSet);
		Collections.sort(stateSet);
		Collections.sort(rangeSet);

		int offset = 75;
		int index = 0;

		for(String a : actionSet){
			if(a.contains(filterInput.value())){
				drawBackground(a, actionLinks, selectedAction, index, offset, 
					Colors.MATERIAL.CYAN._700,
					Colors.MATERIAL.CYAN._600,
					Colors.MATERIAL.BLUE_GREY._600,
					Colors.MATERIAL.BLUE_GREY._500
				);
				FlatColor.fill(canvas,Colors.WHITE);
				canvas.text(a, 5, offset);

				if(isClicked(offset)){
					if(actionLinks.containsKey(a)){
						linkInput.setValue(actionLinks.get(a));
					}else{
						linkInput.setValue("");
					}
					selectAction(a);
				}
				offset += 14;
				index ++;
			}	
		}

		for(String a : stateSet){
			if(a.contains(filterInput.value())){
				drawBackground(a, stateLinks, selectedState, index, offset, 
					Colors.MATERIAL.CYAN._700,
					Colors.MATERIAL.CYAN._600,
					Colors.MATERIAL.BLUE_GREY._600,
					Colors.MATERIAL.BLUE_GREY._500
				);
				FlatColor.fill(canvas,Colors.WHITE);
				canvas.text(a, 5, offset);

				if(isClicked(offset)){
					if(actionLinks.containsKey(a)){
						linkInput.setValue(stateLinks.get(a));
					}else{
						linkInput.setValue("");
					}
					selectState(a);
				}
				offset += 14;
				index ++;
			}	
		}

		for(String a : rangeSet){
			if(a.contains(filterInput.value())){	
				drawBackground(a, rangeLinks, selectedRange, index, offset, 
					Colors.MATERIAL.TEAL._600,
					Colors.MATERIAL.TEAL._400,
					Colors.MATERIAL.INDIGO._600,
					Colors.MATERIAL.INDIGO._500
				);
				FlatColor.fill(canvas,Colors.WHITE);
				canvas.text(a, 5, offset);

				if(isClicked(offset)){
					if(rangeLinks.containsKey(a)){
						linkInput.setValue(rangeLinks.get(a));
					}else{
						linkInput.setValue("");
					}
					selectRange(a);
				}
				offset += 14;
				index ++;
			}	
		}

		setVirtualHeight(offset);
	}

	private void drawBackground(String a, Map<String,String> links, String selected, int index, int offset, FlatColor c1,FlatColor c2,FlatColor c3,FlatColor c4){
		if(a.equals(selected)){
			FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
		}else if(links.containsKey(a)){
			if(index % 2 == 0){
				FlatColor.fill(canvas,c1);
			}else{
				FlatColor.fill(canvas,c2);
			}
		}else{
			if(index % 2 == 0){
				FlatColor.fill(canvas,c3);
			}else{
				FlatColor.fill(canvas,c4);
			}
		}

		canvas.rect(1, offset - 3, width - 1, 14);
	}

	private boolean isLearning(){
		return learningAll || !learnedAction.equals("") || !learnedState.equals("") || !learnedRange.equals("");
	}

	private void selectAction(String a){
		selectedAction = a;
		selectedRange = selectedState = learnedAction = learnedRange = learnedState = "";
	}
	private void selectRange(String a){
		selectedRange = a;
		selectedAction = selectedState = learnedAction = learnedRange = learnedState = "";
	}
	private void selectState(String a){
		selectedState = a;
		selectedAction = selectedRange = learnedAction = learnedRange = learnedState = "";
	}

	private boolean isClicked(int offset){
		return mouseY() > 49 &&
			hasFocus() &&
			offset - 3 - getScroll() < mouseY() &&
			mouseY() < offset + 11 - getScroll() &&
			MaShine.inputs.getState("mouse.left.press");
	}

}