# WHAT ?

MaShine is a software to do live light show  using live audio analysis, prerecorded animations (sequences of frames), filters (function, algorithm written (inside or outside the software, using javascript maybe, I hope.)), midi input, Open Lighting Architecture for DMX/ArtNet output.

You can check the draft version here : https://github.com/procsynth/ShineProcessing

# HOW

Require Processing 3, Java 8 and ant to build. Run `ant` in the root folder to build and run, or `java -jar dist/mashine.jar` to run.
Midi device only tested on Linux. Otherwise it should be crossplateform.

## What the hell are you doing ?

 - 10:25 10 fev 2016 : implementing focusable 
 - 15:12 10 fev 2016 : implemented focusable, nice mouse related elements methods
 - 15:13 10 fev 2016 : what could I do now ?
 - 20:00 10 fev 2016 : little pack script.
 - 02:00 11 fev 2016 : added all the colors \o/
 - 08:30 11 fev 2016 : TODO : ola, port, devices, universes, addresses, -images- frames, device features (color, range, fixed)
 - 00:34 13 fev 2016 : DONE : midi inputs (generic (?) but particularly Behringer CMD DC-1 and Korg NanoKontrol2) (LOT of inputs !)
 - 00:35 13 fev 2016 : TODO : scrollable content (LOT of inputs !)
 - 16:47 14 fev 2016 : better mouse event/element focus behavior, began frame and sequence stuff (devices, features, frame, sequence, visualizer) a lot to do
 - 16:49 14 fev 2016 : TODO : UI for creating scene/frames, UI for typing
 - 23:21 14 fev 2016 : better device drawing, added menu bar, worked out how frame can work, better focus, commencing UI device editor (2 buttons yeah!)
 - 16:37 18 fev 2016 : basic user text input, should add regEx validation, maxlength check.
 - 16:43 22 fev 2016 : git versionning ! Main UI elements are drawn on separate PGraphics canvas, and it works (soon : scrollable) 

 # WHICH LICENSE ?

This software is licensed under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.

This software use :
The [Processing](https://processing.org) export libraries under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[Minim](http://code.compartmental.net/tools/minim/) under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[The MidiBus](http://www.smallbutdigital.com/themidibus.php) under the GNU/GPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[The OLA client](https://www.openlighting.org/ola/) with Google's protobuf software under a custom license which can be found in the [LICENSES][licenses] file.
The Roboto Mono font under the Apache 2.0 license,a copy is provided in the [LICENSES][licenses] file.

[licenses]: https://github.com/procsynth/MaShine/blob/master/LICENSES "Licenses file"