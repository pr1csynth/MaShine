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
import java.io.Serializable;

import mashine.engine.Filter;
import mashine.scene.Sequence;
import mashine.scene.Frame;
import mashine.scene.Device;
import mashine.scene.DeviceGroup;
import mashine.scene.features.*;
import mashine.ui.Colors;
import mashine.ui.FlatColor;

public class Bank implements Serializable{

	private ArrayList<Sequence> sequences;
	private HashMap<String, Filter> filters;
	private ArrayList<FlatColor> colors;

	public Bank(){
		sequences = new ArrayList<Sequence>();
		filters = new HashMap<String, Filter>();
		colors = new ArrayList<FlatColor>();

		sequences.add(new Sequence("unamed sequence"));

		for(int i = 0; i < 154; i++){
			colors.add(new FlatColor(0xFF, 0x00, 0x00)
				.withHue((i % 14)/(float)14.0)
				.withBrightness((float) Math.floor((167-i)/14)/11)
				.withAlpha(0));
		}

		colors.add(Colors.WHITE.withAlpha(255));
		for(int i = 0; i < 5; i++){
			colors.add(Colors.BLACK.withAlpha(0));
		}

		filters.put("dimmer", new Filter("dimmer", new Filter.Robot(){
			public void setup(Filter filter){
				filter.declare("value", Filter.RANGE);
			}

			public Frame f(Filter filter, Frame frame){
				
				for(EditableFeature f : frame.getFeatures().values()){
					if(f instanceof ColorFeature){
						ColorFeature c = (ColorFeature) f;
						c.link(c.getLinkedColor().dim((float)filter.getRange("value")));
					}
				}
				return frame;
			}
		}));

		filters.put("blackout", new Filter("blackout", new Filter.Robot(){
			public Frame f(Filter filter, Frame frame){	
				for(EditableFeature f : frame.getFeatures().values()){
					if(f instanceof ColorFeature){
						ColorFeature c = (ColorFeature) f;
						c.link(new FlatColor(0,0,0,0));
					}
				}
				return frame;
			}
		}));

		filters.put("whiteout", new Filter("whiteout", new Filter.Robot(){
			public Frame f(Filter filter, Frame frame){	
				Map<Device, Integer> weights = filter.getGroup().getDevices();
				for(Device d : weights.keySet()){
					List<Feature> feats = d.getFeatures();
					for(Feature f : feats){
						if(f instanceof ColorFeature){
							ColorFeature c = (ColorFeature) f;
							if(f instanceof RGB) c = new RGB();
							if(f instanceof RGBW) c = new RGBW();
							c.link(new FlatColor(255));
							frame.addFeature(d, c);
						}
					}
				}
				return frame;
			}
		}));

		filters.put("dummy", new Filter("dummy", new Filter.Robot(){
			public Frame f(Filter filter, Frame frame){return frame;}
		}));

		filters.put("shine", new Filter("shine", new Filter.Robot(){
			public Frame f(Filter filter, Frame frame){
				Map<Device, Integer> weights = filter.getGroup().getDevices();
				for(Device d : weights.keySet()){
					List<Feature> feats = d.getFeatures();
					for(Feature f : feats){
						if(f instanceof ColorFeature){
							if(Math.random() > 0.95){
								ColorFeature c = (ColorFeature) f;
								if(f instanceof RGB) c = new RGB();
								if(f instanceof RGBW) c = new RGBW();
								c.link(new FlatColor(255));
								frame.addFeature(d, c);
							}
						}
					}
				}
				return frame;
			}
		}));

		filters.put("strobe", new Filter("strobe", new Filter.Robot(){
			public void setup(Filter filter){
				filter.declare("on", Filter.RANGE);
				filter.declare("off", Filter.RANGE);
			}
			public Frame f(Filter filter, Frame frame){
				long on = Math.round(1+ 1000.0*filter.getRange("on"));
				long off = Math.round(1+ 1000.0*filter.getRange("off"));
				if(MaShine.m.millis() % (on+off) >= off){			
					Map<Device, Integer> weights = filter.getGroup().getDevices();
					for(Device d : weights.keySet()){
						List<Feature> feats = d.getFeatures();
						for(Feature f : feats){
							if(f instanceof ColorFeature){
								ColorFeature c = (ColorFeature) f;
								if(f instanceof RGB) c = new RGB();
								if(f instanceof RGBW) c = new RGBW();
								c.link(new FlatColor(255));
								frame.addFeature(d, c);
							}
						}
					}
				}
				return frame;
			}
		}));

		filters.put("direct_control", new Filter("direct_control", new Filter.Robot(){
			public void setup(Filter filter){
				filter.declare("x", Filter.RANGE);
				filter.declare("x_", Filter.RANGE);
				filter.declare("y", Filter.RANGE);
				filter.declare("y_", Filter.RANGE);
			}

			public Frame f(Filter filter, Frame frame){
				Map<Device, Integer> weights = filter.getGroup().getDevices();
				for(Device d : weights.keySet()){
					List<Feature> feats = d.getFeatures();
					for(Feature f : feats){
						if(f instanceof Coords){
							Coords cf = new Coords();
							boolean symetry = weights.get(d) % 2 == 1;

							double pan  = (symetry ? 255 : 0) + (symetry ? -1 : 1)*filter.getRange("x")*255;
							double pan_ = (symetry ? 255 : 0) + (symetry ? -1 : 1)*filter.getRange("x_")*255;

							cf.setField("x", (int) Math.round(pan));
							cf.setField("x_", (int) Math.round(pan_));
							cf.setField("y", (int) Math.round(filter.getRange("y")*255));
							cf.setField("y_", (int) Math.round(filter.getRange("y_")*255));
							frame.addFeature(d, cf);
						}
					}
				}
				return frame;
			}
		}));

		filters.put("hue_abs", new Filter("hue_abs", new Filter.Robot(){
			public void setup(Filter filter){
				filter.declare("newhue", Filter.RANGE);
			}
			public Frame f(Filter filter, Frame frame){
				Map<Device, Integer> weights = filter.getGroup().getDevices();
				for(Device d : weights.keySet()){
					List<Feature> feats = d.getFeatures();
					for(Feature f : feats){
						if(f instanceof ColorFeature && frame.isIn(d,f)){
							ColorFeature c = (ColorFeature) frame.getFeature(d, f);
							c.link(c.getLinkedColor().withHue((float)filter.getRange("newhue")));
						}
					}
				}
				return frame;
			}
		}));

		filters.put("hue_rot", new Filter("hue_rot", new Filter.Robot(){
			public void setup(Filter filter){
				filter.declare("rotation", Filter.RANGE);
			}
			public Frame f(Filter filter, Frame frame){
				Map<Device, Integer> weights = filter.getGroup().getDevices();
				for(Device d : weights.keySet()){
					List<Feature> feats = d.getFeatures();
					for(Feature f : feats){
						if(f instanceof ColorFeature && frame.isIn(d,f)){
							ColorFeature c = (ColorFeature) frame.getFeature(d, f);
							c.link(c.getLinkedColor().withRotatedHue((float)filter.getRange("rotation") * 2.0f));
						}
					}
				}
				return frame;
			}
		}));

		filters.put("drop_all", new Filter("drop_all", new Filter.Robot(){
			public Frame f(Filter filter, Frame frame){
				return new Frame();
			}
		}));

		filters.put("fft_graph", new Filter("fft_graph", new Filter.Robot(){
			public Frame f(Filter filter, Frame frame){
				Map<Device, Integer> weights = filter.getGroup().getDevices();
				for(Device d : weights.keySet()){
					int w = weights.get(d);
					List<Feature> feats = d.getFeatures();
					for(Feature f : feats){
						if(f instanceof ColorFeature){
							String fftName = String.format("%2s", w).replace(' ', '0');
							ColorFeature c = (ColorFeature) f;
							if(f instanceof RGB) c = new RGB();
							if(f instanceof RGBW) c = new RGBW();
							FlatColor fc = new FlatColor(100, 0, 255, 0);
							fc.setBrightness((float)MaShine.inputs.getRange("minim.fft."+fftName));
							c.link(fc);
							frame.addFeature(d, c);
							
						}
					}
				}
				return frame;
			}
		}));

	}


	public void addSequence(Sequence seq){sequences.add(seq);}
	public void deleteSequence(Sequence seq){sequences.remove(seq);}
	public ArrayList<Sequence> getSequences(){return sequences;}
	public Sequence getSequence(int index){return sequences.get(index);}
	public int getSequencesSize(){return sequences.size();}

	public ArrayList<FlatColor> getColors(){return colors;}

	public Filter getFilter(String f){return filters.get(f);}
	public HashMap<String, Filter> getFilters(){return filters;}

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
}