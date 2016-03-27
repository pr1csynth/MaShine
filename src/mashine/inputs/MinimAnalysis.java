/**
 *  Handle Minim sound analysis inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import mashine.*;
import ddf.minim.*; 
import ddf.minim.analysis.*;
import java.util.ArrayList;

public class MinimAnalysis extends InputSource{

	private final int MINIM_BD_SENSITIVITY = 120;
	private final int MEAN_BEAT_SAMPLE = 4;
	private final float MINUTE = (float)60000.0;

	private Minim minim;
	private BeatDetect beatSE;
	private BeatDetect beatFE;
	private AudioInput in;
	private FFT fft;

	private String beatInputName = "minim.onset";
	private long lastBeat = 0;
	private int lastBeatDistance = 0;
	private int meanBeatDistance = 0;
	private long lastInterpolatedBeat = 0;
	private long lastInterpolatedMeanBeat = 0;
	private ArrayList<Integer> beatDistances;

	public MinimAnalysis (MaShine m) {

		super(m);

		minim = new Minim(M);
		initMinim();

		beatDistances = new ArrayList<Integer>();
		beatDistances.add(500); // hydrate with a single 120BPM time
	}

	private void initMinim(){

		in = minim.getLineIn();
		fft = new FFT(in.bufferSize(), in.sampleRate());

		beatSE = new BeatDetect();
		beatFE = new BeatDetect();
		beatSE.setSensitivity(MINIM_BD_SENSITIVITY);
		beatFE.setSensitivity(MINIM_BD_SENSITIVITY);
		beatSE.detectMode(BeatDetect.SOUND_ENERGY);
		beatFE.detectMode(BeatDetect.FREQ_ENERGY);

	}

	public void tick() {

		// audio analysis
		beatSE.detect(in.mix);	
		beatFE.detect(in.mix);
		fft.forward(in.mix);
		float rms = in.mix.level();
		states.put("minim.onset" , beatSE.isOnset());
		states.put("minim.kick" , beatFE.isKick());
		states.put("minim.snare", beatFE.isSnare());
		states.put("minim.hat"  , beatFE.isHat());
		ranges.put("minim.rms"  , rms);
		states.put("internal.minim.status" , rms != 0.0);

		// interpolated beat detect
		boolean isBeat = false;
		long now = M.millis();

		// get user selected beat input (key, pad, ext source, minim beatdetect, ...)
		if(states.containsKey(beatInputName)){
			// instant
			isBeat = states.get(beatInputName);
		}else{
			// 16.6ms in one frame, 16.6ms of delay...
			isBeat = M.inputs.getState(beatInputName);
		}

		states.put("minim.beat", isBeat);

		// last measured beat update
		if(isBeat){
			lastBeatDistance = (int) (now - lastBeat);
			beatDistances.add(lastBeatDistance);
			lastBeat = now;
			lastInterpolatedBeat = now;
			lastInterpolatedMeanBeat = now;
			ranges.put("minim.bpm.last", ((float)MINUTE/lastBeatDistance));

			if(beatDistances.size() > MEAN_BEAT_SAMPLE)
				beatDistances.remove(0);

			ranges.put("minim.bpm.samplesize", (float)beatDistances.size());

			meanBeatDistance = 0;

			for(long bd : beatDistances){
				meanBeatDistance += bd;
			}

			meanBeatDistance /= beatDistances.size();


			ranges.put("minim.bpm.mean", ((float)MINUTE/meanBeatDistance));
		}

		// interpolated beat
		if(now == lastInterpolatedBeat || now > lastInterpolatedBeat + lastBeatDistance){
			lastInterpolatedBeat = now;
			states.put("minim.beat.interpolated", true);
		}else{
			states.put("minim.beat.interpolated", false);
		}

		// interpolated mean beat
		if(now == lastInterpolatedMeanBeat || now > lastInterpolatedMeanBeat + meanBeatDistance){
			lastInterpolatedMeanBeat = now;
			states.put("minim.beat.mean", true);
		}else{
			states.put("minim.beat.mean", false);
		}

	}

	public FFT getFFT(){
		return fft;
	}


}