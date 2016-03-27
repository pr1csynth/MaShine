package mashine.scene.features;

import mashine.scene.*;
import mashine.ui.*;
import java.util.LinkedHashMap;

public abstract class ColorFeature extends EditableFeature {

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
}