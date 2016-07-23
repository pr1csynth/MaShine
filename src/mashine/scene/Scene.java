/**
 *  Collection of devices
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

import mashine.scene.features.*;

public class Scene{

	private ArrayList<Device> devices;     // for the frames
	private ArrayList<DeviceGroup> groups; // for the filters
	private Frame frameZero;

	public static HashMap<String,Class<?>> FEATURES;

	static{
		FEATURES = new HashMap<String, Class<?>>();
		FEATURES.put(Tradi.class.getName(), Tradi.class);
		FEATURES.put(RGBW.class.getName(), RGBW.class);
		FEATURES.put(RGB.class.getName(), RGB.class);
		FEATURES.put(Coords.class.getName(), Coords.class);
		FEATURES.put(Zoom.class.getName(), Zoom.class);
	}

	public Scene(){

		groups = new ArrayList<DeviceGroup>();

		devices = new ArrayList<Device>();
		Device testDevice = new Device("RGB", 1, 0, 10, 10, 200, 50);
		Device testDevice2 = new Device("RGBW", 5, 0, 10, 65, 200, 50);
		testDevice.addFeature(new RGB(255));
		testDevice.addFeature(new SingleField("dimmer", 255));
		testDevice.addFeature(new FixedField("strobe", 255));
		testDevice2.addFeature(new RGBW(255));
		testDevice2.addFeature(new SingleField("dimmer", 255));
		testDevice2.addFeature(new FixedField("strobe", 255));
		devices.add(testDevice);
		devices.add(testDevice2);
		
		frameZero = new Frame(devices);
	}

	public ArrayList<Device> getDevices(){
		return new ArrayList<Device>(devices);
	}
	public void addDevice(Device d){
		devices.add(d);
	}

	public void removeDevice(Device d){
		devices.remove(d);
	}

	public Frame getDefaultFrame(){
		return new Frame(frameZero);
	}

	public void renameDevice(Device device, String newName){
		device.setName(newName);
	}

	public void addGroup(DeviceGroup grp){groups.add(grp);}
	public void deleteGroup(DeviceGroup grp){groups.remove(grp);}
	public ArrayList<DeviceGroup> getGroups(){return new ArrayList<DeviceGroup>(groups);}
	public DeviceGroup getGroup(int index){return groups.get(index);}
	public int getGroupsSize(){return groups.size();}
}