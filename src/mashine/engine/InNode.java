package mashine.engine;

public class InNode<T>{

	private T value;
	private OutNode<T> up;
	
	public InNode(T value){
		this.value = value;
	}

	public T get(){
		if(up == null){
			return value;
		}
		return up.get();
	}

	public void setDefault(T value){
		this.value = value;
	}

	public void link(OutNode<T> parent){
		this.up = up;
	}
	public void unlink(){
		this.up = null;
	}
}