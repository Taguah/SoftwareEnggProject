package project.excelSpike;

import java.util.List;
import java.util.ArrayList;
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
		
		//Create courses to test
		Course failCourse1 = new Course("Math111", "01B", "F", 3.0, "Fall");
		Course failCourse2 = new Course("Math101", "01B", "F", 3.0, "Winter");
		Course failCourse3 = new Course("Math101", "01B", "B+", 3.0, "Fall");

		Course improveCourse1 = new Course("Engl101", "01B", "C", 3.0, "Fall");
		Course improveCourse2 = new Course("Engl101", "01B", "A", 3.0, "Fall");
		
		Course dummyCourse1 = new Course("Math111", "01B", "A", 3.0, "Fall");
		
		Transcript testTranscript1 = new Transcript();
		testTranscript1.addCourse(failCourse1);
		testTranscript1.addCourse(failCourse2);
		testTranscript1.addCourse(failCourse3);
		testTranscript1.addCourse(improveCourse1);
		testTranscript1.addCourse(improveCourse2);
		
		Transcript testTranscript2 = new Transcript();
		
		List<Transcript> trList = new ArrayList<Transcript>();
		trList.add(testTranscript1);
		trList.add(testTranscript2);
		
		TranscriptList testList = new TranscriptList(trList);
		
		Map<String, Integer> studentsPerYear = testList.getStudentsPerYear();
		for (String year : studentsPerYear.keySet()) {
			System.out.println("There are " + studentsPerYear.get(year) + " students in " + year);
		}
		System.out.println();
		
		/*
		Map<String, List<String>> takenCourseGrades = testList.getTakenCourseGrades();
		for(String courseName : takenCourseGrades.keySet()) {
			System.out.print(courseName);
			for(String grade : takenCourseGrades.get(courseName)) {
				System.out.print("\t" + grade);
			}
			System.out.println();
		}
		
		Map<String, List<String>> retakenCourseGrades = testList.getRetakenCourseGrades();
		for(String courseName : retakenCourseGrades.keySet()) {
			System.out.print(courseName + " Retaken");
			for(String grade : retakenCourseGrades.get(courseName)) {
				System.out.print("\t" + grade);
			}
			System.out.println();
		}
		*/
		
		AreaDistribution areaDist = new AreaDistribution(testList);
		RawDistribution rawDist = new RawDistribution(testList);
		
		for(String area : AreaSchema.getAllAreas()) {
			Map<String, Integer> areaMap = areaDist.getDistributionForArea(area);
			System.out.println(area);
			for(String level : areaMap.keySet()) {
				System.out.println(level + "\t" + areaMap.get(level));
			}
			System.out.println();
		}

		Map<String, Map<String, Integer>> rawMap = rawDist.getRawDistributionMap();
		for (String courseName : rawMap.keySet()) {
			Map<String, Integer> courseDist = rawMap.get(courseName);
			System.out.println(courseName);
			for(String level : courseDist.keySet()) {
				System.out.println(level + "\t" + courseDist.get(level));
			}
			System.out.println();
		}
		
		Map<String, Map<String, Integer>> retakenMap2 = rawDist.getRetakenDistributionMap();
		for (String courseName : retakenMap2.keySet()) {
			Map<String, Integer> courseDist = retakenMap2.get(courseName);
			System.out.println(courseName + " Retaken");
			for(String level : courseDist.keySet()) {
				System.out.println(level + "\t" + courseDist.get(level));
			}
			System.out.println();
		}
		
		/*
		Course course1 = new Course("Math101", "01B", "A+", 3.0, "Fall");
		Course course2 = new Course("Math102", "02B", "C-", 3.0, "Winter");
		Course course3 = new Course("Engl101", "01B", "F", 3.0, "Fall");
		Course course4 = new Course("Engl101", "01B", "B-", 3.0, "Fall");
		Course course5 = new Course("Engl102", "01B", "D", 3.0, "Fall");

		
		//Add courses to transcripts
		Transcript testTranscript1 = new Transcript();
		testTranscript1.addCourse(failCourse1);
		testTranscript1.addCourse(failCourse2);
		testTranscript1.addCourse(failCourse3);

		Transcript testTranscript2 = new Transcript();
		testTranscript2.addCourse(course4);
		testTranscript2.addCourse(course5);

		//Add transcript to transcriptlist
		ArrayList<Transcript> trList = new ArrayList<Transcript>() {
			{
				add(testTranscript1);
//				add(testTranscript2);
			}
		};
		TranscriptList testList = new TranscriptList(trList);

		//Testing if this worked
		Set<String> courseNames = testList.getAllCourseNames();
		System.out.println("Printing all courses in all transcripts");
		for (String course : courseNames) {
			System.out.println(course);
		}
		System.out.println();
		

		Map<String, List<String>> areaAverages = testList.getAllAveragesByArea();
		for (String area : areaAverages.keySet()) {
			System.out.println(area);
			for (String grade : areaAverages.get(area)) {
				System.out.println(grade);
			}
		}
		System.out.println();
		
		System.out.println("Math Courses:");
		List<String> mathCourses = AreaSchema.getAllCoursesInArea("Math");
		for (String course : mathCourses) {
			System.out.println(course);
		}
		System.out.println();

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
		
		//Create an area distribution to test
		AreaDistribution testAreaDist = new AreaDistribution(testList);
		
		for (String area : areaAverages.keySet()) {
			System.out.println(area);
			Map<String, Integer> areaMap = testAreaDist.getDistributionForArea(area);
			for (String level : areaMap.keySet()) {
				System.out.print(level + "    ");
				System.out.println(areaMap.get(level));
			}
			System.out.println();
		}

		RawDistribution testRawDist = new RawDistribution(testList);
		
		Map<String, Map<String, Integer>> rawMap = testRawDist.getRawDistributionMap();
		for (String course : rawMap.keySet()) {
			System.out.println(course);
			Map<String, Integer> courseMap = testRawDist.getCourseDistribution(course);
			for (String level : courseMap.keySet()) {
				System.out.print(level + "    ");
				System.out.println(courseMap.get(level));
			}
			System.out.println();
		}
		*/
	}
}
