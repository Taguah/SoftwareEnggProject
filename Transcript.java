/*
@author Jenny Wang
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Transcript {

    private ArrayList<Course> courselist = new ArrayList<>();
    private static Set<String> gradeSet = new HashSet<>(Arrays.asList(new String[] {"A+", "A", "A-", "B+", "B", 
                                                                                    "B-", "C+", "C", "D", "F",
                                                                                    "AUD", "CTN", "CR", "NCR", 
                                                                                    "DNW", "INC", "NR", "W"} ));

    public Transcript(ArrayList<Course> courselist) {
        this.courselist = courselist;
    }
    
    public void addCourse(Course course) {
    	this.courselist.add(course);
    }

    public ArrayList<Course> getCourses() {
        return courselist;
    }
    
    public ArrayList<Grade> getAllGrades(){
    	ArrayList<Grade> grades = new ArrayList<Grade>();
    	for(Course course : courselist) {
    		grades.add(course.getGrade());
    	}
    	return grades;
    }
    
    public ArrayList<Course> getCoursesByArea(String area){
    	ArrayList<Course> areaCourses = new ArrayList<Course>();
    	ArrayList<String> relevantCourses = AreaSchema.getAllCoursesInArea(area);
    	for (Course course : courselist) {
    		if (relevantCourses.contains(course.getCourseID())){
    			areaCourses.add(course);
    		}
    	}
    	return areaCourses;
    }
    
    public static double getAverageForArea(String area){
        double average = 0;
        double creditHours = 0;
        
        ArrayList<Course> coursesInArea = new ArrayList<>(); 
        AreaSchema areaSchema = new AreaSchema();
        coursesInArea = areaSchema.getAllCoursesInArea(area);
        for(Course c: coursesInArea){
            creditHours += c.getCreditHour();
            average += c.getCreditHour() * ( c.getGrade().getNumberGrade() );
        }
        return (average/creditHours); 
    }
}

