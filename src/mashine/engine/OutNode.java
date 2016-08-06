package mashine.engine;

public class OutNode<T> extends Node<T>{

	private Block parent;
	
	public OutNode(Block parent, T value){
		super(value);
		this.parent = parent;
	}
}