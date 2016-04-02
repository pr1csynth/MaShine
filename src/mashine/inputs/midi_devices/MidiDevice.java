/**
 *  Mother class for all midi device definitions
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.inputs.midi_devices;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class MidiDevice{
	protected Map<Integer,String> PAD;
	protected Map<Integer,String> RANGE;
	protected Map<Integer,String> ENCODER;

	protected String deviceName;

	public MidiDevice(){
		deviceName = "";
		PAD = new HashMap();
		RANGE = new HashMap();
		ENCODER = new HashMap();
	}

	public String getDeviceName(){
		return deviceName;
	}

	public String getInputName(int command, int keyNumber,int value){
		if(PAD.containsKey(keyNumber)){
			return PAD.get(keyNumber);
		}else if(RANGE.containsKey(keyNumber)){
			return RANGE.get(keyNumber);
		}else if(ENCODER.containsKey(keyNumber)){
			return ENCODER.get(keyNumber);
		}else{
			return Integer.toString(keyNumber);
		}
	}

	// returns true for on, false for off, null unchanged/unapplicable
	// state inputs are reset
	public Boolean getState(int command, int keyNumber, int value){
		if (command == 0x80)
			return false;
		else if (command == 0x90)
			return true;
 		else if (command == 0xB0)
			if(PAD.containsKey(keyNumber) || ENCODER.containsKey(keyNumber))
				return value > 64;
			else
				return null;
		else
			return null;
	}

	// returns an int between 0 and 127, null if unchanged/unapplicable
	public Double getRange(int command, int keyNumber, int value){
		if(PAD.containsKey(keyNumber) || ENCODER.containsKey(keyNumber))
			return null;
		else if(command == 0xB0)
			return (double) value;
		else
			return null;
	}
}