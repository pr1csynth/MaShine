package mashine.scene.features;


public final class FixedField extends Feature {
	
	private static final long serialVersionUID = 0xF1F31001L;

	public FixedField(String fieldName, int fixedValue){
		super(fieldName, 1);
		fields.put(fieldName, fixedValue);
	}


	public FixedField(String fieldName){
		this(fieldName, 0);
	}

	public FixedField(FixedField f){
		super(f);
	}

	public FixedField(Feature f){
		super(f);
	}
}