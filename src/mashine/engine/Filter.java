/**
 *  Mother class of tools generating/modifiying  frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import mashine.MaShine;
import mashine.scene.Frame;
import mashine.scene.DeviceGroup;

import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;

public class Filter implements Serializable{

	private static final long serialVersionUID = 0xF1750001L;

	public static final short RANGE = 0;
	public static final short STATE = 1;
	public static final short GROUP = 2;
	public static final short FRAME = 3;
	public static final short LONG  = 4;

	private HashMap<String, Short> parameters;
	private HashMap<String, Object>  values;

	private Robot robot;

	private String name;
	private Boolean enabled;

	public static interface Robot{
		public void  setup(Filter filter);
		public Frame f(Filter filter, Frame frame);
	}

	public Filter(String name, Robot robot){
		this.name = name;
		this.robot = robot;
	}

	public Filter(String parent, Filter f){
		this.name = parent + ".filter." + f.getName();
		this.robot = f.getRobot();
		parameters = new HashMap<String, Short>();
		values = new HashMap<String, Object>();
		robot.setup(this);
	}

	public Frame filter(Frame f){
		return robot.f(this, f);
	}
	public void declare(String param, short type){
		if(type == RANGE){
			MaShine.inputs.registerRange(this.name+"."+param);
		}else if(type == STATE){
			values.put(param, "");
		}else{
			values.put(param, null);
		}
		parameters.put(param, type);
	}

	public void set(String param, Object value){
		values.put(param, value);
	}

	public double getRange(String param){
		if(parameters.containsKey(param) && parameters.get(param) == RANGE){
			return MaShine.inputs.getRange(this.name + "." + param);
		}
		return 0.0;
	}

	public boolean getState(String param){
		if(parameters.containsKey(param) && parameters.get(param) == STATE && null != values.get(param)){
			return MaShine.inputs.getState((String) values.get(param));
		}
		return false;	
	}

	public long getLong(String param){
		if(parameters.containsKey(param) && parameters.get(param) == LONG && null != values.get(param)){
			return (Long) values.get(param);
		}
		return 0L;	
	}

	public Frame getFrame(String param){
		if(parameters.containsKey(param) && parameters.get(param) == FRAME && null != values.get(param)){
			return (Frame) values.get(param);
		}
		return new Frame();	
	}

	public DeviceGroup getGroup(String param){
		if(parameters.containsKey(param) && parameters.get(param) == GROUP && null != values.get(param)){
			return (DeviceGroup) values.get(param);
		}
		return new DeviceGroup("empty");	
	}

	public Object get(String param){
		return values.get(param);
	}

	public String getName(){return name;}
	public Robot getRobot(){return robot;}
	public HashMap<String, Short> getParameters(){return parameters;}
	public HashMap<String, Object> getValues(){return values;}
	public boolean isEnabled(){return enabled;}
	public void disable(){enabled = false;}
	public void enable(){
		enabled = true;
		robot.setup(this);
	}


}