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

public class Filter extends Block{

	private Script script;

	private Boolean hasForEachFeature, hasForEachDevice, hasForEachFrame = false;

	public Filter(String scriptName){

		script = MaShine.bank.filters.getNewScript(scriptName, this);

		try{
			HashMap<String,String> inNodes = (HashMap<String,String>) script.invocable().invokeFunction("getInNodes");
			
			for(String in : inNodes.keySet()){
				declareNodeIn(in, inNodes.get(in));
			}

			// TODO : controlout nodes 

			hasForEachFrame = (Boolean) script.invocable().invokeFunction("hasForEachFrame");
			hasForEachFeature = (Boolean) script.invocable().invokeFunction("hasForEachFeature");
			hasForEachDevice = (Boolean) script.invocable().invokeFunction("hasForEachDevice");
			
		}catch(Exception e){e.printStackTrace();}
	}



	private void declareNodeIn(String name, String type){
		if(type.equals("range")){
			controlIn.put(name, new InNode(0));
		}else if(type.equals("state")){
			controlIn.put(name, new InNode(false));
		}else if(type.equals("frame")){
			controlIn.put(name, new InNode(new Frame()));
		}else if(type.equals("group")){
			controlIn.put(name, new InNode(new DeviceGroup()));
		}
	}

	private void declareNodeOut(String name, String type){
		if(type.equals("range")){
			controlOut.put(name, new OutNode(this, 0));
		}else if(type.equals("state")){
			controlOut.put(name, new OutNode(this, false));
		}else if(type.equals("frame")){
			controlOut.put(name, new OutNode(this, new Frame()));
		}else if(type.equals("group")){
			controlOut.put(name, new OutNode(this, new DeviceGroup()));
		}
	}

	private void nodes(){
		/* controls in 	//	controls out
		- enabled

		/* content in 	//	content out
		- frame 			- frame
		- group

		*/
		controlIn.put("enabled",	new InNode(true));

		contentIn.put("frame", 		new InNode(new Frame()));
		contentIn.put("group", 		new InNode(new DeviceGroup()));

		contentOut.put("frame", 	new OutNode(this, new Frame()));
	}

	public void tick(){
		if((Boolean) get("enabled")){
			set("frame", filter((Frame)get("frame")));
		}else{
			set("frame", get("frame"));
		}
	}

	private Frame filter(Frame frame){
		try{
			if(hasForEachFrame){
			 	frame = (Frame)  script.invocable().invokeFunction("forEachFrame", frame);
			}

			DeviceGroup group = (DeviceGroup)get("group");

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
}