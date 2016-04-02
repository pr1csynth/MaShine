/**
 *  Interface to link actions to inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  30-03-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.elements.*;
import java.util.*;

public class Linker extends UIBox{

	private TextInput filterInput;
	private TextInput linkInput;
	private String learnedState = "";
	private String learnedRange = "";
	private String selectedState = "";
	private String selectedRange = "";
	private boolean learningAll = false;

	public Linker (MaShine m) {
		super(m, "LINKS", 50, 50, 300, 500, 1500);

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
		learnedState = selectedState;
		learnedRange = selectedRange;
		linkInput.setValue("LEARNING...");
		learningAll = false;
	}
	public void linkAll(){
		if(!filterInput.value().equals("")){
			learnedState = "";
			learnedRange = "";
			learningAll = true;
			linkInput.setValue("LEARNING ALL...");
		}
	}
	public void unlink(){
		M.inputs.unlink(selectedState);
		M.inputs.unrange(selectedRange);
	}

	public void commitLink(){
		if(learnedState == "" && learnedRange == ""){
			if(selectedState != ""){
				if(null != linkInput.value()){
					M.inputs.link(selectedState, linkInput.value());
				}else{
					M.inputs.unlink(selectedState);
				}
			}else if(selectedRange != ""){
				if(null != linkInput.value()){
					M.inputs.range(selectedState, linkInput.value());
				}else{
					M.inputs.unrange(selectedRange);
				}
			}
			
		}

		M.println("'"+linkInput.value()+"'");
	}

	public void drawUI(){
		ArrayList<String> actionSet = new ArrayList<String>(M.inputs.getActionSet());
		ArrayList<String> rangeSet = new ArrayList<String>(M.inputs.getRangeSet());

		if(learnedState != ""){
			if(null != M.inputs.getLastState()){
				M.inputs.link(learnedState, M.inputs.getLastState());
				learnedState = "";
				linkInput.setValue(M.inputs.getLastState());
			}
		}else if(learnedRange != ""){
			if(null != M.inputs.getLastRange()){
				M.inputs.range(learnedRange, M.inputs.getLastRange());
				learnedRange = "";
				linkInput.setValue(M.inputs.getLastRange());
			}
		}else if(learningAll){
			if(null != M.inputs.getLastState()){
				for(String a : actionSet){
					if(a.contains(filterInput.value())){
						M.inputs.link(a, M.inputs.getLastState());
					}
				}
				linkInput.setValue(M.inputs.getLastState());
				learningAll = false;
			}else if(null != M.inputs.getLastRange()){
				for(String a : rangeSet){
					if(a.contains(filterInput.value())){
						M.inputs.range(a, M.inputs.getLastState());
					}
				}
				linkInput.setValue(M.inputs.getLastRange());
				learningAll = false;
			}
		}

		canvas.noStroke();
		canvas.textAlign(canvas.LEFT, canvas.TOP);
		HashMap<String,String> actionLinks = M.inputs.getActionLinks();
		HashMap<String,String> rangeLinks = M.inputs.getRangeLinks();

		Collections.sort(actionSet);
		Collections.sort(rangeSet);

		int offset = 75;
		int index = 0;
		for(String a : actionSet){
			if(a.contains(filterInput.value())){
				if(a.equals(selectedState)){
					FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
					canvas.rect(1, offset - 3, width - 1, 14);
				}else if(actionLinks.containsKey(a)){
					if(index % 2 == 0){
						FlatColor.fill(canvas,Colors.MATERIAL.TEAL._700);
					}else{
						FlatColor.fill(canvas,Colors.MATERIAL.TEAL._600);
					}
					canvas.rect(1, offset - 3, width - 1, 14);
				}else{
					if(index % 2 == 0){
						FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._700);
						canvas.rect(1, offset - 3, width - 1, 14);
					}
				}

				FlatColor.fill(canvas,Colors.WHITE);
				canvas.text(a, 5, offset);

				if(mouseY() > 49 &&hasFocus() && offset - 3 - getScroll() < mouseY() && mouseY() < offset + 11 - getScroll() && M.inputs.getState("mouse.left.press")){
					if(actionLinks.containsKey(a)){
						linkInput.setValue(actionLinks.get(a));
					}else{
						linkInput.setValue("");
					}
					selectedState = a;
					selectedRange = "";
					learnedRange = "";
					learnedState = "";
				}

				offset += 14;
				index ++;


			}	
		}

		// offset += 10;

		// for(String a : rangeSet){
		// 	if(a.contains(filterInput.value())){
		// 		if(index % 2 == 0){
		// 			FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._700);
		// 			canvas.rect(1, offset - 3, width - 1, 14);
		// 		}

		// 		FlatColor.fill(canvas,Colors.WHITE);
		// 		canvas.text(a, 5, offset);
		// 		offset += 14;
		// 		index ++;
		// 	}
		// }

		setVirtualHeight(offset);
	}

}