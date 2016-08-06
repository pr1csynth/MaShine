package mashine.engine;

public class Node<T>{

	protected T value;
	
	public Node(T value){
		this.value = value;
	}

	public T get(){
		return value;
	}

	public String getClassName(){
		if(value instanceof Number){
			return "Number";
		}
		return value.getClass().getSimpleName();
	}

	public void set(T value){
		this.value = value;
	}
}