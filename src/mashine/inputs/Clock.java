/**
 *  Clock input
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import java.util.ArrayList;
import mashine.MaShine;

public class Clock extends InputSource {

	private ArrayList<Long> rates;
	private ArrayList<String> str;
	private ArrayList<Long> next;

	private static final long MINUTE = 60000L;
	private static final long SECOND = 1000L;

	private boolean init = false;

	public Clock () {
		super();
		next = new ArrayList<Long>();
		str = new ArrayList<String>();
		rates = new ArrayList<Long>();

		rates.add(MINUTE/120);
		rates.add(SECOND/35);
		rates.add(SECOND/25);
		rates.add(SECOND/10);
		rates.add(SECOND/1);

		for(int i = 0; i < rates.size(); i ++){
			str.add(rates.get(i)+"");
			next.add(0L);
		}

		str.add("variable");
		next.add(0L);
		
	}

	public void tick(){
		if(!init){ init = true; MaShine.inputs.registerRange("clock.variable.adjust"); }
		long now = MaShine.m.millis();
		int i = 0;
		for(Long n : next){
			if(n <= now){
				long rate;
				if(str.get(i).equals("variable")){
					rate = Math.round(SECOND * MaShine.inputs.getRange("clock.variable.adjust"));
					//MaShine.println(rate);
				}else{
					rate = rates.get(i);
				}
				states.put("clock."+str.get(i), true);
				next.set(i, now+rate);
			}
			i++;
		}
	}

	public void clear(){
		for(String key : states.keySet()){
			states.put(key, false);
		}
	}
}