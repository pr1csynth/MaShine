package mashine.scene.features;

import mashine.scene.*;
import mashine.*;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class EditableFeature extends Feature{

	public EditableFeature(String type, int footprint){
		super(type, footprint);
	}

	public EditableFeature(EditableFeature f){
		super(f);
	}

	public EditableFeature(Feature f){
		super(f);
	}

	public void setField(String fieldName, int value){
		if(fields.containsKey(fieldName))
			fields.put(fieldName, value);
	}
}