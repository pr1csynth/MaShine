package mashine.ui.elements;

import processing.core.PConstants;
import mashine.MaShine;
import mashine.utils.*;
import mashine.ui.Colors;
import mashine.ui.Drawable;
import mashine.ui.Element;

public class RangeInput extends Element{

	private Float value;
	private String stringValue;
	private Float min;
	private Float max;
	private Float step;

	public RangeInput(Drawable parent, Float defaultValue, Float min, Float max, Float step, int x, int y, int width){
		super(parent, x, y, width, 15);
		value = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		updateStringValue();

	}
	public RangeInput(Drawable parent, int x, int y, int width){
		this(parent, 0f, 0f, 255f, 1f, x, y, width);
	}

	public RangeInput(Drawable parent, int defaultValue, int x, int y, int width){
		this(parent, (float) defaultValue, 0f, 255f, 1f, x, y, width);
	}

	public Float value(){
		return value;
	}

	public void setMin(Float min){this.min = min;}
	public void setMax(Float max){this.max = max;}
	public void setStep(Float step){this.step = step;}

	public void setMin(int min){setMin((float) min);}
	public void setMax(int max){setMax((float) max);}
	public void setStep(int step){setStep((float) step);}

	public void drawContent(){
		P.canvas.noStroke();

		boolean f = enabled && hasFocus() && P.hasFocus();

		if(f){
			if(MaShine.inputs.getState("keyboard.8.press")){
				if(stringValue.length() > 0)
					stringValue = stringValue.substring(0, stringValue.length() - 1);
			}else if(MaShine.inputs.getState("keyboard.9.press") || MaShine.inputs.getState("keyboard.10.press")){
				focus = false;
				value = Float.parseFloat(stringValue);
				value = normalize(value);
				updateStringValue();
			}else if(MaShine.inputs.getState("keyboard.147.press")){
				stringValue = "";
			}else if(MaShine.inputs.getLastKey() != "" && stringValue.length() < maxLength()){
				String newStringValue = stringValue + MaShine.inputs.getLastKey();
				try{
					Float.parseFloat(newStringValue);
					stringValue += MaShine.inputs.getLastKey();
				}catch(Exception e){

				}	
			}

			if(isDragged()){
				int relX = MaShine.max(0, Math.min(P.mouseX() - x, width));
				value = normalize((float) (max - min + step) * ((float) relX / width));
				updateStringValue();
			}

		}

		if(enabled)
			FlatColor.fill(P.canvas, Colors.WHITE);
		else
			FlatColor.fill(P.canvas, Colors.MATERIAL.GREY._400);
		P.canvas.rect(x, y, width, height);

		P.canvas.noStroke();

		if(enabled)
			FlatColor.fill(P.canvas, Colors.MATERIAL.CYAN._300);
		else
			FlatColor.fill(P.canvas, Colors.MATERIAL.GREY._600);
		P.canvas.rect(x, y, Math.min(width,1 + width * (tempValueFromString(stringValue)/(max - min + step))), height);

		if(f){
			FlatColor.stroke(P.canvas, Colors.MATERIAL.CYAN.A700);
			P.canvas.noFill();
			P.canvas.rect(x, y, width-1, height-1);
		}

		FlatColor.fill(P.canvas, Colors.MATERIAL.BLUE_GREY._900);
		P.canvas.textAlign(PConstants.LEFT, PConstants.CENTER);
		P.canvas.text(stringValue + (MaShine.m.millis() % 1200 > 600 && enabled && stringValue.length() < maxLength() && hasFocus() ? "_" : ""), x + 3, y +height/2+1);
	}

	private Float normalize(Float val){
		if(val != null){
			val = Math.min(max, Math.max(min, val));
			val = val - val % step;		
		}

		return val;
	}

	protected float tempValueFromString(String s){

		float val = min;

		try{
			if(stringValue.length() > 0){
				val = Float.parseFloat(stringValue);
				val = normalize(val);	
			}else{
				val = min;
			}
		}catch(Exception e){}
		return val;
	}

	protected void onDefocus(){
		try{		
			if(stringValue.length() > 0){
				value = Float.parseFloat(stringValue);
				value = normalize(value);	
			}else{
				value = min;
			}

			updateStringValue();
		}catch(Exception e){}
	}

	private void updateStringValue(){
		// if integer step : no point
		if(step % 1 == 0){
			try{
			stringValue = Integer.toString(Math.round(value));
			}catch(Exception ignore){}
		}else{
			stringValue = Float.toString(value);
		}
	}

	private int maxLength(){
		// if integer step : no point
		if(step % 1 == 0){
			return Integer.toString(Math.round(max)).length();
		}else{
			return Float.toString(value).length();
		}
	}

	public void setValue(int v){setValue(new Float(v));}
	public void setValue(Float v){
		value = v;
		value = normalize(value);
		updateStringValue();
	}

	public void setStringValue(String s){
		stringValue = s;
	}

}