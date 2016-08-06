package mashine.ui;

import java.util.List;
import java.util.ArrayList;

import processing.core.PVector;
import mashine.engine.*;

public class Path{

	private String hash;
	private Block inBlock, outBlock;
	private InNode inNode;
	private OutNode outNode;
	private List<PVector> path;
	
	public Path(InNode inNode, OutNode outNode){
		path = new ArrayList<PVector>();
	}

	public List<PVector> get(){
		return path;
	}
}