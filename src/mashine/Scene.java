/**
 *  Collection of devices
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

import mashine.scene.Device;
import mashine.scene.DeviceGroup;
import mashine.scene.Frame;
import mashine.scene.features.FixedField;
import mashine.scene.features.RGB;
import mashine.scene.features.RGBW;
import mashine.scene.features.SingleField;

public class Scene{

	private ArrayList<Device> devices;     // for the frames
	private ArrayList<DeviceGroup> groups; // for the filters
	private Frame frameZero;

	public static HashMap<String,Class<?>> FEATURES;

	static{
		FEATURES = new HashMap<String, Class<?>>();
		FEATURES.put(RGBW.class.getName(), RGBW.class);
		FEATURES.put(RGB.class.getName(), RGB.class);
	}

	public Scene(){

		groups = new ArrayList<DeviceGroup>();

		devices = new ArrayList<Device>();
		Device testDevice = new Device("RGB", 1, 30, 10, 10, 200, 50);
		Device testDevice2 = new Device("RGBW", 5, 30, 10, 65, 200, 50);
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
	
	public static class SaveObject implements Serializable{
		public ArrayList<Device> devices;
		public ArrayList<DeviceGroup> groups;

		public SaveObject(
			ArrayList<Device> devices,
			ArrayList<DeviceGroup> groups
		){
			this.devices = devices;
			this.groups = groups;
		}
	}

	public Object save(){
		return new SaveObject(devices, groups);
	}

	public void restore(Object restoredObject){
		SaveObject s = (SaveObject) restoredObject;
		devices = s.devices;
		groups = s.groups;
		MaShine.ui.reloadElements();
	}
}