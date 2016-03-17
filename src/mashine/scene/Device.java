/**
 *  Instance of a device with features and addresses
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import mashine.*;
import mashine.scene.features.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Device{

	private int startAddress;
	private int universe;
	private int footprint;
	private int x;
	private int y;
	private int w;
	private int h;
	private String identifier;
	private ArrayList<Feature> features;

	public Device(String identifier, int startAddress, int universe, int x, int y, int width, int height){
		this.identifier = identifier;
		this.startAddress = startAddress;
		this.universe = universe;
		footprint = 0;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		features = new ArrayList<Feature>();
	}

	public void addFeature(Feature feature){
		for(Feature f : features){
			if(f.getType() == feature.getType())
				return;
		}
		features.add(feature);
		footprint += feature.getFootprint();
	}

	public void removeFeature(String featureType){
		Iterator<Feature> fi = features.iterator();
		while(fi.hasNext()){
			Feature f = fi.next();
			if(f.getType().equals(featureType)){
				features.remove(f);
				break;
			}
		}
	}
	public void updateFeature(String featureField, int value){
		String[] ff = featureField.split("\\.");
		Iterator<Feature> fi = features.iterator();
		while(fi.hasNext()){
			Feature f = fi.next();
			if(f.getType().equals(ff[0])){
				f.setField(ff[1], value);
				break;
			}
		}
	}

	public Integer getFeatureField(String featureField){
		String[] ff = featureField.split("\\.");
		Iterator<Feature> fi = features.iterator();
		while(fi.hasNext()){
			Feature f = fi.next();
			if(f.getType().equals(ff[0])){
				return f.getField(ff[1]);
			}
		}

		return null;
	}

	public ArrayList<Feature> getFeatures(){
		return new ArrayList<Feature>(features);
	}

	public int getX(){return x;}
	public int getY(){return y;}
	public int getWidth(){return w;}
	public int getHeight(){return h;}

	public String getIdentifier(){return identifier;}
	public int getStartAddress(){return startAddress;}
	public int getUniverse(){return universe;}

	public void setIdentifier(String id){this.identifier = id;}
	public void setStartAddress(int address){this.startAddress = address;}
	public void setUniverse(int universe){this.universe = universe;}


}