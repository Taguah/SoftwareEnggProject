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
public class GradeTest {
    
    static Grade g1;
    static Grade g2;

    
    @BeforeClass
    public static void setUpClass() {
        g1 = new Grade("A");
        g2 = new Grade(4.2);
    }
   
    @Test
    public void testConvertLetterToNumber() {
        double expResult = 4.0;
        double result = Grade.convertLetterToNumber(g1.getLetterGrade());
        assertEquals(expResult, result, 0);
    }

    @Test
    public void testConvertNumberToLetter() {
       String expResult = "A";
       String result = Grade.convertNumberToLetter(g2.getNumberGrade());
       assertEquals(expResult, result);
    }

    @Test
    public void testGetNumberGrade() {
        double expResult = 4.2;
        double result = g2.getNumberGrade();
        assertEquals(expResult, result, 0);
    }

    @Test
    public void testGetLetterGrade() {
        String expResult = "A";
        String result = g1.getLetterGrade();
        assertEquals(expResult, result);
       
    }
    
}
