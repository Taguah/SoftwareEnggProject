/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

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
public class CourseTest {

    Course course1;

    @Before
    public void setUp() {
        course1 = new Course("ADM2503", "FR01B", "A", 3.0, "2010/FA");
    }

    @Test
    public void testGetCourseID() {

        String result = "ADM2503";
        assertTrue(course1.getCourseID().equalsIgnoreCase(result));

    }

    @Test
    public void testGetGrade() {
        String result = "A";
        assertTrue(course1.getGrade().getLetterGrade().equalsIgnoreCase(result));
    }

    @Test
    public void testGetCreditHours() {
        double result = 3.0;
        assertEquals(result, course1.getCreditHours(), 0);
    }

}
