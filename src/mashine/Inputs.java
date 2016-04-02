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
import java.util.ArrayList;
import processing.event.*;

public class Inputs{

	private HashMap<String,InputSource> inputs;
	private ArrayList<Learnable> learnable;

	private KeyboardInputs keyboard;
	private MouseInputs mouse;
	private String lastTyped = "";
	private String lastState;
	private String lastRange;

	private HashMap<String,Boolean> stateInputs;
	private HashMap<String,Double>  rangeInputs;

	private HashMap<String,Do>      actions;
	private ArrayList<String>       ranges;
	private HashMap<String,String>  actionLinks;
	private HashMap<String,String>  rangeLinks;

	public Inputs(){

		stateInputs = new HashMap<String,Boolean>();
		rangeInputs = new HashMap<String,Double>();

		actions     = new HashMap<String,Do>();
		ranges      = new ArrayList<String> ();
		rangeLinks  = new HashMap<String,String>();
		actionLinks = new HashMap<String,String>();

		keyboard = new KeyboardInputs();
		mouse = new MouseInputs();

		inputs = new HashMap<String,InputSource>(){{
			put("keyboard", keyboard);
			put("mouse", mouse);
			put("minim", new MinimAnalysis());
			put("midi", new MidiInputs());
		}};

		learnable = new ArrayList<Learnable>();
		learnable.add(keyboard);
		learnable.add((Learnable)inputs.get("midi"));

	}

	public void poll(){

		lastRange = null;
		lastState = null;
		lastTyped = keyboard.lastTyped;

		for(Learnable l : learnable){
			if(null != l.getLastState()){
				lastState = l.getLastState();
			}
			if(null != l.getLastRange()){
				lastRange = l.getLastRange();
			}
		}

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

	public double getRange(String inputName){
		if(rangeInputs.containsKey(inputName)){
			return rangeInputs.get(inputName);
		}else if(rangeLinks.containsKey(inputName)){
			return getRange(rangeLinks.get(inputName));
		}else{
			return 0.0;		
		}
	}

	public boolean hasRange(String inputName){
		return rangeInputs.containsKey(inputName);
	}

	public void setRange(String inputName, double range){
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

	public void range(String what, String with){
		rangeLinks.put(what, with);
	}

	public void unrange(String what){
		rangeLinks.remove(what);
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

	public Set<String> getRangeInputSet(){
		return rangeInputs.keySet();
	}
	public HashMap<String,String> getActionLinks(){
		return actionLinks;
	}
	public HashMap<String,String> getRangeLinks(){
		return rangeLinks;
	}
	public ArrayList<String> getRangeSet(){
		return ranges;
	}

	public String getLastKey(){
		return lastTyped;
	}
	public String getLastState(){
		return lastState;
	}
	public String getLastRange(){
		return lastRange;
	}

	public Object save(){
		HashMap<String,Object> saveObject = new HashMap<String,Object>();
		saveObject.put("actions", actionLinks);
		saveObject.put("ranges", rangeLinks);
		return saveObject;
	}
	

	public void restore(Object restoredObject){
		HashMap<String, Object> r = (HashMap<String, Object>) restoredObject;
		actionLinks = (HashMap<String, String>) r.get("actions");
		rangeLinks = (HashMap<String, String>) r.get("ranges");
	}

}

