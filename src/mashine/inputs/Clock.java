/**
 *  Clock input
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import java.util.ArrayList;

import mashine.MaShine;
import mashine.engine.*;

public class Clock extends Block {

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
			str.add(rates.get(i)+"");
			next.add(0L);
		}

		str.add("variable");
		next.add(0L);

		nodes();
		
	}

	private void nodes(){
		/* controls in 	//	controls out
		- sync				- [fixed_rates]
		- adjust 			- variable_rate

		/* content in 	//	content out
		
		*/

		controlIn.put("adjust", new InNode(1.0));
		controlIn.put("sync", new InNode(false));

		for(String s : str){
			controlOut.put(s, new OutNode(this, false));
		}
	}

	public void tick(){
		long now = MaShine.m.millis();
		int i = 0;
		for(Long n : next){
			if(n <= now){
				long rate;
				if(str.get(i).equals("variable")){
					rate = Math.round(SECOND * (Double)get("adjust"));
					//MaShine.println(rate);
				}else{
					rate = rates.get(i);
				}
				set(str.get(i), true);
				next.set(i, now+rate);
			}else{
				set(str.get(i), false);
			}

			i++;
		}
	}
}