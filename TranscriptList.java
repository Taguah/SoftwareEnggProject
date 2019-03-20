package project.excelSpike;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class TranscriptList {

	ArrayList<Transcript> transcriptList;
	
    private static Set<String> gradeSet = new HashSet<>(Arrays.asList(new String[] {"A+", "A", "A-", "B+", "B", 
            "B-", "C+", "C", "C-", "D", "F",
            "AUD", "CTN", "CR", "NCR", 
            "DNW", "INC", "NR", "W"} ));

    public TranscriptList(String sourceFolder) {
    	transcriptList = readTranscriptsFromFolder(sourceFolder);
    	
    }
    
    public Set<String> getAllCourseNames() {
    	HashSet<String> courseNames = new HashSet<String>();
    	for (Transcript transcript : transcriptList) {
    		ArrayList<Course> courseList = transcript.getCourses();
    		for (Course course : courseList) {
    			courseNames.add(course.getCourseID());
    		}
    	}
    	return courseNames;
    }
    
    public Map<String, ArrayList<Course>> getAllCoursesByArea(){
    	ArrayList<String> areas = AreaSchema.getAllAreas();
    	Map<String, ArrayList<Course>> areaMap = new HashMap<String, ArrayList<Course>>();
    	for (String area : areas) {
    		ArrayList<Course> areaCourses = this.getAllCoursesInArea(area);
    		areaMap.put(area, areaCourses);
    	}
    	return areaMap;
    }
    
    public ArrayList<Course> getAllCoursesInArea(String area){
    	ArrayList<Course> courseList = new ArrayList<Course>();
    	for (Transcript transcript : transcriptList) {
			ArrayList<Course> transcriptCourses = transcript.getCoursesByArea(area);
			Collections.copy(courseList, transcriptCourses);
		}
    	return courseList;
    }
    
    public RawDistribution createRawDistribution() {
    	RawDistribution rawDistribution = new RawDistribution("Filepath goes here");
    	return rawDistribution;
    }

    public Map<String, AreaDistribution> createAreaDistributionMap(){
    	Map<String, AreaDistribution> areaDistributionMap = new HashMap<String, AreaDistribution>();
    	ArrayList<String> areas = AreaSchema.getAllAreas();
    	for (String area : areas) {
    		AreaDistribution areaDistribution = createAreaDistribution(area);
    		areaDistributionMap.put(area, areaDistribution);
    	}
    	return areaDistributionMap;
    }
    
    public AreaDistribution createAreaDistribution(String area) {
    	AreaDistribution areaDistribution = new AreaDistribution(area, this.getAllCoursesInArea(area));
    	return areaDistribution;
    }
    
    /*
    Reads each transcript text file from a specified folder path and creates a 
    transcript object from each file. 
    Returns a list of all the transcripts within a folder.
    */
    private ArrayList<Transcript> readTranscriptsFromFolder(String folderPath) {

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

    /*
    public static void main(String[] args) {

        ArrayList<Transcript> tList = readTranscriptsFromFolder("/mnt/c/Users/Jenny/Google Drive/Winter2019/CS2043/Transcript Analyzer/Transcripts");
        for(Transcript transcript : tList) {
            for(Course course : transcript.getCourses()) {
                System.out.println(course.toString());
            }
            System.out.println();
        }
    }
    */
}
