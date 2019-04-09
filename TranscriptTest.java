/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rojan
 */
public class TranscriptTest {

    Transcript t1;
    Transcript t2;

    @Before
    public void setUp() {

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
        GradeSchema.addGradeToLevel("Fail", "D-");
        GradeSchema.addGradeToLevel("Fail", "D+");
        GradeSchema.addGradeToLevel("Fail", "F");
        GradeSchema.addGradeToLevel("Other", "CTN");

        ArrayList<Course> courseList1 = new ArrayList<>();
        ArrayList<Course> courseList2 = new ArrayList<>();
        Course course1 = new Course("ADM2503", "FR01B", "A", 3.0, "2010/FA");
        Course course2 = new Course("ADM2504", "FR01A", "B", 3.0, "2010/FA");
        Course course3 = new Course("ADM2503", "FR01B", "A+", 3.0, "2010/FA");

        Course course4 = new Course("ADM2503", "FR01B", "D+", 3.0, "2010/FA");
        Course course5 = new Course("ADM2504", "FR01A", "D-", 3.0, "2010/FA");
        Course course6 = new Course("cs2263", "FR01B", "D", 3.0, "2010/FA");

        courseList1.add(course1);
        courseList1.add(course2);
        courseList1.add(course3);

        courseList2.add(course4);
        courseList2.add(course5);
        courseList2.add(course6);

        ArrayList<String> admlList = new ArrayList<String>() {
            {
                add("ADM2503");
                add("ADM2504");
            }
        };

        AreaSchema.addArea("ADM", admlList);
       
        t1 = new Transcript(courseList1);
        t2 = new Transcript(courseList2);
    }

    @Test
    public void testAddCourse() {
        Course newCourse = new Course("cs1317", "FR01B", "A", 3.0, "2010/FA");
        t1.addCourse(newCourse);
        assertTrue(t1.getPassedCourseMap().size() == 3);
    }

    @Test
    public void testAddRetakenCourse() {
        String result = "ADM2503";
        assertTrue(t1.getRetakenCourseMap().get(result).size() != 0);
    }

    @Test
    public void testGetPassedCourseMap() {

        Map<String, Course> result = t1.getPassedCourseMap();
        assertTrue(result.size() != 0);
    }

    @Test
    public void testFailedCourseMap() {
        String course = "ADM2504";
        assertTrue(t2.getFailedCourseMap().size() != 0);
    }

    @Test
    public void testGetAverageGradesByArea() {
        assertTrue(t1.getAverageGradesByArea().size()!=0);
    }
    
    @Test
    public void testCheckRank() {
        String result = "First Year";
        assertTrue(t1.checkRank().equalsIgnoreCase(result));

    }

}
