/**
 *  Collection of sequences and filters
 *
 *	@author procsynth - Antoine Pintout
 *	@since  19-03-2016`
 */
package mashine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.io.Serializable;

import java.util.Scanner;

import mashine.engine.ScriptManager;
import mashine.scene.Sequence;
import mashine.scene.Frame;
import mashine.scene.Device;
import mashine.scene.DeviceGroup;
import mashine.scene.features.*;
import mashine.ui.Colors;
import mashine.ui.FlatColor;

import processing.core.PConstants;

public class Bank implements Serializable{

	private ArrayList<Sequence> sequences;
	private ArrayList<FlatColor> colors;
	public ScriptManager filters;

	public Bank(){
		sequences = new ArrayList<Sequence>();
		colors = new ArrayList<FlatColor>();

		sequences.add(new Sequence("unamed sequence"));

		for(int i = 0; i < 154; i++){
			colors.add(new FlatColor(0xFF, 0x00, 0x00)
				.withHue((i % 14)/(float)14.0)
				.withSaturation((float) Math.floor((167-i)/14)/11)
				.withAlpha(0));
		}

		colors.add(Colors.WHITE.withAlpha(255));
		for(int i = 0; i < 5; i++){
			colors.add(Colors.BLACK.withAlpha(0));
		}

		filters = null;

		try{
			String[] filtersList = new Scanner(getClass().getResourceAsStream("/javascript/filters.list"), "utf-8").useDelimiter("\\A").next().split("\n");
			for(int i = 0; i < filtersList.length; i++) filtersList[i] = "/javascript/filters/"+filtersList[i];
			filters = new ScriptManager(filtersList, "/javascript/filters.js");
		}catch(Exception e){e.printStackTrace();}

		MaShine.inputs.registerAction("mashine.filters.reload", new Do(){public void x(){filters.reloadScripts();}});
		

	}



	public void addSequence(Sequence seq){sequences.add(seq);}
	public void deleteSequence(Sequence seq){sequences.remove(seq);}
	public ArrayList<Sequence> getSequences(){return sequences;}
	public Sequence getSequence(int index){return sequences.get(index);}
	public int getSequencesSize(){return sequences.size();}

	public ArrayList<FlatColor> getColors(){return colors;}

	public List<String> getFilters(){return filters.getScripts();}

	public static class SaveObject implements Serializable{
		public ArrayList<Sequence> sequences;
		public ArrayList<FlatColor> colors;

		public SaveObject(ArrayList<Sequence> sequences, ArrayList<FlatColor> colors){
			this.sequences = sequences;
			this.colors = colors;
		}
	}

	public Object save(){
		return new SaveObject(sequences, colors);
	}

	public void restore(Object restoredObject){
		SaveObject s = (SaveObject) restoredObject;
		sequences = s.sequences;
		colors = s.colors;
		MaShine.ui.setSelectedSequence(sequences.get(0));
	}

	// private void registerFilters(){
	// 	filters.put("dimmer", new Filter("dimmer", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("value", Filter.RANGE);
	// 		}

	// 		public Frame f(Filter filter, Frame frame){
				
