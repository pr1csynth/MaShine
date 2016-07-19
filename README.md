# WHAT ?

MaShine is a software to do live light show  using live audio analysis, prerecorded animations (sequences of frames), filters (function, algorithms, effects, ...), MIDI/DMX input, Open Lighting Architecture for DMX/ArtNet output.

![mashine](https://cloud.githubusercontent.com/assets/321345/14266488/eed3cbbc-fac8-11e5-8db3-842b1792f12e.png)

# HOW TO USE 
Download the [latest build](https://github.com/procsynth/MaShine/blob/devel/dist/mashine.jar?raw=true). You may want to install [OLA](https://www.openlighting.org/ola/) to output real DMX, but you can still use MaShine without it.

Midi devices only tested on Linux. Otherwise it should be crossplateform (OLA is only available on Linux and OSX).

TODO : user manual.

# HOW TO BUILD

Require Java 8 and ant to build. Run `ant` in the root folder to build and run.

# TODO

- [x] Inputs basics
- [x] Outputs basics
- [x] Patch
- [x] Animation
- [x] Saves
- [x] Input binding
- [x] Filters mechanism
- [x] Device groups in filters
- [x] DMX input
- [x] FFT ranges
- [x] More device features
- [x] More filters
- [ ] __A complete demo set__
- [ ] protobuf saves
- [ ] grid ui (see [Blocks](https://github.com/procsynth/Blocks))
- [x] MIDI outputs (get rid of themidibus ?)
- [ ] integrated Artnet node, get rid of OLA ?
- [ ] User manual

# WHICH LICENSE ?

This software is licensed under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.

This software use :
The [Processing](https://processing.org) export libraries under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[Minim](http://code.compartmental.net/tools/minim/) under the GNU/LGPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[The MidiBus](http://www.smallbutdigital.com/themidibus.php) under the GNU/GPL v3 license, a copy is provided in the [LICENSES][licenses] file.
[The OLA client](https://www.openlighting.org/ola/) with Google's protobuf software under a custom license which can be found in the [LICENSES][licenses] file.
The Roboto Mono font under the Apache 2.0 license,a copy is provided in the [LICENSES][licenses] file.

[licenses]: https://github.com/procsynth/MaShine/blob/master/LICENSES "Licenses file"
