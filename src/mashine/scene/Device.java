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

	public void addFeature(Feature f){
		features.add(f);
		footprint += f.getFootprint();
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


}