/**
 *  Interface view the state of the engine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  27-03-2016`
 */

package mashine.ui.boxes;

import mashine.MaShine;
import mashine.engine.Track;
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

			if(t.sequencer.isTweaked()){
				FlatColor.fill(canvas, Colors.MATERIAL.TEAL.A700);
			}else if(t.sequencer.isManual()){
				FlatColor.fill(canvas, Colors.MATERIAL.ORANGE.A700);
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
			if(t.sequencer.isTweaked()){
				FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);
			}
			canvas.text(
				t.sequencer.getName()+" @"+(t.sequencer.getIndex()+1) +" >"+ t.sequencer.getOffset()+" %"+t.sequencer.getClip(),
				MaShine.m.width - 424, offset - 6
			);
			offset += 20;
			
		}
	}

}