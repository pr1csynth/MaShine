/**
 *  Instance of a device feature containing fields
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene.features;

import mashine.scene.*;
import mashine.*;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.Serializable;

public abstract class Feature implements Serializable {

	protected int footprint;
	protected LinkedHashMap<String,Integer> fields;
	protected String type;

	public Feature(String type, int footprint){
		this.type = type;
		this.footprint = footprint;
		fields = new LinkedHashMap<String, Integer>();
	}

	public Feature(String type, int footprint, LinkedHashMap<String,Integer> fields){
		this.type = type;
		this.footprint = footprint;
		this.fields = new LinkedHashMap<String, Integer>(fields);
	}

	public Feature(Feature f){
		this(f.getType(), f.getFootprint(), f.getFields());
	}

	public static Feature cloneFeature(Feature f){

		Feature n = null;

		if(f instanceof RGB){
			n = new RGB(f);
		}else if(f instanceof RGBW){
			n = new RGBW(f);
		}else if(f instanceof FixedField){
			n = new FixedField(f);
		}

		return n;
	}

	public String getType(){
		return type;
	}
	public  int getFootprint(){
		return footprint;
	}
	public LinkedHashMap<String,Integer> getFields(){
		return new LinkedHashMap<String,Integer>(fields);
	}

	public Integer getField(String fieldName){
		if(fields.containsKey(fieldName)){
			return fields.get(fieldName);
		}else{
			return null;
		}
	}

	public void setField(String fieldName, int value){
		if(fields.containsKey(fieldName))
			fields.put(fieldName, value);
	}

	public String toString(){
		String stringFields = "";
		for(String f : fields.keySet()){
			stringFields += ":"+fields.get(f);
		}
		return type +":"+ footprint + stringFields;
	}

	public ArrayList<Integer> to255(){
		ArrayList<Integer> r = new ArrayList<Integer>();
		for(String f : fields.keySet()){
			r.add(fields.get(f));
		}
		return r;
	}
}