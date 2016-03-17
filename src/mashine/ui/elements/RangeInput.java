package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;
import java.util.HashMap;

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
	public void setValue(Float value){this.value = value; normalize();}

	public void drawContent(){
		P.canvas.noStroke();


		if(enabled && hasFocus() && P.hasFocus()){
			FlatColor.stroke(P.canvas, Colors.MATERIAL.CYAN.A700);
			if(M.inputs.getState("keyboard.8.press")){
				if(stringValue.length() > 0)
					stringValue = stringValue.substring(0, stringValue.length() - 1);
			}else if(M.inputs.getState("keyboard.9.press") || M.inputs.getState("keyboard.10.press")){
				focus = false;
				value = Float.parseFloat(stringValue);
				normalize();
				updateStringValue();
			}else if(M.inputs.getState("keyboard.147.press")){
				stringValue = "";
			}else if(M.inputs.getLastKey() != "" && stringValue.length() < maxLength()){
				String newStringValue = stringValue + M.inputs.getLastKey();
				try{
					Float.parseFloat(newStringValue);
					stringValue += M.inputs.getLastKey();
				}catch(Exception e){

				}	
			}

		}

		if(enabled)
			FlatColor.fill(P.canvas, Colors.WHITE);
		else
			FlatColor.fill(P.canvas, Colors.MATERIAL.GREY._400);
		P.canvas.rect(x, y, width, height);
		FlatColor.fill(P.canvas, Colors.MATERIAL.BLUE_GREY._900);
		P.canvas.textAlign(P.canvas.LEFT, P.canvas.CENTER);
		P.canvas.text(stringValue + (M.millis() % 1200 > 600 && enabled && stringValue.length() < maxLength() && hasFocus() ? "_" : ""), x + 3, y +height/2);
	}

	private void normalize(){
		if(value != null){
			value = M.min(max, M.max(min, value));
			value = value - value % step;		
		}
	}

	protected void onDefocus(){
		try{		
			if(stringValue.length() > 0){
				value = Float.parseFloat(stringValue);
				normalize();	
			}else{
				value = min;
			}

			updateStringValue();
		}catch(Exception e){}
	}

	private void updateStringValue(){
		// if integer step : no point
		if(step % 1 == 0){
			stringValue = Integer.toString(Math.round(value));
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

	public void setValue(float v){
		value = v;
		normalize();
		updateStringValue();
	}
	public void setValue(int v){
		value = (float)v;
		normalize();
		updateStringValue();
	}
	public void setStringValue(String s){
		stringValue = s;
	}

}