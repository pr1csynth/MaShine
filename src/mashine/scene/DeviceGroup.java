/**
 *  Instance of grouped devices with ordering
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.scene;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.lang.Comparable;
import java.util.Comparator;
import java.util.stream.Stream;

public class DeviceGroup implements Serializable{

	private static final long serialVersionUID = 0xDED00001L;

	private LinkedHashMap<Device,Integer> devices;
	private String name;

	public DeviceGroup(String name){
		this.name = name;
		devices = new LinkedHashMap<Device, Integer>();
	}

	public DeviceGroup(String name, DeviceGroup grp){
		this.name = name;
		this.devices = new LinkedHashMap<Device, Integer>(grp.getDevices());
	}

	public DeviceGroup(String name, List<Device> devices){
		this(name);

		for(Device d : devices){
			this.devices.put(d, d.getStartAddress());
		}
	}

	public LinkedHashMap<Device,Integer> getDevices() {
		return devices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void putDevice(Device d){
		this.devices.put(d, d.getStartAddress());
	}
	public void removeDevice(Device d){
		this.devices.remove(d);
	}

	public boolean isIn(Device d){
		return devices.containsKey(d);
	}
	public boolean isEmpty(){
		return devices.isEmpty();
	}

	public Device getFirst(){
		if(isEmpty()){
			return null;
		}
		return (Device) devices.keySet().toArray()[0];
	}

	public Integer getWeight(Device d){
		return devices.get(d);
	}

	public Integer setWeight(Device d, Integer w){
		return devices.put(d, w);
	}

	public void reverseWeights(){
		int min = -1; 
		int max = 0;
		for(int v : devices.values()){
			if(min == -1 || v < min) min = v;
			if(v > max) max = v;
		}
		int range = Math.max(1, (max - min) + 1 );
		for(Device d : devices.keySet()){
			devices.put(d, min + range - devices.get(d));
		}
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ){

		Map<K,V> result = new LinkedHashMap<>();
		Stream <Entry<K,V>> st = map.entrySet().stream();

		st.sorted(Comparator.comparing(e -> e.getValue()))
		.forEachOrdered(e ->result.put(e.getKey(),e.getValue()));

		return result;
	}

}