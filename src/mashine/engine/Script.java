package mashine.engine;

import javax.script.ScriptEngine;
import javax.script.Invocable;

public class Script{

	private ScriptEngine nashorn;
	private Object parent;

	public Script(ScriptEngine nashorn, String preprocessor, String script, Object parent){
		this.parent = parent;
		reload(nashorn, preprocessor, script);
	}

	public Invocable invocable(){
		return (Invocable) nashorn;
	}
	public ScriptEngine engine(){
		return nashorn;
	}
	public void reload(ScriptEngine nashorn, String preprocessor, String script){
		try{	
			this.nashorn = nashorn;
			nashorn.put("parent", parent);
			nashorn.eval(preprocessor);
			nashorn.eval(script);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}