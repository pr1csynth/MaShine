package mashine.scene.features;

import java.util.LinkedHashMap;
import mashine.ui.FlatColor;

public final class Tradi extends ColorFeature {

	private static final long serialVersionUID = 0xD1DD0001L;

	public Tradi(int dim){
		super("Tradi", 1);
		fields.put("intensity", dim);
	}

	public Tradi(){
		this(0);
	}

	public Tradi(Tradi f){
		super(f);
	}

	public Tradi(Feature f){
		super(f);
	}

	public void setIntensity(int v){
		setField("intensity", v);
	}

	public FlatColor getColor(){
		return getLinkedColor().withAlpha(255);
	}

	public FlatColor getLinkedColor(){
		FlatColor rc;
		if(linkedColor != null){
			rc = linkedColor;
		}else{
			rc = new FlatColor(fields.get("intensity"));
		}
		return rc;
	}

	public LinkedHashMap<String,Integer> getFields(){
		LinkedHashMap<String,Integer> rf;

		if(linkedColor != null){
			rf = new LinkedHashMap<String,Integer>();
			rf.put("intensity", linkedColor.getGrayscale());
		}else{
			rf = new LinkedHashMap<String,Integer>(fields);
		}

		return rf;
	}

	public short[] toArray(){
		if(null == linkedColor){
			return new short[]{
				fields.get("intensity").shortValue()
			};
		}
		
		return new short[]{
			(short)linkedColor.getGrayscale()
		};
	}
}