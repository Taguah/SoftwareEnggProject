package project.excelSpike;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class AreaDistribution extends Distribution{

	private Map<String, Map<String, Integer>> areaDistributionMap;
	
	public AreaDistribution(TranscriptList transcriptList) {
		this.transcriptList = transcriptList;
		
		areaDistributionMap = new HashMap<String, Map<String, Integer>>();
		this.setDistributionForAreas();	
	}

	public void setDistributionForAreas() {
		Map<String, List<String>> areaAverages = transcriptList.getAllAveragesByArea();
		for (String area : areaAverages.keySet()){
			List<String> averageGrades = areaAverages.get(area);
			Map<String, Integer> areaDistribution = this.createDistributionMap();
			Set<String> levels = GradeSchema.getLevels();
			for (String grade : averageGrades) {
				for (String level : levels) {
					Set<String> gradesInLevel = GradeSchema.getGradesForLevel(level);
					if (gradesInLevel.contains(grade)) {
						Integer number = areaDistribution.get(level);
						areaDistribution.put(level, number+1);
					}
				}
			}
			areaDistributionMap.put(area, areaDistribution);
		}
	}

	public Map<String, Integer> getDistributionForArea(String areaName) {
		return areaDistributionMap.get(areaName);
	}
}