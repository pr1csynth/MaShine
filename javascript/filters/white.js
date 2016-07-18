// white.js

var nodes = {
	in : {"white" : "range"},
	out : {}
}

function forEachFeatureInDevices(feature, weight){
	if(feature instanceof Features.color){
		feature.link(
			new Color(255).dim(get("white"))
		);
		return feature;
	}
}