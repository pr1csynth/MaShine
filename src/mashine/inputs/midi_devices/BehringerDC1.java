/**
 *  Instance for handling Behringer CMD DC-1
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs.midi_devices;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class BehringerDC1 extends MidiDevice{

	public BehringerDC1(){
		super();

		deviceName = "DC1";

		Map<Integer, String> pad = new HashMap();
		pad.put(32, "bigbutton.pad");

		pad.put(0, "top.1");
		pad.put(1, "top.2");
		pad.put(2, "top.3");
		pad.put(3, "top.4");
		pad.put(4, "top.5");
		pad.put(5, "top.6");
		pad.put(6, "top.7");
		pad.put(7, "top.8");

		pad.put(0+16, "small.1");
		pad.put(1+16, "small.2");
		pad.put(2+16, "small.3");
		pad.put(3+16, "small.4");
		pad.put(4+16, "small.5");
		pad.put(5+16, "small.6");
		pad.put(6+16, "small.7");
		pad.put(7+16, "small.8");

		pad.put(36, "pad.01");
		pad.put(37, "pad.02");
		pad.put(38, "pad.03");
		pad.put(39, "pad.04");
		pad.put(40, "pad.05");
		pad.put(41, "pad.06");
		pad.put(42, "pad.07");
		pad.put(43, "pad.08");
		pad.put(44, "pad.09");
		pad.put(45, "pad.10");
		pad.put(46, "pad.11");
		pad.put(47, "pad.12");
		pad.put(48, "pad.13");
		pad.put(49, "pad.14");
		pad.put(50, "pad.15");
		pad.put(51, "pad.16");
		PAD = Collections.unmodifiableMap(pad);

		Map<Integer, String> encoder = new HashMap();
		encoder.put(32, "bigbutton.encoder");

		encoder.put(16+0, "encoder.1");
		encoder.put(16+1, "encoder.2");
		encoder.put(16+2, "encoder.3");
		encoder.put(16+3, "encoder.4");
		encoder.put(16+4, "encoder.5");
		encoder.put(16+5, "encoder.6");
		encoder.put(16+6, "encoder.7");
		encoder.put(16+7, "encoder.8");
		ENCODER = Collections.unmodifiableMap(encoder);
	}

	public String getInputName(int command, int keyNumber,int value){
		if((command == 0x80 || command == 0x90) && PAD.containsKey(keyNumber)){
			return PAD.get(keyNumber);
		}else if(command == 0xB0 && ENCODER.containsKey(keyNumber)){
			return ENCODER.get(keyNumber);
		}else{
			return super.getInputName(command, keyNumber, value);
		}
	}
}