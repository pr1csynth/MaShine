/**
 *  Instance of grouped devices with ordering
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import mashine.*;
import mashine.scene.features.*;
import java.util.HashMap;

public class DeviceGroup{

	private HashMap<Device,Integer> devices;
	private String identifier;

	public DeviceGroup(String identifier){
		this.identifier = identifier;
		devices = new HashMap<Device,Integer>();
	}
}