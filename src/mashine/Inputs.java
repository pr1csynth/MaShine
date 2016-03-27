/**
 *  Managing instance for inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

 package mashine;

import mashine.*;
import mashine.inputs.*;
import java.util.HashMap; 
import java.util.Set;
import processing.event.*;

public class Inputs{

	private HashMap<String,InputSource> inputs;
	private MaShine M;

	private KeyboardInputs keyboard;
	private MouseInputs mouse;
	private String lastTyped = "";

	private HashMap<String,Boolean> stateInputs;
	private HashMap<String,Float>   rangeInputs;

	private HashMap<String,Do>      actions;
	private HashMap<String,String>  actionLinks;

	public Inputs(MaShine m){
		M = m;

		stateInputs = new HashMap<String,Boolean>();
		rangeInputs = new HashMap<String,Float>();

		actions     = new HashMap<String,Do>();
		actionLinks = new HashMap<String,String>();

		keyboard = new KeyboardInputs(M);
		mouse = new MouseInputs(M);

		inputs = new HashMap<String,InputSource>(){{
			put("keyboard", keyboard);
			put("mouse", mouse);
			put("minim", new MinimAnalysis(M));
			put("midi", new MidiInputs(M));
		}};

	}

	public void poll(){

		lastTyped = keyboard.lastTyped;

		for(String key : inputs.keySet()){
			inputs.get(key).tick();
			stateInputs.putAll(inputs.get(key).pollStates());
			rangeInputs.putAll(inputs.get(key).pollRanges());
			inputs.get(key).clear();
		}

		for(String key : actionLinks.keySet()){
			String s = actionLinks.get(key);
			if(stateInputs.containsKey(s)){
				if(stateInputs.get(s)){
					actions.get(key).x();
				}
			}
		}
	}

	public boolean getState(String inputName){
		if(stateInputs.containsKey(inputName)){
			return stateInputs.get(inputName);
		}else{
			return false;		
		}
	}

	public boolean hasState(String inputName){
		return stateInputs.containsKey(inputName);
	}

	public void setState(String inputName, Boolean state){
		stateInputs.put(inputName, state);
	}

	public float getRange(String inputName){
		if(rangeInputs.containsKey(inputName)){
			return rangeInputs.get(inputName);
		}else{
			return (float)0.0;		
		}
	}

	public boolean hasRange(String inputName){
		return rangeInputs.containsKey(inputName);
	}

	public void setRange(String inputName, float range){
		rangeInputs.put(inputName, range);
	}

	public void passKeyEvent(KeyEvent e){
		keyboard.keyEvent(e);
	}

	public void passMouseEvent(MouseEvent e){
		mouse.mouseEvent(e);
	}

	public void register(String actionName, Do action){
		actions.put(actionName, action);
	}

	public void link(String actionName, String stateInputName){
		actionLinks.put(actionName, stateInputName);
	}

	public void unlink(String actionName){
		actionLinks.remove(actionName);
	}

	public Set<String> getActionSet(){
		return actions.keySet();
	}
	public Do getAction(String actionName){
		return actions.get(actionName);
	}

	public Set<String> getStateSet(){
		return stateInputs.keySet();
	}
	public Set<String> getRangeSet(){
		return rangeInputs.keySet();
	}

	public String getLastKey(){
		return lastTyped;
	}

}

