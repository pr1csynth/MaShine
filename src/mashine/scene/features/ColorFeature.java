package mashine.scene.features;

import mashine.scene.*;
import mashine.ui.*;

public abstract class ColorFeature extends EditableFeature {

	public ColorFeature(String type, int footprint){
		super(type, footprint);
	}

	public ColorFeature(ColorFeature f){
		super(f);
	}

	public ColorFeature(Feature f){
		super(f);
	}

	public abstract FlatColor getColor();
}