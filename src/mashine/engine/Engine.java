/**
 *  Instance for manipulating tracks, mixer and filters to calculate an ImageFrame/Frame
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.engine;

import java.util.ArrayList;
import java.util.List;

import mashine.engine.blocks.*;
import mashine.MaShine;

public class Engine extends Thread{

	private List<Block> blocks;
	private float tickRate = 10f;
	private long  tickRatePeriod = 1000000000L / 42L; // target is 42 tps


	public Engine(){
		blocks = new ArrayList<Block>();

		//blocks.add(new Ola());
		blocks.add(new Sequencer());

		this.start();
	}

	public void run(){

		long tickRateLastNanos = 0L;
      	long overSleepTime = 0L;

		while(true){
			long now = System.nanoTime();
			double rate = 1000000.0 / ((now - tickRateLastNanos) / 1000000.0);
			float instantaneousRate = (float) (rate / 1000.0);
			tickRate = (tickRate * 0.9f) + (instantaneousRate * 0.1f);
			tickRateLastNanos = now;
			tick();

			long afterTime = System.nanoTime();
			long timeDiff = afterTime - now;
			long sleepTime = (tickRatePeriod - timeDiff) - overSleepTime;

			if (sleepTime > 0){  // some time left in this cycle
				try{
					Thread.sleep(sleepTime / 1000000L, (int) (sleepTime % 1000000L));
				} catch (InterruptedException ex){}

				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}else{
				overSleepTime = 0;
			}
		}
	}

	public void tick(){
		for(Block block : blocks){
			block.tick();
		}
	}

	public float tickRate(){
		return tickRate;
	}

	public void tickRate(float tps) {
		tickRatePeriod = (long) (1000000000.0 / tps);
	}

	public List<Block> getBlocks(){return blocks;}

	public static String hex(int n) {
		return String.format("%2s", Integer.toHexString(n)).replace(' ', '0');
	}
}