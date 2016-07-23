/**
 *  Interface to select and group devices;
 *
 *	@author procsynth - Antoine Pintout
 *	@since  09-04-2016`
 */

package mashine.ui.boxes;

import java.util.ArrayList;
import java.util.Map;
import java.lang.Math;

import mashine.MaShine;
import mashine.ui.Colors;
import mashine.utils.FlatColor;
import mashine.ui.UIBox;
import mashine.ui.elements.TextButton;
import mashine.ui.elements.RangeInput;
import mashine.ui.elements.TextInput;
import mashine.utils.*;
import mashine.scene.Device;
import mashine.scene.DeviceGroup;

import processing.core.PConstants;

public class DeviceSelector extends UIBox{

	private DeviceGroup selectedGroup;
	private Device selectedDevice;

	private RangeInput weightInput;
	private RangeInput univSelectInput;
	private TextInput groupNameInput;

	public DeviceSelector () {
		super("DEVICES", 100, 600, 410, 230);

		weightInput = new RangeInput(this,205, height-15, 40);
		groupNameInput = new TextInput(this,"new group", 0, height-15, 110);

		elements.add(weightInput);
		elements.add(groupNameInput);
		elements.add(new TextButton(this,"new", 112, height-15, 30,
			new Do(){public void x(){cloneGroup();}}
		));
		elements.add(new TextButton(this,"delete", 144, height-15, 50,
			new Do(){public void x(){deleteGroup();}}
		));
		elements.add(new TextButton(this,"remove", 250, height-15, 50,
			new Do(){public void x(){removeDevice();}}
		));
		elements.add(new TextButton(this,"reverse", 305, height-15, 55,
			new Do(){public void x(){reverseWeights();}}
		));

		// SIDE BUTTONS

		elements.add(new TextButton(this,"clear", width-59, 39, 60,
			new Do(){public void x(){selectClear();}}
		));

		elements.add(new TextButton(this,"all", width-59, 56, 60,
			new Do(){public void x(){selectAll();}}
		));

		elements.add(new TextButton(this,"univ.", width-59, 73, 40,
			new Do(){public void x(){selectUniverse();}}
		));

		univSelectInput = new RangeInput(this, 1f, 0f, 99f, 1f, width-19, 73, 20);
		elements.add(univSelectInput);


		elements.add(new TextButton(this,"> set", width-59, height-66, 60,
			new Do(){public void x(){setGroupAsSelection();}}
		));

		elements.add(new TextButton(this,"< add", width-59, height-49, 60,
			new Do(){public void x(){addCurrent();}}
		));
		elements.add(new TextButton(this,"remove", width-59, height-32, 60,
			new Do(){public void x(){removeCurrent();}}
		));


	}

	public void tick(){}

	public void drawFixedUI(){
		canvas.noStroke();
		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(1, height - 16, width -1, 17);
		FlatColor.stroke(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.line(199, 20, 199, height-1);
		canvas.line(width-65, 20, width-65, height-1);
		canvas.noStroke();

		FlatColor.fill(canvas, Colors.WHITE);
		canvas.textAlign(PConstants.RIGHT, PConstants.TOP);
		canvas.text("select°:", width -4, 25);
	}

	public void drawUI(){
		DeviceGroup currentSelection = new DeviceGroup("(selection)", MaShine.ui.getSelectedDevices());
		ArrayList<DeviceGroup> groups = new ArrayList<DeviceGroup>(MaShine.scene.getGroups());
		groups.add(0, currentSelection);

		if(selectedGroup == null || selectedGroup.getName().equals("(selection)")){
			selectedGroup = currentSelection;
			if(!groupNameInput.value().equals("new group")){
				cloneGroup(groupNameInput.value());
			}
		}else{
			if(!groupNameInput.value().equals(selectedGroup.getName())){
				selectedGroup.setName(groupNameInput.value());
			}
			//if(selectedDevice != null && !weightInput.value().equals(selectedGroup.getWeight(selectedDevice))){
			//	selectedGroup.setWeight(selectedDevice, Math.round(weightInput.value()));
			//}
		}
		Map<Device, Integer> selectedDevices = selectedGroup.getDevices();

		canvas.noStroke();


		int index = 0;
		int offset = 30;

		for(DeviceGroup g : groups){
			if(g == selectedGroup){
				FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
			}else{
				if(index % 2 == 0){
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._600);
				}else{
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._500);
				}
			}
			canvas.rect(1, offset - 3, 199, 14);

			if(isClicked(0, offset - 3, 199)){
				setSelectedGroup(g);
			}

			canvas.textAlign(PConstants.LEFT, PConstants.TOP);
			FlatColor.fill(canvas, Colors.WHITE);
			canvas.text(g.getName(), 5, offset);
			offset += 14;
			index ++;
		}

