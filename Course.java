package project.excelSpike;

public class Course {
    private String courseID;
    private String section;
    private Grade grade;
    private double creditHours;
    private String term;

    public Course(String courseID, String section, String grade, double creditHours, String term) {
        this.courseID = courseID;
        this.section = section;
        this.grade = new Grade(grade);
        this.creditHours = creditHours;
        this.term = term;
    }

    public String getCourseID() {
        return courseID;
    }

    public String toString() {
        return courseID + " " + section + " " +  grade + " " + creditHours + " " + term;
    }

    public Grade getGrade() {
    	return grade;
    }
    
    public double getCreditHours() {
    	return creditHours;
    }
}