/**
 *  Class for defining and manipulating screenable colors
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import java.awt.Color;
import processing.core.PGraphics;

public class FlatColor {

	protected int r;
	protected int g;
	protected int b;
	protected int a;
	protected float h;
	protected float s;
	protected float v;

	public static void fill(PGraphics g, FlatColor c) {
		g.fill(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public static void stroke(PGraphics g, FlatColor c) {
		g.stroke(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public static void background(PGraphics g, FlatColor c) {
		g.background(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}


	// No arg : black
	public FlatColor () {
		this(0);
	}

	// One arg : gray
	public FlatColor (int gray){
		this(gray, gray, gray);
	}

	// Three arg : RGB
	public FlatColor(int red, int green, int blue){
		this(red, green, blue, 255);
	}

	public FlatColor(Color color) {
		this(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
	}

	public FlatColor(FlatColor color) {
		this(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
	}

	// Four arg : RGBA
	public FlatColor(int red, int green, int blue, int alpha){
		r = red;
		g = green;
		b = blue;
		a = alpha;
		updateHSB();
	}

	private void updateHSB(){
		float[] hsb = Color.RGBtoHSB(r, g, b, null);
		h = hsb[0];
		s = hsb[1];
		v = hsb[2];
	}

	private void updateRGB(){
		Color c = new Color(Color.HSBtoRGB(h, s, v));
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
	}

	public void setRed(int red){
		r = red;
		updateHSB();
	}
	public void setGreen(int green){
		g = green;
		updateHSB();
	}
	public void setBlue(int blue){
		b = blue;
		updateHSB();
	}
	public void setAlpha(int alpha){
		a = alpha;
		updateHSB();
	}

	public void rotateHue(float angle) {
		h += angle;
		updateRGB();
	}

	public void setHue(float hue){
		h = hue;
		updateRGB();
	}
	public void setSaturation(float saturation){
		s = saturation;
		updateRGB();
	}
	public void setBrightness(float brightness){
		v = brightness;
		updateRGB();
	}



	public FlatColor withRed(int red){
		FlatColor c = new FlatColor(this);
		c.setRed(red);
		return c;
	}
	public FlatColor withGreen(int green){
		FlatColor c = new FlatColor(this);
		c.setGreen(green);
		return c;
	}
	public FlatColor withBlue(int blue){
		FlatColor c = new FlatColor(this);
		c.setBlue(blue);
		return c;
	}
	public FlatColor withAlpha(int alpha){
		FlatColor c = new FlatColor(this);
		c.setAlpha(alpha);
		return c;
	}

	public FlatColor withRotatedHue(float angle) {
		FlatColor c = new FlatColor(this);
		c.rotateHue(angle);
		return c;
	}

	public FlatColor withHue(float hue){
		FlatColor c = new FlatColor(this);
		c.setHue(hue);
		return c;
	}
	public FlatColor withSaturation(float saturation){
		FlatColor c = new FlatColor(this);
		c.setSaturation(saturation);
		return c;
	}
	public FlatColor withBrightness(float brightness){
		FlatColor c = new FlatColor(this);
		c.setBrightness(brightness);
		return c;
	}

	public FlatColor withAlphaAsWhite(){
		FlatColor c = new FlatColor(this);
		c.setRed(getRed() + getAlpha()/2);
		c.setGreen(getGreen() + getAlpha()/2);
		c.setBlue(getBlue() + getAlpha()/2);
		c.setAlpha(255);
		return c;
	}

	public int getRed(){
		return r;
	}
	public int getGreen(){
		return g;
	}
	public int getBlue(){
		return b;
	}
	public int getAlpha(){
		return a;
	}
	public float getHue(){
		return h;
	}
	public float getSaturation(){
		return s;
	}
	public float getBrightness(){
		return v;
	}


}