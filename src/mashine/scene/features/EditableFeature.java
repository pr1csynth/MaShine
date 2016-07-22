package mashine.scene.features;


public class EditableFeature extends Feature{

	private static final long serialVersionUID = 0x3D3710001L;

	public EditableFeature(String type, int footprint){
		super(type, footprint);
	}

	public EditableFeature(EditableFeature f){
		super(f);
	}

	public EditableFeature(Feature f){
		super(f);
	}
}