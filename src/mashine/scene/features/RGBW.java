package mashine.scene.features;

import java.util.LinkedHashMap;

import mashine.ui.FlatColor;

public final class RGBW extends ColorFeature {

	private static final long serialVersionUID = 0xC0F20001L;
	
	public RGBW(int red, int green, int blue, int white){
		super("rgbw", 4);
		fields.put("red", red);
		fields.put("green", green);
		fields.put("blue", blue);
		fields.put("white", white);
	}

	public RGBW(int generalDim){
		this(generalDim, generalDim, generalDim, generalDim);
	}

	public RGBW(){
		this(0);
	}

	public RGBW(RGB f){
		super(f);
	}

	public RGBW(Feature f){
		super(f);
	}

	public void setRed(int v){
		setField("red", v);
	}
	public void setGreen(int v){
		setField("green", v);
	}	
	public void setBlue(int v){
		setField("blue", v);
	}	
	public void setWhite(int v){
		setField("white", v);
	}	

	public FlatColor getColor(){
		FlatColor rc;
		if(linkedColor != null){
			rc = linkedColor;
		}else{
			rc = new FlatColor(fields.get("red"), fields.get("green"), fields.get("blue"), fields.get("white"));
		}
		return rc.withAlphaAsWhite();
	}

	public LinkedHashMap<String,Integer> getFields(){
		LinkedHashMap<String,Integer> rf;

		if(linkedColor != null){
			rf = new LinkedHashMap<String,Integer>();
			rf.put("red", linkedColor.getRed());
			rf.put("green", linkedColor.getGreen());
			rf.put("blue", linkedColor.getBlue());
			rf.put("white", linkedColor.getAlpha());
		}else{
			rf = new LinkedHashMap<String,Integer>(fields);
		}

		return rf;
	}

	public short[] toArray(){
		if(null == linkedColor){
			return new short[]{0,0,0,0};
		}
		
		return new short[]{
			(short)linkedColor.getRed(),
			(short)linkedColor.getGreen(),
			(short)linkedColor.getBlue(),
			(short)linkedColor.getAlpha()
		};
	}

}