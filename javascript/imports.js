// imports from MaShine java app

print("Importing java env...");

var Features = {
	color : Java.type("mashine.scene.features.ColorFeature"),
	coords: Java.type("mashine.scene.features.Coords"),
	zoom:   Java.type("mashine.scene.features.Zoom"),
}

var Color = Java.type("mashine.ui.FlatColor");
var Feature = Java.type("mashine.scene.features.EditableFeature");
var Frame = Java.type("mashine.scene.Frame");

function getInNodes(){
	var HashMap = Java.type("java.util.HashMap");
	var ins = new HashMap();
	if("in" in nodes){
		for(i in nodes.in){
			ins.put(i, nodes.in[i]);
		}
	}
	return ins;
}
function getOutNodes(){
	var HashMap = Java.type("java.util.HashMap");
	var outs = new HashMap();
	if("out" in nodes){
		for(i in nodes.out){
			outs.put(i, nodes.out[i]);
		}
	}
	return outs;
}

function get(nodeName){
	if(nodeName in nodes.in){
		if(nodes.in[nodeName] == "range") return filter.getRange(nodeName);
		if(nodes.in[nodeName] == "state") return filter.getState(nodeName);
	}else{
		print("no "+ nodeName +" in filter nodes");
	}
}
function hasForEachFrame(){return typeof forEachFrame !== 'undefined';}
function hasForEachFeature(){return typeof forEachFeature !== 'undefined';}
function hasForEachDevice(){return typeof forEachFeatureInDevices !== 'undefined';}