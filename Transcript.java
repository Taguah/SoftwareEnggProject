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
    
    public static double averageForArea(String area){
        double average;
        double creditHours;
        average = 0;
        creditHours = 0;
        ArrayList<Course> coursesInArea = new ArrayList<>(); 
        AreaSchema areaSchema = new AreaSchema();
        coursesInArea = areaSchema.getAllCoursesInArea(area);
        for(Course c: coursesInArea){
            creditHours += c.getCreditHour();
            average += c.getCreditHour() * ( c.getGrade().getNumberGrade() );
        }
        return (average/creditHours); 
    }

    /*
    Reads each transcript text file from a specified folder path and creates a 
    transcript object from each file. 
    Returns a list of all the transcripts within a folder.
    */
    public static ArrayList<Transcript> readTranscriptsFromFolder(String folderPath) {

        ArrayList<Transcript> transcriptList = new ArrayList<>();

        try {
            File folder = new File(folderPath);

            for(File file : folder.listFiles()) {
                String filepath;

                if(file.isFile()) {
                    filepath = file.getAbsolutePath();
                    Transcript transcript = readTranscriptFromFile(filepath);
                    transcriptList.add(transcript);
                }
            }
            return transcriptList;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Reads line by line from a transcript text file, and splits each line on
    the transcript into an array of strings, which represent the different
    attributes in a course. 
    Returns a transcript object which contatins a list of all the courses 
    listed in one transcript.
    */
    public static Transcript readTranscriptFromFile(String filePath) {

        ArrayList<Course> courses = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty()) {
                    continue;
                }
                String[] strArr = line.split("\\s+");
                int n = strArr.length;
                String grade = null;
                if(gradeSet.contains(strArr[n-3])) {
                    grade = strArr[n-3];
                }
                Course course = new Course(strArr[0], strArr[1], grade, Double.parseDouble(strArr[n-2]), strArr[n-1]);
                courses.add(course);
            }
            Transcript transcript = new Transcript(courses);
            return transcript;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if(br != null) {
                try {
                    br.close();
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
   
    public static void main(String[] args) {

        ArrayList<Transcript> tList = readTranscriptsFromFolder("/mnt/c/Users/Jenny/Google Drive/Winter2019/CS2043/Transcript Analyzer/Transcripts");
        for(Transcript transcript : tList) {
            for(Course course : transcript.getCourses()) {
                System.out.println(course.toString());
            }
            System.out.println();
        }
    }
}

