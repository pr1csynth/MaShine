package mashine.engine;

public class OutNode<T>{

	private T value;
	private Block parent;
	
	public OutNode(Block parent, T value){
		this.parent = parent;
		this.value = value;
	}

	public T get(){
		return value;
	}

	public void set(T value){
		this.value = value;
	}
}