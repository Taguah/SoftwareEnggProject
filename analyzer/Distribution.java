package analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Distribution {

	protected TranscriptList transcriptList;
	
	public Map<String, Integer> createDistributionMap(){
		Map<String, Integer> distributionMap = new HashMap<String, Integer>();
		Set<String> levels = GradeSchema.getLevels();
		for (String level : levels){
			distributionMap.put(level.toString(), 0);
		}
		return distributionMap;
	}
}