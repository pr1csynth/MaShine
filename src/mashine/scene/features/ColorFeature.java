package mashine.scene.features;

import java.util.LinkedHashMap;

import mashine.ui.FlatColor;

public abstract class ColorFeature extends EditableFeature {

	private static final long serialVersionUID = 0xC010F301L;
	
	protected FlatColor linkedColor = null;

	public ColorFeature(String type, int footprint){
		super(type, footprint);
	}

	public ColorFeature(ColorFeature f){
		super(f);
	}

	public ColorFeature(Feature f){
		super(f);
	}

	public void link(FlatColor c){
		linkedColor = c;
	}

	public void unlink(){
		linkedColor = null;
	}

	public void setField(String fieldName, int value){
		if(fields.containsKey(fieldName)){
			unlink();
			fields.put(fieldName, value);
		}
	}

	public FlatColor getLinkedColor(){
		return linkedColor;
	}

	public abstract FlatColor getColor();
	public abstract LinkedHashMap<String,Integer> getFields();
	public abstract short[] toArray();

}