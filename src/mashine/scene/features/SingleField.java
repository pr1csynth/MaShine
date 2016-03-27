package mashine.scene.features;

import mashine.scene.*;

public final class SingleField extends EditableFeature {
	public SingleField(String fieldName, int singleValue){
		super(fieldName, 1);
		fields.put(fieldName, singleValue);
	}

	public SingleField(String fieldName){
		this(fieldName, 0);
	}

	public SingleField(SingleField f){
		super(f);
	}

	public SingleField(Feature f){
		super(f);
	}

	public void set(int value){
		setField(type, value);
	}
}