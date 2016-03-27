package mashine.scene.features;

import mashine.scene.*;
import mashine.ui.*;
import java.util.LinkedHashMap;

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
		this(0);
	}

	public RGB(RGB f){
		super(f);
	}

	public RGB(Feature f){
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

	public FlatColor getColor(){
		FlatColor rc;
		if(linkedColor != null){
			rc = new FlatColor(linkedColor);
		}else{
			rc = new FlatColor(fields.get("red"), fields.get("green"), fields.get("blue"));
		}
		return rc;
	}

	public LinkedHashMap<String,Integer> getFields(){
		LinkedHashMap<String,Integer> rf;

		if(linkedColor != null){
			rf = new LinkedHashMap<String,Integer>();
			rf.put("red", linkedColor.getRed());
			rf.put("green", linkedColor.getGreen());
			rf.put("blue", linkedColor.getBlue());
		}else{
			rf = new LinkedHashMap<String,Integer>(fields);
		}

		return rf;
	}
}