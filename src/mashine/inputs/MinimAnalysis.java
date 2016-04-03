/**
 *  Handle Minim sound analysis inputs
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs;

import java.util.ArrayList;

import mashine.MaShine;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;

public class MinimAnalysis extends InputSource{

	private final int MINIM_BD_SENSITIVITY = 120;
	private final int MEAN_BEAT_SAMPLE = 4;
	private final double MINUTE = 60000.0;

	private Minim minim;
	private BeatDetect beatSE;
	private BeatDetect beatFE;
	private AudioInput in;
	private FFT fft;

	private long lastBeat = 0;
	private int lastBeatDistance = 0;
	private int meanBeatDistance = 0;
	private long lastInterpolatedBeat = 0;
	private long lastInterpolatedMeanBeat = 0;
	private ArrayList<Integer> beatDistances;
	private boolean init = false;

	public MinimAnalysis () {

		super();

		minim = new Minim(MaShine.m);
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

		if(!init){
			MaShine.inputs.registerState("minim.beat");
			MaShine.inputs.state("minim.beat", "minim.onset");
			init = true;
		}

		// audio analysis
		beatSE.detect(in.mix);	
		beatFE.detect(in.mix);
		fft.forward(in.mix);
		double rms = in.mix.level();
		states.put("minim.onset" , beatSE.isOnset());
		states.put("minim.kick" , beatFE.isKick());
		states.put("minim.snare", beatFE.isSnare());
		states.put("minim.hat"  , beatFE.isHat());
		ranges.put("minim.rms"  , rms);
		states.put("internal.minim.status" , rms != 0.0);

		// interpolated beat detect
		boolean isBeat = false;
		long now = MaShine.m.millis();

		// get user selected beat input (key, pad, ext source, minim beatdetect, ...)
		String beatInputName = MaShine.inputs.getStateLinks().get("minim.beat");

		if(states.containsKey(beatInputName)){
			// instant
			isBeat = states.get(beatInputName);
		}else{
			// 16.6ms in one frame, 16.6ms of delay...
			isBeat = MaShine.inputs.getState(beatInputName);
		}

		states.put("minim.beat", isBeat);

		// last measured beat update
		if(isBeat){
			lastBeatDistance = (int) (now - lastBeat);
			beatDistances.add(lastBeatDistance);
			lastBeat = now;
			lastInterpolatedBeat = now;
			lastInterpolatedMeanBeat = now;
			ranges.put("minim.bpm.last", ((double)MINUTE/lastBeatDistance));

			if(beatDistances.size() > MEAN_BEAT_SAMPLE)
				beatDistances.remove(0);

			ranges.put("minim.bpm.samplesize", (double)beatDistances.size());

			meanBeatDistance = 0;

			for(long bd : beatDistances){
				meanBeatDistance += bd;
			}

			meanBeatDistance /= beatDistances.size();


			ranges.put("minim.bpm.mean", ((double)MINUTE/meanBeatDistance));
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