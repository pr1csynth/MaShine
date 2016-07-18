// dimmer.js

var nodes = {
	in : {"dimmer" : "range"},
	out : {}
}

function forEachFeature(feature, weight){
	if(feature instanceof Features.color){
		feature.link(
			feature.getLinkedColor().dim(get("dimmer"))
		);
	}
	return feature;
}