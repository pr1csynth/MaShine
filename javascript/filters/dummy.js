// dummy.js

var nodes = {
	// Links to the outside
	// the get the external value :
	// get("range_in") or
	// get("state_in")
	in : {
		"range_in" : "range",
		"state_in" : "state"},
	out : {/*not implemented yet*/}
}

// You can declare variables to store and use them.
var lastFrame = new Frame();

function forEachFrame(frame){
	// Get called for each frame
	// Should return a Frame object
	// Get called even if filter's group is unset
	lastFrame = frame;
	return frame;
}

function forEachFeature(feature, weight){
	// Get called for each feature IN THE FRAME
	// Not called if the filter's group is unset
	//
	// Must return a Feature object :
	// Feature or
	// Features.color,
	// Features.coords,
	// Features.zoom...
	return feature;
}

function forEachFeatureInDevices(feature, weight){
	// Get called for each feature IN THE DEVICES
	// (feature that can be absent in the Frame).
	// Not called if the filter's group is unset
	//
	// May return a Feature object or null:
	// Feature or
	// Features.color,
	// Features.coords,
	// Features.zoom...
	// or null;
	// The returned feature replace the frame's one if it exist
	return null;
}
