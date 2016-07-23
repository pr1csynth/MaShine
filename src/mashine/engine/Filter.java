/**
 *  Mother class of tools generating/modifiying  frames
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import mashine.MaShine;
import mashine.utils.Do;
import mashine.scene.Frame;
import mashine.scene.DeviceGroup;
import mashine.scene.Device;
import mashine.scene.features.*;

import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filter implements Serializable{

	private static final long serialVersionUID = 0xF1750001L;

	private HashMap<String, String> parameters;
	private DeviceGroup group;
	private HashMap<String, Frame> frames;
	private HashMap<String, Long>  longs;

	private Script script;

	private String name;
	private String type;
	private Boolean enabled;

	private Boolean hasForEachFeature, hasForEachDevice, hasForEachFrame = false;

	public Filter(String parent, String scriptName){
		this.type = scriptName;
		this.name = parent + "." + scriptName;
		parameters = new HashMap<String, String>();
		frames = new HashMap<String, Frame>();
		longs = new HashMap<String, Long>();
		this.enabled = false;

		script = MaShine.bank.filters.getNewScript(scriptName, this);

		try{
			HashMap<String,String> inNodes = (HashMap<String,String>) script.invocable().invokeFunction("getInNodes");
			
			for(String in : inNodes.keySet()){
				declare(in, inNodes.get(in));
			}

			hasForEachFrame = (Boolean) script.invocable().invokeFunction("hasForEachFrame");
			hasForEachFeature = (Boolean) script.invocable().invokeFunction("hasForEachFeature");
			hasForEachDevice = (Boolean) script.invocable().invokeFunction("hasForEachDevice");
			
		}catch(Exception e){e.printStackTrace();}

		registerActions();
	}

	public Frame filter(Frame frame){
		try{
			if(hasForEachFrame){
			 	frame = (Frame)  script.invocable().invokeFunction("forEachFrame", frame);
			}

			if(group != null){	
				if(hasForEachDevice || hasForEachFeature){
					Map<Device, Integer> weights = group.getDevices();
					for(Device d : weights.keySet()){
						int w = weights.get(d);
						List<Feature> features = d.getFeatures();
						for(Feature f : features){
							if(hasForEachDevice){
								if(f instanceof EditableFeature){
									Feature newFeature = (Feature) script.invocable().invokeFunction("forEachFeatureInDevices", f, w);
									if(newFeature != null){
										frame.addFeature(d, newFeature);
									}
								}
							}else if (hasForEachFeature) {
								Feature frameFeature = frame.getFeature(d, f);
								if(frameFeature != null){
									frame.addFeature(d, (Feature) script.invocable().invokeFunction("forEachFeature", frameFeature, w));
								}
							}
						}
					}
				}
			}
		}catch(Exception e){e.printStackTrace();
			// TODO inform that script broke
		}
		return frame;
	}

	private void declare(String param, String type){
		if(type.equals("range")){
			MaShine.inputs.registerRange(this.name+"."+param);
		}else if(type.equals("state")){
			MaShine.inputs.registerState(this.name+"."+param);
		}else if(type.equals("frame")){
			frames.put(param, new Frame());
		}else if(type.equals("long")){
			longs.put(param, 0L);
		}
		parameters.put(param, type);
	}

	public void redeclare(){
		for(String param : parameters.keySet()){
			String type = parameters.get(param);
			if(type.equals("range")){
				MaShine.inputs.registerRange(this.name+"."+param);
			}else if(type.equals("state")){
				MaShine.inputs.registerState(this.name+"."+param);
			}
		}
	}

	public void registerActions(){
		MaShine.inputs.registerState(this.name+".enabled");
		MaShine.inputs.registerAction(this.name+".toggle", new Do(){public void x(){toggle();}});
		MaShine.inputs.registerAction(this.name+".enable", new Do(){public void x(){enable();}});
		MaShine.inputs.registerAction(this.name+".disable", new Do(){public void x(){disable();}});
	}

	public double getRange(String param){
		if(parameters.containsKey(param) && parameters.get(param).equals("range")){
			return MaShine.inputs.getRange(this.name + "." + param);
		}
		return 0.0;
	}

	public boolean getState(String param){
		if(parameters.containsKey(param) && parameters.get(param).equals("state")){
			return MaShine.inputs.getState(this.name + "." + param);
		}
		return false;	
	}

	public long getLong(String param){
		if(parameters.containsKey(param) && parameters.get(param).equals("long") && null != longs.get(param)){
			return longs.get(param);
		}
		return 0L;	
	}

	public Frame getFrame(String param){
		if(parameters.containsKey(param) && parameters.get(param).equals("frame") && null != frames.get(param)){
			return frames.get(param);
		}
		return new Frame();	
	}

	public DeviceGroup getGroup(){return (null != group ? group : new DeviceGroup(""));}
	public void setGroup(DeviceGroup g){group = g;}

	public String getName(){return name;}
	public String getType(){return type;}
	public HashMap<String, String> getParameters(){return parameters;}
	public boolean isEnabled(){return enabled || MaShine.inputs.getState(this.name+".enabled");}
	public void disable(){enabled = false;}
	public void enable(){enabled = true;}
	public void toggle(){enabled = !enabled;}

	public void setLong(String param, Long value){longs.put(param, value);}
	public void setFrame(String param, Frame value){frames.put(param, value);}


}