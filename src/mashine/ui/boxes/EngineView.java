/**
 *  Interface view the state of the engine
 *
 *	@author procsynth - Antoine Pintout
 *	@since  27-03-2016`
 */

package mashine.ui.boxes;

import mashine.*;
import mashine.ui.*;
import mashine.ui.Colors;
import mashine.ui.elements.*;
import mashine.scene.*;
import mashine.engine.*;
import java.lang.Math;
public class EngineView extends Drawable {


	public EngineView(MaShine m){
		super(m, 10, 50, m.displayWidth - 10, m.displayHeight - 50);
	}

	public void drawContent(){
		canvas.noStroke();
		canvas.clear();

		int offset = 10;

		for(Track t : M.engine.getTracks()){
			int diff = 32 + (int) Math.ceil((t.sequencer.getSequence().getSize()-1)/24)*17;


			FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);
			canvas.rect(M.width - 426, offset - 2, 426, diff);

			for(int i = 0; i < t.sequencer.getSequence().getSize(); i++){
				if(t.sequencer.getIndex() == i){
					FlatColor.fill(canvas, Colors.MATERIAL.ORANGE.A400);
				}else if(i < t.sequencer.getOffset()){			
					FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._600);
				}else if(i < t.sequencer.getClip() + t.sequencer.getOffset()){
					FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._900);	
				}else{
					FlatColor.fill(canvas, Colors.MATERIAL.BLUE_GREY._500);
				}
				canvas.rect(M.width - 424 + (i%24) * 17, offset + (float) Math.ceil(i/24)*17, 15, 15);
			}
			
			offset += diff;

			FlatColor.fill(canvas, Colors.WHITE);
			canvas.text(
				t.sequencer.getName()+" @"+(t.sequencer.getIndex()+1) +" >"+ t.sequencer.getOffset()+" %"+t.sequencer.getClip(),
				M.width - 424, offset - 6
			);
			offset += 5;
			
		}
	}

}