		offset = 30;
		index = 0;

		for(Device d : selectedDevices.keySet()){
			if(d == selectedDevice){
				FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
			}else{
				if(index % 2 == 0){
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._600);
				}else{
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._500);
				}
			}
			canvas.rect(200, offset - 3, 145, 14);

			if(isClicked(200, offset - 3, 145)){
				setSelectedDevice(d);
			}

			canvas.textAlign(PConstants.LEFT, PConstants.TOP);
			FlatColor.fill(canvas, Colors.WHITE);
			canvas.text(d.getName() + " ("+ selectedDevices.get(d)+")", 205, offset);
			offset += 14;
			index ++;
		}

		if(selectedGroup != null && !selectedGroup.getName().equals("(selection)")){
			if(selectedDevice != null && !weightInput.value().equals(selectedGroup.getWeight(selectedDevice))){
				selectedGroup.setWeight(selectedDevice, Math.round(weightInput.value()));
			}
		}

		setVirtualHeight(30 + Math.max(groups.size(), selectedDevices.size()) * 14 + 16);
	}

	private boolean isClicked(int offsetX, int offsetY, int width){
		return 
			offsetX < mouseX() &&
			mouseX() < + offsetX + width &&
			mouseY() > 27 &&
			mouseY() < height-16 &&
			hasFocus() &&
			offsetY - getScroll() < mouseY() &&
			mouseY() < offsetY + 14 - getScroll() &&
			MaShine.inputs.getState("mouse.left.press");
	}

	private void cloneGroup(){cloneGroup(groupNameInput.value());}
	private void cloneGroup(String name){
		DeviceGroup grp = new DeviceGroup(name, selectedGroup);
		selectedGroup = grp;
		MaShine.scene.addGroup(grp);
	}
	private void deleteGroup(){
		MaShine.scene.deleteGroup(selectedGroup);
		setSelectedGroup(null);
	}

	private void removeDevice(){
		selectedGroup.removeDevice(selectedDevice);
		setSelectedDevice(selectedGroup.getFirst());
	}
	private void reverseWeights(){
		if(null != selectedGroup || !selectedGroup.getName().equals("(selection)")){
			selectedGroup.reverseWeights();
		}
	}

	private void selectAll(){MaShine.ui.setSelectedDevices(MaShine.scene.getDevices());}
	private void selectClear(){MaShine.ui.clearSelectedDevices();}
	private void selectUniverse(){
		ArrayList<Device> devices = MaShine.scene.getDevices();
		ArrayList<Device> newSelection = new ArrayList<Device>();
		int univ = Math.round(univSelectInput.value());

		for(Device d : devices){
			if(d.getUniverse() == univ){
				newSelection.add(d);
			}
		}
		MaShine.ui.setSelectedDevices(newSelection);
	}

	private void setGroupAsSelection(){
		if(null != selectedGroup || !selectedGroup.getName().equals("(selection)")){
			MaShine.ui.setSelectedDevices(new ArrayList(selectedGroup.getDevices().keySet()));
		}
	}

	private void addCurrent(){
		if(null != selectedGroup || !selectedGroup.getName().equals("(selection)")){
			ArrayList<Device> devices = MaShine.ui.getSelectedDevices();
			for(Device d : devices){
				selectedGroup.putDevice(d);
			}
		}
	}
	private void removeCurrent(){
		if(null != selectedGroup || !selectedGroup.getName().equals("(selection)")){
			ArrayList<Device> devices = MaShine.ui.getSelectedDevices();
			for(Device d : devices){
				selectedGroup.removeDevice(d);
			}
		}
	}

	private void setSelectedDevice(Device d){
		selectedDevice = d;
		if(null != d && null != selectedGroup && selectedGroup.isIn(d)){
			weightInput.setValue((float) selectedGroup.getWeight(d));
		}
	}

	public DeviceGroup getSelectedGroup(){
		if(null != selectedGroup && selectedGroup.getName().equals("(selection)")){
			return null;
		}
		return selectedGroup;
	}

	public void setSelectedGroup(DeviceGroup  g){
		if(g != null && g != selectedGroup){
			selectedGroup = g;
			if(g.getName().equals("(selection)")){
				groupNameInput.setValue("new group");
			}else{
				groupNameInput.setValue(g.getName());
			}
			if(!g.isIn(selectedDevice)){
				if(!g.isEmpty()){
					setSelectedDevice(g.getFirst());
				}else{
					setSelectedDevice(null);
				}
			}else{
						// update weight input
				setSelectedDevice(selectedDevice);
			}
		}
	}

}