package mashine.engine;

import java.util.Map;
import java.util.LinkedHashMap;

public class Block{

	private int x, y, w, h;
	private Map<String,InNode> contentIn, controlIn;
	private Map<String,OutNode> contentOut, controlOut;
	
	public Block(int x,int y,int w,int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		contentIn 	= new LinkedHashMap<String,InNode>();
		contentOut 	= new LinkedHashMap<String,OutNode>();
		controlIn 	= new LinkedHashMap<String,InNode>();
		controlOut 	= new LinkedHashMap<String,OutNode>();
	}

	public int x(){return x;}
	public int y(){return y;}
	public int w(){return w;}
	public int h(){return h;}

	public int leftNodeCount(){return contentIn.size();}
	public int rightNodeCount(){return contentOut.size();}
	public int topNodeCount(){return controlIn.size();}
	public int bottomNodeCount(){return controlOut.size();}

	public Map<String,InNode> getContentInNodes(){return contentIn;}
	public Map<String,OutNode> getContentOutNodes(){return contentOut;}
	public Map<String,InNode> getControlInNodes(){return controlIn;}
	public Map<String,OutNode> getControlOutNodes(){return controlOut;}

	public void addContentInNodes(){}
	public void addContentOutNodes(){}
	public void addControlInNodes(){}
	public void addControlOutNodes(){}
}