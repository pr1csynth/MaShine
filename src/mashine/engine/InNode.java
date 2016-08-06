package mashine.engine;

public class InNode<T> extends Node<T>{

	private OutNode<T> up;
	
	public InNode(T value){
		super(value);
	}

	public T get(){
		if(up == null){
			return value;
		}
		return up.get();
	}

	public void link(OutNode<T> parent){
		this.up = up;
	}
	public void unlink(){
		this.up = null;
	}
}