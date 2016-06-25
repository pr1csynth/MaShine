/**
 *  Interface view the state of the engine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  27-03-2016`
 */

package mashine.ui.boxes;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import mashine.MaShine;
import mashine.engine.Track;
import mashine.engine.Filter;
import mashine.ui.Colors;
import mashine.ui.Drawable;
import mashine.ui.FlatColor;

public class EngineView extends Drawable {


	public EngineView(){
		super(10, 50, MaShine.m.displayWidth - 10, MaShine.m.displayHeight - 50);
	}

	public void drawContent(){
		canvas.noStroke();
		canvas.clear();

		int offset = 10;

		for(Track t : MaShine.engine.getTracks()){
			int diff = 32 + (int) Math.ceil((t.sequencer.getSequence().getSize()-1)/24)*17;

			String filtersList = " ";
			boolean visible = true;

			for(Filter f : t.getFilters()){
				if(f.isEnabled()){
					filtersList += f.getType() + " ";
					if(f.getType().equals("drop_all")) visible = false;
				}				
			}

			if(t.sequencer.isTweaked()){
				FlatColor.fill(canvas, Colors.MATERIAL.TEAL.A700);
			}else if(t.sequencer.isManual()){
				FlatColor.fill(canvas, Colors.MATERIAL.ORANGE.A700);
			}else if(!visible){
				FlatColor.fill(canvas, Colors.MATERIAL.GREY._400);
			}else{
				FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._700);
			}
			canvas.rect(MaShine.m.width - 426, offset - 2, 426, diff);

			for(int i = 0; i < t.sequencer.getSequence().getSize(); i++){
				if(t.sequencer.getIndex() == i){
					FlatColor.fill(canvas, Colors.MATERIAL.ORANGE.A400);
				}else if(i < t.sequencer.getOffset()){			
					FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._300);
				}else if(i < t.sequencer.getClip() + t.sequencer.getOffset()){
					FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._800);	
				}else{
					FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._500);
				}
				canvas.rect(MaShine.m.width - 424 + (i%24) * 17, offset + (float) Math.ceil(i/24)*17, 15, 15);
			}
			
			offset += diff;

			FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._200);
			canvas.text(t.sequencer.getSequence().getName(),MaShine.m.width - 424, offset + 9);
			if(t.sequencer.isTweaked() || !visible){
				FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);
			}

			canvas.text(
				t.sequencer.getName()+" @"+(t.sequencer.getIndex()+1) +" >"+ t.sequencer.getOffset()+" %"+t.sequencer.getClip() + filtersList,
				MaShine.m.width - 424, offset - 6
			);
			offset += 20;
			
		}

		offset += 20;

		int diff = MaShine.engine.getFilters().size()*20;

		FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._700);
		canvas.rect(MaShine.m.width - 426, offset - 2, 426, diff);

		List<Filter> filters = new ArrayList<Filter>(MaShine.engine.getFilters());
		Collections.reverse(filters);

		for(Filter f : filters){

			FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._300);
			if(f.isEnabled()){
				FlatColor.fill(canvas, Colors.MATERIAL.CYAN._700);
				canvas.rect(MaShine.m.width - 424, offset, 100, 15);
				if(!f.getGroup().getName().equals("")){
					FlatColor.fill(canvas, Colors.MATERIAL.CYAN._600);
					canvas.rect(MaShine.m.width - 424+100, offset, 150, 15);
				}
				FlatColor.fill(canvas, Colors.WHITE);
			}
			canvas.text(f.getType(), MaShine.m.width - 424 + 3, offset + 12);
			canvas.text(f.getGroup().getName(), MaShine.m.width - 424 + 103, offset + 12);
			offset += 18;
		}
	}

}