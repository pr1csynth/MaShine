package mashine.scene.features;

public final class Zoom extends EditableFeature {

	private static final long serialVersionUID = 0x2003D0001L;
	
	public Zoom(){
		super("zoom", 2);
		fields.put("z", 127);
		fields.put("z_", 0);
	}

	public Zoom(Coords f){
		super(f);
	}
	public Zoom(Feature f){
		super(f);
	}
}