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
		FlatColor.fill(canvas, Colors.WHITE);
		for(Track t : M.engine.getTracks()){
			canvas.text(t.sequencer.getName()+" @"+t.sequencer.getIndex() +" >"+ t.sequencer.getOffset()+" %"+t.sequencer.getClip(), M.width - 424, offset);
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
				canvas.rect(M.width - 424 + (i%24) * 17, offset + 14 + (float) Math.ceil(i/24)*17, 15, 15);
			}
			offset +=30;
		}
	}

}