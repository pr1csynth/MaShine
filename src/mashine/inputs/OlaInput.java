/**
 *  Handle keyboard inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import mashine.MaShine;
import java.lang.Thread;
import java.lang.Runnable;

public class OlaInput extends InputSource implements Runnable{

	private Thread T;
	private boolean started = false;

	public OlaInput () {
		super();

		T = new Thread(this);
	}

	public void tick() {
		if(!started){
			T.start();
			started = true;
		}else{
			synchronized (this) {
				this.notify();
			}
		}
	}

	public void run() {
		while(true){
			short[] dmxData;
			synchronized (MaShine.outputs.ola) {
				dmxData = MaShine.outputs.ola.getDmx();
			}
			if(dmxData != null){				
				synchronized (ranges) {
					for(int i = 0; i < Math.min(8, dmxData.length); i++){
						ranges.put("ola.in."+(i+1), (double) dmxData[i]/255.0);
					}
				}
			}
			try {
				synchronized (this) {
					this.wait();
				}
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}