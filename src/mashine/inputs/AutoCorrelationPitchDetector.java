/* 
 * Pitch dectection by Autocorrelation
 *
 * Freely adapted from the works of
 * L. Anton-Canalis (info@luisanton.es) 
 * https://github.com/Notnasiul/R2D2-Processing-Pitch
 */

package mashine.inputs;

import java.util.Arrays;

import ddf.minim.AudioBuffer;

public class AutoCorrelationPitchDetector { 

	private float sampleRate;
	private double lastPeriod;
	private double currentFrequency;
	private long  t;
	
	private final double FMIN = 20;
	private final double FMAX = 440;

	private int minShift;
	private int maxShift;

	private double []freqBuffer = new double[10];
	private double []sortedFreqBuffer = new double[10];
	private int freqBufferIndex = 0;
	
	
	AutoCorrelationPitchDetector (float sampleRate) {
		this.sampleRate = sampleRate;
		double tmin = 1.0 / FMAX;
		double tmax = 1.0 / FMIN;
		minShift = (int)Math.round(tmin * sampleRate ); 
		maxShift = (int)Math.round(tmax * sampleRate );
		lastPeriod = maxShift;
		t = 0;
	}
	
	private void setFrequency(double f) {
		if(f < FMAX){
			freqBuffer[freqBufferIndex] = f;
			freqBufferIndex++;
			freqBufferIndex = freqBufferIndex % freqBuffer.length;
			sortedFreqBuffer = freqBuffer.clone();
			Arrays.sort(sortedFreqBuffer);
			currentFrequency = sortedFreqBuffer[5];
			currentFrequency = f;
		}
	}
	
	public double getFrequency() {
		return sortedFreqBuffer[7];
	}
	public double[] getFrequencies() {
		return sortedFreqBuffer;
	}

	void forward(AudioBuffer buffer) {
		float[] audio = buffer.toArray();

		t++;
		int buffer_index = 0;

		double max_sum = 0;   
		int period = 0;
		for (int shift = minShift; shift < maxShift; shift++)
		{  
			// Assigh higher weights to lower frequencies
			// and even higher to periods that are closer to the last period (quick temporal coherence hack)
			double mod = (shift - minShift) / (maxShift - minShift);
			mod *= 1.0 - 1.0 / (1.0 + Math.abs(shift - lastPeriod));

			// Compare samples with shifted samples using autocorrelation
			double dif = 0;
			for (int i = shift; i < audio.length; i++)
				dif += audio[i] * audio[i - shift];		

			// Apply weight
			dif *= 1.0 + mod;
			
			if (dif > max_sum)
			{
				max_sum = dif;			 
				period = shift;
			}
		}	

		if (period != 0){
			lastPeriod = period;
			double freq = 1.0f / period;
			freq *= sampleRate;			  
			setFrequency(freq);
			buffer_index += period + minShift;		  
		}
		else {
			lastPeriod = (maxShift + minShift) / 2;
			setFrequency(0);
			buffer_index += minShift;
		}
	}
};
