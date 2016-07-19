var nodes = {
	in : {
		"rate" : "range",
	},
	out : {}
};

var currentFrame = new Frame();

function forEachFrame(frame){
	var targetFrame = new Frame(frame);
	var returnFrame = new Frame();
	var rate = get("rate");
	rate = Math.max(0.002, rate*rate);


	var targetFeatures = targetFrame.getFeatures();

	print(targetFeatures.keySet());
	for each(var targetFeatureId in targetFeatures.keySet()){

		print(targetFeatureId);
		var currentFeature = currentFrame.getFeature(targetFeatureId);
		var targetFeature = cloneFeature(targetFeatures.get(targetFeatureId));

		print(currentFeature);

		if(currentFeature != null){
			for each(var targetFieldId in targetFeature.getFields().keySet()){
				var currentFieldValue = currentFeature.getField(targetFieldId); 
				var targetFieldValue = targetFeature.getField(targetFieldId);
				if(currentFieldValue != targetFieldValue){
					var newValue = 0;
					var change = (targetFieldValue - currentFieldValue) * rate;
					if(change < 0) newValue = currentFieldValue + Math.round(Math.min(-1, change));
					if(change > 0) newValue = currentFieldValue + Math.round(Math.max(+1, change)); 
					currentFeature.setField(targetFieldId,newValue);
				}
			}

			returnFrame.addFeature(targetFeatureId, currentFeature);
		}else{
			returnFrame.addFeature(targetFeatureId, targetFeature);
		}
	}

	currentFrame = returnFrame;
	return returnFrame;
}
