/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

import java.util.*;
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
public class RawDistributionTest {

    RawDistribution rd;

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
        GradeSchema.addGradeToLevel("Fail", "F");
        GradeSchema.addGradeToLevel("Other", "CTN");

        ArrayList<Course> courseList1 = new ArrayList<>();
        ArrayList<Course> courseList2 = new ArrayList<>();
        Course course1 = new Course("ADM2503", "FR01B", "A", 3.0, "2010/FA");
        Course course2 = new Course("ADM2504", "FR01A", "B", 3.0, "2010/FA");
        Course course3 = new Course("ADM2503", "FR01B", "A+", 3.0, "2010/FA");

        Course course4 = new Course("ADM2503", "FR01B", "c", 3.0, "2010/FA");
        Course course5 = new Course("ADM2504", "FR01A", "B", 3.0, "2010/FA");
        Course course6 = new Course("cs2263", "FR01B", "B-", 3.0, "2010/FA");

        courseList1.add(course1);
        courseList1.add(course2);
        courseList1.add(course3);

        courseList2.add(course4);
        courseList2.add(course5);
        courseList2.add(course6);

        Transcript t1 = new Transcript(courseList1);
        Transcript t2 = new Transcript(courseList2);
        List<Transcript> transcripts = new ArrayList<>();
        transcripts.add(t1);
        transcripts.add(t2);
        TranscriptList tl = new TranscriptList(transcripts);
        rd = new RawDistribution(tl);
    }

    @Test
    public void testSetDistributionForCourses() {

        rd.setDistributionForCourses();
        assertTrue(rd.getRawDistributionMap().size() != 0);
    }

    @Test
    public void testSetDistributionForRetakenCourses() {
        rd.setDistributionForRetakenCourses();
        assertTrue(rd.getRetakenDistributionMap().size() != 0);
    }

    @Test
    public void testGetCourseDistribution() {
        String courseName = "ADM2504";
        rd.setDistributionForCourses();
        Map<String, Integer> result = rd.getCourseDistribution(courseName);
        assertTrue(result.size()!= 0);
    }
    
    @Test
    public void testGetRetakenCourseDistribution() {
        String courseName = "ADM2503";
        rd.setDistributionForRetakenCourses();
        Map<String, Integer> result = rd.getRetakenCourseDistribution(courseName);
        assertTrue(result.size()!= 0);
    }

   
}
