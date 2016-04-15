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
			MaShine.println(rates.get(i)+"");
			str.add(rates.get(i)+"");
			next.add(0L);
		}
		
	}

	public void tick(){
		long now = MaShine.m.millis();
		int i = 0;
		for(Long n : next){
			if(n <= now){
				long rate = rates.get(i);
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