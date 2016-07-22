/**
 *  Instance of a device with features and addresses
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import mashine.scene.features.Feature;

public class Device implements Serializable{

	private static final long serialVersionUID = 0xDE410001L;

	private int startAddress;
	private int universe;
	private int footprint = 0;
	private int x;
	private int y;
	private int w;
	private int h;
	private String id;
	private String name;
	private ArrayList<Feature> features;

	public Device(String name, int startAddress, int universe, int x, int y, int width, int height){
		this.name = name;
		this.startAddress = startAddress;
		this.universe = universe;
		footprint = 0;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		this.id = UUID.randomUUID().toString();
		features = new ArrayList<Feature>();
	}

	public Device(Device d, String name){
		this.name = name;
		this.startAddress = d.getStartAddress();
		this.universe = d.getUniverse();
		features = d.getFeatures();
		for(Feature f : features){
			footprint += f.getFootprint();
		}
		this.x = d.getX();
		this.y = d.getY() + 5 + d.getHeight();;
		this.w = d.getWidth();
		this.h = d.getHeight();
		
		this.id = UUID.randomUUID().toString();
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

	public String getIdentifier(){return id;}
	public String getName(){return name;}
	public int getStartAddress(){return startAddress;}
	public int getFootprint() {return footprint;}
	public int getUniverse(){return universe;}

	public void setName(String name){this.name = name;}
	public void setStartAddress(int address){this.startAddress = address;}
	public void setUniverse(int universe){this.universe = universe;}

	public void moveUp(){y --; y = Math.max(0, y);}
	public void moveDown(){y ++;}
	public void moveLeft(){x --; x = Math.max(0, x);}
	public void moveRight(){x ++;}

	public void setX(int v){x = v;}
	public void setY(int v){y = v;}
	public void setWidth(int v){w = v;}
	public void setHeight(int v){h = v;}

	public static ArrayList<Feature> commonFeatures(ArrayList<Device> devices){

		ArrayList<Feature> commonFeatures = new ArrayList<Feature>();

		if(!devices.isEmpty()){		
			commonFeatures = devices.get(0).getFeatures();
			for(Device d : devices){
				ArrayList<Feature> deviceFeatures = (d).getFeatures();

				Iterator<Feature> cfi = commonFeatures.iterator();
				while(cfi.hasNext()){
					Feature cf = cfi.next();
					boolean found = false;
					for(Feature df : deviceFeatures){
						if(df.getType() == cf.getType())
							found = true;
					}

					if(!found){
						cfi.remove();
					}
				}
			}
		}

		return commonFeatures;
	}
}