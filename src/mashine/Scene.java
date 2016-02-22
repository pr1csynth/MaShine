/**
 *  Collection of devices
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import mashine.scene.Frame;
import mashine.scene.*;
import mashine.scene.features.*;
import java.util.HashMap;

public class Scene{

	private MaShine M;
	private HashMap<String,Device> devices;     // for the frames
	private HashMap<String,DeviceGroup> groups; // for the filters
	private Frame frameZero;

	public Scene(MaShine m){

		M = m;

		devices = new HashMap<String,Device>();
		Device testDevice = new Device("testDevice", 1, 42, 10, 10, 200, 50);
		Device testDevice2 = new Device("testDevice2", 6, 42, 10, 65, 200, 60);
		testDevice.addFeature(new RGBW(255));
		testDevice.addFeature(new FixedField("strobe", 255));
		testDevice2.addFeature(new RGBW(255));
		testDevice2.addFeature(new FixedField("strobe", 255));
		testDevice2.addFeature(new FixedField("dimmer", 255));
		devices.put(testDevice.getIdentifier(), testDevice);
		devices.put(testDevice2.getIdentifier(), testDevice2);
		frameZero = new Frame(devices);
	}

	public HashMap<String,Device> getDevices(){
		return new HashMap<String,Device>(devices);
	}

	public Frame getDefaultFrame(){
		return new Frame(frameZero);
	}
}