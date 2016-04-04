/**
 *  Mother class of tools generating/modifiying  frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import mashine.MaShine;
import mashine.Do;
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
	private HashMap<String, DeviceGroup> groups;
	private HashMap<String, Frame> frames;
	private HashMap<String, Long>  longs;

	private Robot robot;

	private String name;
	private String type;
	private Boolean enabled;

	public static abstract class Robot implements Serializable{
		public void  setup(Filter filter){};
		public abstract Frame f(Filter filter, Frame frame);
	}

	public Filter(String name, Robot robot){
		this.name = this.type = name;
		this.robot = robot;
		this.enabled = false;
	}

	public Filter(String parent, Filter f){
		this.type = f.getType();
		this.name = parent + "." + f.getName();
		this.robot = f.getRobot();
		parameters = new HashMap<String, Short>();
		groups = new HashMap<String, DeviceGroup>();
		frames = new HashMap<String, Frame>();
		longs = new HashMap<String, Long>();
		robot.setup(this);
		this.enabled = false;

		MaShine.inputs.registerState(this.name+".enabled");
		MaShine.inputs.registerAction(this.name+".toggle", new Do(){public void x(){toggle();}});
		MaShine.inputs.registerAction(this.name+".enable", new Do(){public void x(){enable();}});
		MaShine.inputs.registerAction(this.name+".disable", new Do(){public void x(){disable();}});
	}

	public Frame filter(Frame f){
		return robot.f(this, f);
	}
	public void declare(String param, short type){
		if(type == RANGE){
			MaShine.inputs.registerRange(this.name+"."+param);
		}else if(type == STATE){
			MaShine.inputs.registerState(this.name+"."+param);
		}else if(type == FRAME){
			frames.put(param, new Frame());
		}else if(type == GROUP){
			groups.put(param, new DeviceGroup(""));
		}else if(type == LONG){
			longs.put(param, 0L);
		}
		parameters.put(param, type);
	}

	public double getRange(String param){
		if(parameters.containsKey(param) && parameters.get(param) == RANGE){
			return MaShine.inputs.getRange(this.name + "." + param);
		}
		return 0.0;
	}

	public boolean getState(String param){
		if(parameters.containsKey(param) && parameters.get(param) == STATE){
			return MaShine.inputs.getState(this.name + "." + param);
		}
		return false;	
	}

	public long getLong(String param){
		if(parameters.containsKey(param) && parameters.get(param) == LONG && null != longs.get(param)){
			return longs.get(param);
		}
		return 0L;	
	}

	public Frame getFrame(String param){
		if(parameters.containsKey(param) && parameters.get(param) == FRAME && null != frames.get(param)){
			return frames.get(param);
		}
		return new Frame();	
	}

	public DeviceGroup getGroup(String param){
		if(parameters.containsKey(param) && parameters.get(param) == GROUP && null != groups.get(param)){
			return groups.get(param);
		}
		return new DeviceGroup("empty");	
	}

	public String getName(){return name;}
	public String getType(){return type;}
	public Robot getRobot(){return robot;}
	public HashMap<String, Short> getParameters(){return parameters;}
	public boolean isEnabled(){return enabled || MaShine.inputs.getState(this.name+".enabled");}
	public void disable(){enabled = false;}
	public void enable(){enabled = true;}
	public void toggle(){enabled = !enabled;}


}