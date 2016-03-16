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
import java.util.List;

public class Scene{

	private MaShine M;
	private HashMap<String,Device> devices;     // for the frames
	private HashMap<String,DeviceGroup> groups; // for the filters
	private Frame frameZero;

	public static HashMap<String,Class<?>> FEATURES;

	static{
		FEATURES = new HashMap<String, Class<?>>();
		FEATURES.put(RGBW.class.getName(), RGBW.class);
		FEATURES.put(RGB.class.getName(), RGB.class);
	}

	public Scene(MaShine m){

		M = m;

		devices = new HashMap<String,Device>();
		Device testDevice = new Device("testDevice", 1, 1, 10, 10, 200, 50);
		Device testDevice2 = new Device("testDevice2", 6, 42, 10, 65, 200, 60);
		testDevice.addFeature(new RGBW(255));
		testDevice.addFeature(new FixedField("strobe", 255));
		testDevice2.addFeature(new RGBW(255));
		testDevice2.addFeature(new FixedField("strobe", 255));
		testDevice2.addFeature(new FixedField("dimmer", 255));
		devices.put(testDevice.getIdentifier(), testDevice);
		devices.put(testDevice2.getIdentifier(), testDevice2);
		Device testDevice3 = new Device("testDevice3", 1, 42, 215, 10, 200, 50);
		Device testDevice4 = new Device("testDevice4", 6, 42, 215, 65, 200, 70);
		testDevice3.addFeature(new RGBW(255));
		testDevice3.addFeature(new FixedField("strobe", 255));
		testDevice4.addFeature(new RGBW(255));
		testDevice4.addFeature(new FixedField("strobe", 255));
		testDevice4.addFeature(new FixedField("dimmer", 255));
		testDevice4.addFeature(new FixedField("kelvin", 255));
		testDevice4.addFeature(new FixedField("test", 255));
		devices.put(testDevice3.getIdentifier(), testDevice3);
		devices.put(testDevice4.getIdentifier(), testDevice4);
		frameZero = new Frame(devices);

		for(String c : Scene.FEATURES.keySet()){
			M.print(c);
			M.print(" ");
		}

		M.println();
	}

	public HashMap<String,Device> getDevices(){
		return new HashMap<String,Device>(devices);
	}

	public Frame getDefaultFrame(){
		return new Frame(frameZero);
	}

	public boolean validateDeviceIdentifier(String identifier){
		return !devices.containsKey(identifier);
	}

	public boolean renameDevice(Device device, String newIdentifier){
		if(devices.containsValue(device) && validateDeviceIdentifier(newIdentifier)){
			devices.remove(device.getIdentifier());
			M.println("-"+device.getIdentifier() +"->"+ newIdentifier +"<");
			M.ui.sceneVisualizer.renameDevice(device.getIdentifier(), newIdentifier);
			device.setIdentifier(newIdentifier);
			devices.put(newIdentifier, device);
			return true;
		}
		return false;
	}
}