/**
 *  Instance for handling the Korg NanoKontrol2
 *
 *  @author procsynth - Antoine Pintout
 *  @since  13-02-2016`
 */

package mashine.inputs.midi_devices;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KorgNanoKontrol2 extends MidiDevice{

    public KorgNanoKontrol2(){
    	super();

    	deviceName = "nanoKONTROL2";

        Map<Integer, String> pad = new HashMap<Integer, String>();
        pad.put(58, "track.previous");
        pad.put(59, "track.next");
        pad.put(46, "cycle");
        pad.put(60, "marker.set");
        pad.put(61, "marker.previous");
        pad.put(62, "marker.next");
        pad.put(43, "back");
        pad.put(44, "forward");
        pad.put(42, "stop");
        pad.put(41, "play");
        pad.put(45, "record");
        pad.put(32+0, "track1.solo");
        pad.put(48+0, "track1.mute");
        pad.put(64+0, "track1.rec");
        pad.put(32+1, "track2.solo");
        pad.put(48+1, "track2.mute");
        pad.put(64+1, "track2.rec");
        pad.put(32+2, "track3.solo");
        pad.put(48+2, "track3.mute");
        pad.put(64+2, "track3.rec");
        pad.put(32+3, "track4.solo");
        pad.put(48+3, "track4.mute");
        pad.put(64+3, "track4.rec");
        pad.put(32+4, "track5.solo");
        pad.put(48+4, "track5.mute");
        pad.put(64+4, "track5.rec");
        pad.put(32+5, "track6.solo");
        pad.put(48+5, "track6.mute");
        pad.put(64+5, "track6.rec");
        pad.put(32+6, "track7.solo");
        pad.put(48+6, "track7.mute");
        pad.put(64+6, "track7.rec");
        pad.put(32+7, "track8.solo");
        pad.put(48+7, "track8.mute");
        pad.put(64+7, "track8.rec");
        PAD = Collections.unmodifiableMap(pad);

        Map<Integer, String> range = new HashMap<Integer, String>();
      	range.put(16+0, "track1.knob");
      	range.put(0 +0, "track1.slider");
      	range.put(16+1, "track2.knob");
      	range.put(0 +1, "track2.slider");
      	range.put(16+2, "track3.knob");
      	range.put(0 +2, "track3.slider");
      	range.put(16+3, "track4.knob");
      	range.put(0 +3, "track4.slider");
      	range.put(16+4, "track5.knob");
      	range.put(0 +4, "track5.slider");
      	range.put(16+5, "track6.knob");
      	range.put(0 +5, "track6.slider");
      	range.put(16+6, "track7.knob");
      	range.put(0 +6, "track7.slider");
      	range.put(16+7, "track8.knob");
      	range.put(0 +7, "track8.slider");
        RANGE = Collections.unmodifiableMap(range);
    }
}