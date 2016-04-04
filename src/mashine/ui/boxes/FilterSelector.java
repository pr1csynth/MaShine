/**
 *  Interface to select and apply filters;
 *
 *	@author procsynth - Antoine Pintout
 *	@since  18-03-2016`
 */

package mashine.ui.boxes;

import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import mashine.Do;
import mashine.MaShine;
import mashine.engine.Track;
import mashine.engine.Filter;
import mashine.ui.Colors;
import mashine.ui.FlatColor;
import mashine.ui.UIBox;
import mashine.ui.elements.TextButton;
import processing.core.PConstants;

public class FilterSelector extends UIBox{

	private Filter selectedModel;
	private Filter selectedFilter;

	public FilterSelector () {
		super("FILTERS", 400, 600, 500, 230);


		MaShine.inputs.registerAction("ui.filt_select.next", new Do(){public void x(){}});
		MaShine.inputs.registerAction("ui.filt_select.prev", new Do(){public void x(){}});

		elements.add(new TextButton(this, "add >", 90, height-15, 55, 
			new Do(){public void x(){addFilter();}}
			));

		elements.add(new TextButton(this, "up", 155, height-15, 30, 
			new Do(){public void x(){moveUpFilter();}}
			));
		elements.add(new TextButton(this, "down", 187, height-15, 40, 
			new Do(){public void x(){moveDownFilter();}}
			));
		elements.add(new TextButton(this, "delete", 232, height-15, 55, 
			new Do(){public void x(){removeFilter();}}
			));	
		elements.add(new TextButton(this, "toggle", width - 74, height-15, 75, 
			new Do(){public void x(){toggleFilter();}}
			));

		elements.add(new TextButton(this, "bind", width - 74, height-32, 75,
			new Do(){public void x(){
				MaShine.ui.open("Linker");
				if(selectedFilter != null){
					MaShine.ui.linker.setFilterValue(selectedFilter.getName());
				}else{
					MaShine.ui.linker.setFilterValue("filter");
				}
			}}
		));
	}

	public void tick(){
	}

	public void drawFixedUI(){
		canvas.noStroke();
		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.rect(1, height - 16, width -1, 17);
		FlatColor.stroke(canvas, Colors.MATERIAL.BLUE_GREY._900);
		canvas.line(149, 20, 149, height-1);
		canvas.line(349, 20, 349, height-1);
		canvas.noStroke();

		FlatColor.fill(canvas, Colors.WHITE);

		canvas.text(getListName(), 295, height-13);

		if(selectedFilter != null){

			int offset = 28;

			canvas.text(selectedFilter.getType() + " (" + (selectedFilter.isEnabled() ? "enabled" : "disabled") + ")", 355,  offset);	
	
			offset += 20;

			canvas.textAlign(PConstants.RIGHT, PConstants.TOP);
			Map<String,Short> params = selectedFilter.getParameters();
			for(String p : params.keySet()){
				String t = "";
				short ti = params.get(p);
				if(ti != Filter.FRAME && ti != Filter.LONG){
					if(ti == Filter.RANGE) t = "RANGE";
					if(ti == Filter.STATE) t = "STATE";
					if(ti == Filter.GROUP) t = "GROUP";
					canvas.text(p+" :  "+ t, width - 5, offset);
					offset += 14;
				}
			}
		}else{
			canvas.text("no filter selected", 355,  28);	
		}
	}

	public void drawUI(){
		canvas.noStroke();
		int offset = 30;
		canvas.textAlign(PConstants.LEFT, PConstants.TOP);

		List<Filter> filterModels = new ArrayList(MaShine.bank.getFilters().values());
		setSelectedModel(drawFilterList(filterModels, selectedModel, 0, offset, 148));

		List<Filter> filterList = getFilterList();
		setSelectedFilter(drawFilterList(filterList, selectedFilter, 149, offset, 200));
		setVirtualHeight(45 + Math.max(filterModels.size(), filterList.size()) * 14);
	}

	public void setSelectedModel(Filter model){selectedModel = model;}
	public void setSelectedFilter(Filter filter){selectedFilter = filter;}

	private boolean isClicked(int offsetX, int offsetY, int width){
		return 
			offsetX < mouseX() &&
			mouseX() < + offsetX + width &&
			mouseY() > 27 &&
			mouseY() < height-16 &&
			hasFocus() &&
			offsetY - 3 - getScroll() < mouseY() &&
			mouseY() < offsetY + 11 - getScroll() &&
			MaShine.inputs.getState("mouse.left.press");
	}

	private Filter drawFilterList(List<Filter> filters, Filter selected, int offsetX, int offsetY, int width){
		int index = 0;
		Filter newSelected = selected;
		Collections.reverse(filters);
		for(Filter m : filters){
			if(m == selected){
				FlatColor.fill(canvas,Colors.MATERIAL.ORANGE.A400);
			}else if(m.isEnabled()){
				if(index % 2 == 0){
					FlatColor.fill(canvas,Colors.MATERIAL.CYAN._700);
				}else{
					FlatColor.fill(canvas,Colors.MATERIAL.CYAN._600);
				}
			}else{
				if(index % 2 == 0){
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._600);
				}else{
					FlatColor.fill(canvas,Colors.MATERIAL.BLUE_GREY._500);
				}
			}
			canvas.rect(offsetX+1, offsetY - 3, width, 14);

			if(isClicked(offsetX, offsetY, width)){
				newSelected = m;
			}

			FlatColor.fill(canvas,Colors.WHITE);
			canvas.text(m.getName(), offsetX+5, offsetY);
			offsetY += 14;
			index ++;
		}

		Collections.reverse(filters);
		return newSelected;
	}

	private void addFilter(){
		if(selectedModel != null){	
			ArrayList<Track> tracks = MaShine.engine.getTracks();
			for(Track t : tracks){
				if(t.isTweaked()){
					t.addFilter(selectedModel.getType());
					return;
				}
			}
			MaShine.engine.addFilter(selectedModel.getType());
		}
		return;
	}

	private void toggleFilter(){
		if(null != selectedFilter){
			selectedFilter.toggle();
		}
	}

	private void moveDownFilter(){
		List<Filter> filters = getFilterList();
		int i = filters.indexOf(selectedFilter);
		if(i != -1 && i != 0)
			Collections.swap(filters, i, i - 1);
	}
	private void moveUpFilter(){
		List<Filter> filters = getFilterList();
		int i = filters.indexOf(selectedFilter);
		if(i != -1 && i != filters.size() -1)
			Collections.swap(filters, i, i + 1);
	}

	private void removeFilter(){
		List<Filter> fl = getFilterList();
		fl.remove(selectedFilter);
		if(fl.size() != 0){
			selectedFilter = fl.get(fl.size()-1);
		}else{
			selectedFilter = null;
		}
	}

	private List<Filter> getFilterList(){
		ArrayList<Track> tracks = MaShine.engine.getTracks();
		for(Track t : tracks){
			if(t.isTweaked()){
				return t.getFilters();
			}
		}
		return MaShine.engine.getFilters();
	}
	private String getListName(){
		ArrayList<Track> tracks = MaShine.engine.getTracks();
		for(Track t : tracks){
			if(t.isTweaked()){
				return "track."+t.getName();
			}
		}
		return "mixer";
	}
}