	// 			for(EditableFeature f : frame.getFeatures().values()){
	// 				if(f instanceof ColorFeature){
	// 					ColorFeature c = (ColorFeature) f;
	// 					c.link(c.getLinkedColor().dim((float)filter.getRange("value")));
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("blackout", new Filter("blackout", new Filter.Robot(){
	// 		public Frame f(Filter filter, Frame frame){	
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature){
	// 						ColorFeature c = (ColorFeature) f;
	// 						c.link(new FlatColor(0));
	// 						frame.addFeature(d, c);
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("whiteout", new Filter("whiteout", new Filter.Robot(){
	// 		public Frame f(Filter filter, Frame frame){	
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature){
	// 						ColorFeature c = (ColorFeature) f;
	// 						c.link(new FlatColor(255));
	// 						frame.addFeature(d, c);
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("shine", new Filter("shine", new Filter.Robot(){
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature){
	// 						if(Math.random() > 0.95){
	// 							ColorFeature c = (ColorFeature) f;
	// 							c.link(new FlatColor(255));
	// 							frame.addFeature(d, c);
	// 						}
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("strobe", new Filter("strobe", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("on", Filter.RANGE);
	// 			filter.declare("off", Filter.RANGE);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			long on = Math.round(1+ 1000.0*filter.getRange("on"));
	// 			long off = Math.round(1+ 1000.0*filter.getRange("off"));
	// 			if(MaShine.m.millis() % (on+off) >= off){			
	// 				Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 				for(Device d : weights.keySet()){
	// 					List<Feature> feats = d.getFeatures();
	// 					for(Feature f : feats){
	// 						if(f instanceof ColorFeature){
	// 							ColorFeature c = (ColorFeature) f;
	// 							c.link(new FlatColor(255));
	// 							frame.addFeature(d, c);
	// 						}
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("direct_control", new Filter("direct_control", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("x", Filter.RANGE);
	// 			filter.declare("x_", Filter.RANGE);
	// 			filter.declare("y", Filter.RANGE);
	// 			filter.declare("y_", Filter.RANGE);
	// 		}

	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof Coords){
	// 						Coords cf = new Coords();
	// 						boolean symetry = weights.get(d) % 2 == 1;

	// 						double pan  = (symetry ? 255 : 0) + (symetry ? -1 : 1)*filter.getRange("x")*255;
	// 						double pan_ = (symetry ? 255 : 0) + (symetry ? -1 : 1)*filter.getRange("x_")*255;

	// 						cf.setField("x", (int) Math.round(pan));
	// 						cf.setField("x_", (int) Math.round(pan_));
	// 						cf.setField("y", (int) Math.round(filter.getRange("y")*255));
	// 						cf.setField("y_", (int) Math.round(filter.getRange("y_")*255));
	// 						frame.addFeature(d, cf);
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("hue_abs", new Filter("hue_abs", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("newhue", Filter.RANGE);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature && frame.isIn(d,f)){
	// 						ColorFeature c = (ColorFeature) frame.getFeature(d, f);
	// 						c.link(c.getLinkedColor().withHue((float)filter.getRange("newhue")));
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("hue_rot", new Filter("hue_rot", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("rotation", Filter.RANGE);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature && frame.isIn(d,f)){
	// 						ColorFeature c = (ColorFeature) frame.getFeature(d, f);
	// 						c.link(c.getLinkedColor().withRotatedHue((float)filter.getRange("rotation") * 2.0f));
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("drop_all", new Filter("drop_all", new Filter.Robot(){
	// 		public Frame f(Filter filter, Frame frame){
	// 			return new Frame();
	// 		}
	// 	}));

	// 	filters.put("fft_graph", new Filter("fft_graph", new Filter.Robot(){
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				int w = weights.get(d);
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature){
	// 						String fftName = String.format("%2s", w).replace(' ', '0');
	// 						ColorFeature c = (ColorFeature) f;
	// 						FlatColor fc = new FlatColor(100, 0, 255, 0);
	// 						fc.setBrightness((float)MaShine.inputs.getRange("minim.fft."+fftName));
	// 						c.link(fc);
	// 						frame.addFeature(d, c);
							
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("wave_circle", new Filter("wave_circle", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("size", Filter.RANGE);
	// 			filter.declare("speed", Filter.RANGE);
	// 			filter.declare("speedMult", Filter.RANGE);
	// 			filter.declare("offset", Filter.LONG);
	// 			filter.setLong("offset", 0L);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			int min = -1; 
	// 			int max = 0;
	// 			for(int v : weights.values()){
	// 				if(min == -1 || v < min) min = v;
	// 				else if(v > max) max = v;
	// 			}
	// 			int length = Math.max(1, (max - min) + 1);
	// 			float size = (float)filter.getRange("size")*length/2f;
	// 			long offset = filter.getLong("offset");
	// 			offset += filter.getRange("speed")*9000f*filter.getRange("speedMult")*10f;
	// 			filter.setLong("offset", offset);

	// 			float scaledOffset = offset/10000.0f;


	// 			for(Device d : weights.keySet()){
	// 				int w = weights.get(d);
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature && frame.isIn(d,f)){
	// 						ColorFeature c = (ColorFeature) frame.getFeature(d, f);
	// 						c.link(c.getLinkedColor().dim(waveFunction(scaledOffset, w, size, length)));
	// 					}
	// 				}
	// 			}

	// 			return frame;
	// 		}

	// 		public float waveFunction(float offset, int index, float size, int length){
	// 			offset = offset % length;
	// 			if(length < offset+ 2*size && index < (offset + 2*size) - length){
	// 				offset = (offset+ 2*size) - length;
	// 				return (float)Math.cos(PConstants.PI*(-index + offset)/size - PConstants.PI)/2f+0.5f;
	// 			}else if(offset < index && index < offset + 2*size){
	// 				return (float)Math.cos(PConstants.PI*(-index + offset)/size - PConstants.PI)/2f+0.5f;
	// 			}
	// 			return 0f;	
	// 		}
	// 	}));

	// 	filters.put("wave_bounce", new Filter("wave_bounce", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("size", Filter.RANGE);
	// 			filter.declare("speed", Filter.RANGE);
	// 			filter.declare("speedMult", Filter.RANGE);
	// 			filter.declare("offset", Filter.LONG);
	// 			filter.setLong("offset", 0L);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			int min = -1;
	// 			int max = 0;
	// 			for(int v : weights.values()){
	// 				if(min == -1 || v < min) min = v;
	// 				else if(v > max) max = v;
	// 			}
	// 			int length = Math.max(1, (max - min) + 1);
	// 			float size = (float)filter.getRange("size")*length/2f;
	// 			long offset = filter.getLong("offset");
	// 			offset += filter.getRange("speed")*9000f*filter.getRange("speedMult")*10f;
	// 			filter.setLong("offset", offset);

	// 			float scaledOffset = offset/10000.0f;


	// 			for(Device d : weights.keySet()){
	// 				int w = weights.get(d);
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature && frame.isIn(d,f)){
	// 						ColorFeature c = (ColorFeature) frame.getFeature(d, f);
	// 						c.link(c.getLinkedColor().dim(waveFunction(scaledOffset, w, size, length)));
	// 					}
	// 				}
	// 			}

	// 			return frame;
	// 		}

	// 		public float waveFunction(float offset, int index, float size, int length){
	// 			length --;			
	// 			offset = Math.abs((offset % (2*length)) -(length));
	// 			if(offset - size <= index && index < offset + size){
	// 				return (float)Math.cos(PConstants.PI*(-index + (offset - size))/size - PConstants.PI)/2f+0.5f;
	// 			}
	// 			return 0f;	
	// 		}
	// 	}));

	// 	filters.put("waves", new Filter("waves", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("size", Filter.RANGE);
	// 			filter.declare("speed", Filter.RANGE);
	// 			filter.declare("min", Filter.RANGE);
	// 			filter.declare("max", Filter.RANGE);
	// 			filter.declare("speedMult", Filter.RANGE);
	// 			filter.declare("direction", Filter.STATE);
	// 			filter.declare("oneWave", Filter.STATE);
	// 			filter.declare("offset", Filter.LONG);
	// 			filter.setLong("offset", 0L);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			int min = -1; 
	// 			int max = 0;
	// 			for(int v : weights.values()){
	// 				if(min == -1 || v < min) min = v;
	// 				else if(v > max) max = v;
	// 			}
	// 			int length = Math.max(1, (max - min) + 1);
	// 			float size = (float)filter.getRange("size")*length/2f;
	// 			long offset = filter.getLong("offset");
	// 			float minDim = Math.min((float)filter.getRange("min"), (float)filter.getRange("max"));
	// 			float rangeDim = Math.max((float)filter.getRange("min"), (float)filter.getRange("max")) - minDim;
	// 			//MaShine.println(filter.getRange("min") + "\t" + filter.getRange("max")+ "\t" + rangeDim);
	// 			boolean cut = filter.getState("oneWave");

	// 			offset += (filter.getState("direction") ? 1 : -1) *filter.getRange("speed")*9000f*filter.getRange("speedMult")*10f;
	// 			filter.setLong("offset", offset);

	// 			float scaledOffset = offset/10000.0f;


	// 			for(Device d : weights.keySet()){
	// 				int w = weights.get(d);
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature && frame.isIn(d,f)){
	// 						ColorFeature c = (ColorFeature) frame.getFeature(d, f);
	// 						float value = minDim + rangeDim * waveFunction(scaledOffset, w, size, length, cut);
	// 						//MaShine.println(value);
	// 						c.link(c.getLinkedColor().dim(value));
	// 					}
	// 				}
	// 			}

	// 			return frame;
	// 		}

	// 		public float waveFunction(float offset, int index, float size, int length, boolean cut){
	// 			if(cut)
	// 				offset = offset % length;
	// 			if(cut && length < offset+ 2*size && index < (offset + 2*size) - length){
	// 				offset = (offset+ 2*size) - length;
	// 				return (float)Math.cos(PConstants.PI*(-index + offset)/size - PConstants.PI)/2f+0.5f;
	// 			}
	// 			if(!cut || (cut && offset < index && index < offset + 2*size)){
	// 				return (float)Math.cos(PConstants.PI*(-index + offset)/size - PConstants.PI)/2f+0.5f;
	// 			}
	// 			return 0f;	
	// 		}
	// 	}));

	// 	filters.put("group_dimmer", new Filter("group_dimmer", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("value", Filter.RANGE);
	// 		}

	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();

	// 			for(Device d : weights.keySet()){
	// 				int w = weights.get(d);
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature && frame.isIn(d,f)){
	// 						ColorFeature c = (ColorFeature) frame.getFeature(d, f);
	// 						c.link(c.getLinkedColor().dim((float)filter.getRange("value")));
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("shine_adv", new Filter("shine_adv", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("probabilty", Filter.RANGE);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			float probabilty = (float)filter.getRange("probabilty");
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature){
	// 						if(Math.random() < probabilty){
	// 							ColorFeature c = (ColorFeature) f;
	// 							c.link(new FlatColor(255));
	// 							frame.addFeature(d, c);
	// 						}
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));
	// 	filters.put("easing_basic", new Filter("easing_basic", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("rate", Filter.RANGE);
	// 			filter.declare("currentFrame", Filter.FRAME);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			Frame currentFrame = filter.getFrame("currentFrame");
	// 			Frame targetFrame = new Frame(frame);
	// 			Frame returnFrame = new Frame();
	// 			double rate = filter.getRange("rate");
	// 			rate = Math.max(0.002, rate*rate);

	// 			Map<String,EditableFeature> targetFeatures = targetFrame.getFeatures(); 

	// 			for(String targetId : targetFeatures.keySet()){

	// 				EditableFeature currentFeature = currentFrame.getFeature(targetId);
	// 				EditableFeature targetFeature = (EditableFeature)Feature.cloneFeature(targetFeatures.get(targetId));
	// 				// if target feature is not in current,  just put it in. 
	// 				if(currentFeature != null){
	// 					for(String targetFieldId : targetFeature.getFields().keySet()){
	// 						int currentFieldValue = currentFeature.getField(targetFieldId); 
	// 						int targetFieldValue = targetFeature.getField(targetFieldId);
	// 						if(currentFieldValue != targetFieldValue){
	// 							int newValue = 0;
	// 							double change = (targetFieldValue - currentFieldValue) * rate;
	// 							if(change < 0) newValue = currentFieldValue + (int)Math.round(Math.min(-1, change));
	// 							if(change > 0) newValue = currentFieldValue + (int)Math.round(Math.max(+1, change)); 
	// 							currentFeature.setField(targetFieldId,newValue);
	// 						}
	// 					}

	// 					returnFrame.addFeature(targetId, currentFeature);
	// 				}else{
	// 					returnFrame.addFeature(targetId, targetFeature);
	// 				}
	// 			}

	// 			filter.setFrame("currentFrame", returnFrame);
	// 			return returnFrame;
	// 		}
	// 	}));

	// 	filters.put("ERY", new Filter("ERY", new Filter.Robot(){
	// 		public Frame f(Filter filter, Frame frame){
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					int w = weights.get(d);
	// 					if(f instanceof Coords){
	// 						Coords cf = new Coords();

	// 						cf.setField("x", (int) Math.ceil(MaShine.inputs.getRange("udp.ERY."+ w +".pan")));
	// 						cf.setField("x_", (int) Math.ceil(MaShine.inputs.getRange("udp.ERY."+ w +".panf")));
	// 						cf.setField("y", (int) Math.ceil(MaShine.inputs.getRange("udp.ERY."+ w +".tilt")));
	// 						cf.setField("y_", (int) Math.ceil(MaShine.inputs.getRange("udp.ERY."+ w +".tiltf")));
	// 						frame.addFeature(d, cf);
	// 					}else if(f instanceof Zoom){
	// 						Zoom cf = new Zoom();

	// 						cf.setField("z", (int) Math.ceil(MaShine.inputs.getRange("udp.ERY."+ w +".zoom")));
	// 						cf.setField("z_", (int)Math.ceil(MaShine.inputs.getRange("udp.ERY."+ w +".zoomf")));
	// 						frame.addFeature(d, cf);
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));

	// 	filters.put("shine_dim", new Filter("shine_dim", new Filter.Robot(){
	// 		public void setup(Filter filter){
	// 			filter.declare("probabilty", Filter.RANGE);
	// 		}
	// 		public Frame f(Filter filter, Frame frame){
	// 			float probabilty = (float)filter.getRange("probabilty");
	// 			Map<Device, Integer> weights = filter.getGroup().getDevices();
	// 			for(Device d : weights.keySet()){
	// 				List<Feature> feats = d.getFeatures();
	// 				for(Feature f : feats){
	// 					if(f instanceof ColorFeature){
	// 						if(Math.random() < probabilty){
	// 							ColorFeature c = (ColorFeature) frame.getFeature(d, f);
	// 							c.link(c.getLinkedColor().dim(0f));
	// 						}
	// 					}
	// 				}
	// 			}
	// 			return frame;
	// 		}
	// 	}));	
	// }
}