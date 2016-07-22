package mashine.scene.features;


public final class SingleField extends EditableFeature {

	private static final long serialVersionUID = 0x11F310001L;
	
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