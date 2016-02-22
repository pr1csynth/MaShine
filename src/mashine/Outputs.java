/**
 *  Mother class for all outputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */
 
 package mashine;

 import java.util.HashMap;
 import mashine.outputs.*;

 public class Outputs{

 	public MaShine M;

 	private HashMap<String,Output> outputs;

 	public Outputs(MaShine m){
 		M = m;

 		outputs = new HashMap<String,Output>();

 		outputs.put("OLA", new Ola(M));
 	}

 	public void push(){
 		for(String o : outputs.keySet()){
 			outputs.get(o).push();
 		}
 	}

 }