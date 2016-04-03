/**
 *  Managing instance for inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

 package mashine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import mashine.inputs.InputSource;
import mashine.inputs.KeyboardInputs;
import mashine.inputs.Learnable;
import mashine.inputs.MidiInputs;
import mashine.inputs.MinimAnalysis;
import mashine.inputs.MouseInputs;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

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
	private ArrayList<String>       states;

	private HashMap<String,String>  actionLinks;
	private HashMap<String,String>  rangeLinks;
	private HashMap<String,String>  stateLinks;

	public Inputs(){

		rangeInputs = new HashMap<String,Double>();
		stateInputs = new HashMap<String,Boolean>();

		actions     = new HashMap<String,Do>();
		ranges      = new ArrayList<String> ();
		states      = new ArrayList<String> ();

		actionLinks = new HashMap<String,String>();
		rangeLinks  = new HashMap<String,String>();
		stateLinks  = new HashMap<String,String>();

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

	public void passKeyEvent(KeyEvent e){keyboard.keyEvent(e);}
	public void passMouseEvent(MouseEvent e){mouse.mouseEvent(e);}

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

	public Set<String> getStateInputSet(){return stateInputs.keySet();}
	public Set<String> getRangeInputSet(){return rangeInputs.keySet();}

	public boolean hasRange(String inputName){	return rangeInputs.containsKey(inputName);}
	public boolean hasState(String inputName){	return stateInputs.containsKey(inputName);}
	
	public void setRange(String inputName, double range){	rangeInputs.put(inputName, range);}
	public void setState(String inputName, Boolean state){	stateInputs.put(inputName, state);}

	public boolean getState(String inputName){
		if(stateInputs.containsKey(inputName)){
			return stateInputs.get(inputName);
		}else if(stateLinks.containsKey(inputName)){
			return getState(stateLinks.get(inputName));
		}else{
			return false;		
		}
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





	public void registerAction(String destination, Do action){	actions.put(destination, action);}
	public void registerState(String destination){  states.add(destination);}
	public void registerRange(String destination){	ranges.add(destination);}

	public void link(String what, String with){actionLinks.put(what, with);}
	public void range(String what, String with){rangeLinks.put(what, with);}
	public void state(String what, String with){stateLinks.put(what, with);}

	public void unlink(String what){	actionLinks.remove(what);}
	public void unrange(String what){	 rangeLinks.remove(what);}
	public void unstate(String what){	 stateLinks.remove(what);}
	
	public HashMap<String,String> getActionLinks(){	return actionLinks;}
	public HashMap<String,String> getRangeLinks(){	return rangeLinks;}
	public HashMap<String,String> getStateLinks(){	return rangeLinks;}

	public Set<String> 		 getActionSet(){ return actions.keySet();}
	public ArrayList<String> getRangeSet(){	 return ranges;}
	public ArrayList<String> getStateSet(){	 return states;}
	
	public Do getAction(String actionName){return actions.get(actionName);}

	public String getLastKey(){		return lastTyped;}
	public String getLastState(){	return lastState;}
	public String getLastRange(){	return lastRange;}

	public Object save(){
		HashMap<String,Object> saveObject = new HashMap<String,Object>();
		saveObject.put("actions", actionLinks);
		saveObject.put("states", stateLinks);
		saveObject.put("ranges", rangeLinks);
		return saveObject;
	}

	public void restore(Object restoredObject){
		HashMap<String, Object> r = (HashMap<String, Object>) restoredObject;
		actionLinks = (HashMap<String, String>) r.get("actions");
		stateLinks = (HashMap<String, String>) r.get("states");
		rangeLinks = (HashMap<String, String>) r.get("ranges");
	}

}

