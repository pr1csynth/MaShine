package mashine.scene.features;

public final class Coords extends EditableFeature {

	private static final long serialVersionUID = 0xC004D0001L;
	
	public Coords(){
		super("coords", 4);
		fields.put("x", 127);
		fields.put("x_", 0);
		fields.put("y", 127);
		fields.put("y_", 0);
	}

	public Coords(Coords f){
		super(f);
	}
	public Coords(Feature f){
		super(f);
	}
}