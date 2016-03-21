package mashine.scene.features;

import mashine.scene.*;
import mashine.ui.*;

public final class RGBW extends ColorFeature {
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
		fields.put("red", v);
	}
	public void setGreen(int v){
		fields.put("green", v);
	}	
	public void setBlue(int v){
		fields.put("blue", v);
	}	
	public void setWhite(int v){
		fields.put("white", v);
	}	

	public FlatColor getColor(){
		return new FlatColor(fields.get("red"), fields.get("green"), fields.get("blue"));
	}
}