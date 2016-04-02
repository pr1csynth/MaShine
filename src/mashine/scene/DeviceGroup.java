/**
 *  Instance of grouped devices with ordering
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import java.io.Serializable;
import java.util.HashMap;

public class DeviceGroup implements Serializable{

	private static final long serialVersionUID = 0xDED00001L;

	private HashMap<Device,Integer> devices;
	private String identifier;

	public DeviceGroup(String identifier){
		this.identifier = identifier;
		setDevices(new HashMap<Device,Integer>());
	}

	public HashMap<Device,Integer> getDevices() {
		return devices;
	}

	public void setDevices(HashMap<Device,Integer> devices) {
		this.devices = devices;
	}

	public String getIdentifier() {
		return identifier;
	}

}