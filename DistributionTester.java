package analyzer;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class DistributionTester {

	public static void main(String[] args) {

		//Set the grades in the distribution
		GradeSchema.addGradeToLevel("Exceeds", "A+");
		GradeSchema.addGradeToLevel("Exceeds", "A");
		GradeSchema.addGradeToLevel("Exceeds", "A-");
		GradeSchema.addGradeToLevel("Meets", "B+");
		GradeSchema.addGradeToLevel("Meets", "B");
		GradeSchema.addGradeToLevel("Meets", "B-");
		GradeSchema.addGradeToLevel("Marginal", "C+");
		GradeSchema.addGradeToLevel("Marginal", "C");
		GradeSchema.addGradeToLevel("Marginal", "C-");
		GradeSchema.addGradeToLevel("Fail", "D");
		GradeSchema.addGradeToLevel("Fail", "F");
		GradeSchema.addGradeToLevel("Other", "CTN");
		
		//Set the equivalencies
		List<String> equivalents = new ArrayList<String>();
		equivalents.add("Engl102");
		EquivalencySchema.setEquivalency("Engl101", equivalents);
		equivalents = new ArrayList<String>();
		equivalents.add("Math111");
		EquivalencySchema.setEquivalency("Math101", equivalents);

		//Set the areas
		ArrayList<String> mathList = new ArrayList<String>() {
			{
				add("Math101");
				add("Math102");
			}
		};
		
		ArrayList<String> englList = new ArrayList<String>() {
			{
				add("Engl101");
				add("Engl102");
			}
		};

		AreaSchema.addArea("Math", mathList);
		AreaSchema.addArea("Engl", englList);
		
		
		//Specific Instance Test
		TranscriptList testList = TranscriptFileReader.readTranscriptsFromFolder("/home1/ugrads/ecamp/CS2043/cohort_1");
		
		Map<String, Integer> studentsPerYear = testList.getStudentsPerYear();
		for (String year : studentsPerYear.keySet()) {
			System.out.println("There are " + studentsPerYear.get(year) + " students in " + year);
		}
		System.out.println();
		AreaDistribution areaDist = new AreaDistribution(testList);
		RawDistribution rawDist = new RawDistribution(testList);
		
		List<String> areas = AreaSchema.getAllAreas();
		Collections.sort(areas);
		
		for(String area : areas) {
			Map<String, Integer> areaMap = areaDist.getDistributionForArea(area);
			System.out.println(area);
			for(String level : areaMap.keySet()) {
				System.out.println(level + "\t" + areaMap.get(level));
			}
			System.out.println();
		}

		Map<String, Map<String, Integer>> rawMap = rawDist.getRawDistributionMap();
		List<String> courseNames = new ArrayList<String>(rawMap.keySet());
		Collections.sort(courseNames);
		for (String courseName : courseNames) {
			Map<String, Integer> courseDist = rawMap.get(courseName);
			System.out.println(courseName);
			for(String level : courseDist.keySet()) {
				System.out.println(level + "\t" + courseDist.get(level));
			}
			System.out.println();
		}

		Map<String, Map<String, Integer>> retakenMap2 = rawDist.getRetakenDistributionMap();
		courseNames = new ArrayList<String>(retakenMap2.keySet());
		Collections.sort(courseNames);
		for (String courseName : courseNames) {
			Map<String, Integer> courseDist = retakenMap2.get(courseName);
			System.out.println(courseName + " Retaken");
			for(String level : courseDist.keySet()) {
				System.out.println(level + "\t" + courseDist.get(level));
			}
			System.out.println();
		}
	}
}
