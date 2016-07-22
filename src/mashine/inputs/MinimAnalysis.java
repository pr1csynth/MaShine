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

	private final int MINIM_BD_SENSITIVITY = 200;
	private final int MEAN_BEAT_SAMPLE = 4;
	private final double MINUTE = 60000.0;

	private Minim minim;
	private BeatDetect beatSE;
	private BeatDetect beatFE;
	private AudioInput in;
	private FFT fft;
	private AutoCorrelationPitchDetector acpd;

	private long lastBeat = 0;
	private int lastBeatDistance = 0;
	private int meanBeatDistance = 0;
	private long lastInterpolatedBeat = 0;
	private long lastInterpolatedMeanBeat = 0;
	private boolean init = false;

	public MinimAnalysis () {

		super();

		minim = new Minim(MaShine.m);
		initMinim();
	}

	private void initMinim(){

		// FFT
		in = minim.getLineIn();
		fft = new FFT(in.bufferSize(), in.sampleRate());
		fft.logAverages( 21, 3 );

		beatSE = new BeatDetect();
		beatFE = new BeatDetect();
		beatSE.setSensitivity(MINIM_BD_SENSITIVITY);
		beatFE.setSensitivity(MINIM_BD_SENSITIVITY);
		beatSE.detectMode(BeatDetect.SOUND_ENERGY);
		beatFE.detectMode(BeatDetect.FREQ_ENERGY);

		// Auto Correlation Pitch Detect
		acpd = new AutoCorrelationPitchDetector(in.sampleRate());

	}

	public void tick() {

		if(!init){
			MaShine.inputs.registerState("minim.beat");
			MaShine.inputs.state("minim.beat", "minim.onset");
			MaShine.inputs.registerState("minim.do_interpolate");
			MaShine.inputs.state("minim.do_interpolate", "_true");
			init = true;
		}

		// audio analysis
		beatSE.detect(in.mix);	
		beatFE.detect(in.mix);
		fft.forward(in.mix);
		acpd.forward(in.mix);

		float maxBandValue = 0;
		int maxBand = 0;

		for(int i = 0; i < fft.avgSize(); i++){	
			float bandValue = fft.getAvg(i);
			ranges.put("minim.fft."+
				String.format("%2s", i).replace(' ', '0'), 
				(double) bandValue/64.0);
			if(bandValue > maxBandValue){
				maxBand = i;
				maxBandValue = bandValue;
			}
		}
		double[] pitchs = acpd.getFrequencies();
		for(int i = 0; i < pitchs.length; i++){
			ranges.put("minim.acpd." +
				String.format("%2s", i).replace(' ', '0'), 
				pitchs[i]);
		}

		ranges.put("minim.pitch", acpd.getFrequency());
		ranges.put("minim.fft.maxband", (double) maxBand);

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
			lastBeat = now;
			lastInterpolatedBeat = now;
			lastInterpolatedMeanBeat = now;
			ranges.put("minim.bpm.last", ((double)MINUTE/lastBeatDistance));
		}

		// interpolated beat
		if(now == lastInterpolatedBeat || MaShine.inputs.getState("minim.do_interpolate") && now > lastInterpolatedBeat + lastBeatDistance){
			lastInterpolatedBeat = now;
			states.put("minim.beat.interpolated", true);
		}else{
			states.put("minim.beat.interpolated", false);
		}
	}

	public FFT getFFT(){
		return fft;
	}


}