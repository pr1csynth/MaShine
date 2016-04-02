# WHAT ?

MaShine is a software to do live light show  using live audio analysis, prerecorded animations (sequences of frames), filters (function, algorithms, effects, ...), MIDI/DMX input, Open Lighting Architecture for DMX/ArtNet output.

![mashine](https://cloud.githubusercontent.com/assets/321345/14228588/b38be446-f919-11e5-99f9-edbed3daf3d5.png)


# HOW

Require Processing 3, Java 8 and ant to build. Run `ant` in the root folder to build and run, or `java -jar dist/mashine.jar` to run. You may want to edit the Processing paths in `build.xml` to match your installation.
Midi device only tested on Linux. Otherwise it should be crossplateform.

# TODO

- [x] Patch
- [x] Animation
- [x] Saves
- [x] Input binding
- [ ] Filters
- [ ] More device features
- [ ] Go in the details of the UI/interactions

# WHICH LICENSE ?

This software is licensed under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.

This software use :
The [Processing](https://processing.org) export libraries under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[Minim](http://code.compartmental.net/tools/minim/) under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[The MidiBus](http://www.smallbutdigital.com/themidibus.php) under the GNU/GPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[The OLA client](https://www.openlighting.org/ola/) with Google's protobuf software under a custom license which can be found in the [LICENSES][licenses] file.
The Roboto Mono font under the Apache 2.0 license,a copy is provided in the [LICENSES][licenses] file.

[licenses]: https://github.com/procsynth/MaShine/blob/master/LICENSES "Licenses file"
