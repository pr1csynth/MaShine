package mashine.engine;

import java.util.Map;
import java.util.LinkedHashMap;

public class Block{
	private int x, y, w, h;
	protected Map<String,InNode> contentIn, controlIn;
	protected Map<String,OutNode> contentOut, controlOut;

	public Block(){
		this(0,0);
	}
	public Block(int x,int y){
		this(0,0, 1, 1);
	}
	
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

	public void set(String node, Object value){
		if(contentOut.containsKey(node)){
			contentOut.get(node).set(value);
		}else if(controlOut.containsKey(node)){
			controlOut.get(node).set(value);
		}
	}

	public Object get(String node){
		if(contentIn.containsKey(node)){
			return contentIn.get(node).get();
		}else if(controlIn.containsKey(node)){
			return controlIn.get(node).get();
		}
		return null;
	}

	public Float getFloat(String node){
		return ((Number)get(node)).floatValue();
	}

	public int x(){return x;}
	public int y(){return y;}
	public int w(){return Math.max(w, 2 +Math.max(controlIn.size(), controlOut.size()));}
	public int h(){return Math.max(h, 2 +Math.max(contentIn.size(), contentOut.size()));}

	public int minWidth(){return 1;}
	public int minHeight(){return 1;}

	public void tick(){}

	public int leftNodeCount(){return contentIn.size();}
	public int rightNodeCount(){return contentOut.size();}
	public int topNodeCount(){return controlIn.size();}
	public int bottomNodeCount(){return controlOut.size();}

	public Map<String,InNode> getContentInNodes(){return contentIn;}
	public Map<String,OutNode> getContentOutNodes(){return contentOut;}
	public Map<String,InNode> getControlInNodes(){return controlIn;}
	public Map<String,OutNode> getControlOutNodes(){return controlOut;}

}