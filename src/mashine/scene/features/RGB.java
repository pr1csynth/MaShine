package mashine.scene.features;

import mashine.scene.*;
import mashine.ui.*;

public final class RGB extends ColorFeature {
	public RGB(int red, int green, int blue){
		super("rgb", 3);
		fields.put("red", red);
		fields.put("green", green);
		fields.put("blue", blue);
	}

	public RGB(int generalDim){
		this(generalDim, generalDim, generalDim);
	}

	public RGB(){
		this(255);
	}

	public RGB(RGB f){
		super(f);
	}

	public RGB(Feature f){
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

	public FlatColor getColor(){
		return new FlatColor(fields.get("red"), fields.get("green"), fields.get("blue"));
	}
}