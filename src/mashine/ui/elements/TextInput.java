package mashine.ui.elements;

import mashine.*;
import mashine.ui.*;
import java.util.HashMap;

public class TextInput extends Element{

	private String value;

	public TextInput(Drawable parent, String defaultValue, int x, int y, int width){
		super(parent, x, y, width, 15);
		value = defaultValue;
	}

	public void drawContent(){
		P.canvas.noStroke();


		if(hasFocus() && P.hasFocus()){
			FlatColor.stroke(P.canvas, Colors.MATERIAL.ORANGE.A700);
			if(M.inputs.getState("keyboard.8.press")){
				if(value.length() > 0)
					value = value.substring(0, value.length() - 1);
			}else if(M.inputs.getState("keyboard.9.press") || M.inputs.getState("keyboard.10.press")){
				focus = false;
			}else if(M.inputs.getState("keyboard.147.press")){
				value = "";
			}else if(M.inputs.getLastKey() != ""){
				value += M.inputs.getLastKey();
			}
		}

		// UIREFORM
		FlatColor.fill(P.canvas, Colors.WHITE);
		P.canvas.rect(x, y, width, height);
		FlatColor.fill(P.canvas, Colors.MATERIAL.BLUE_GREY._900);
		P.canvas.textAlign(P.canvas.LEFT, P.canvas.CENTER);
		P.canvas.text(value + (M.millis() % 1200 > 600 && hasFocus() ? "_" : ""), x + 3, y +height/2);
	}

}