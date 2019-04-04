/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author rojan
 */
public class Analyzer {
    
 private LoginF login=new LoginF();
   
    private static void changeLookAndFeel() {
        try 
        { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
        } 
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){ 
        }
    }
    
    public static void setToMiddle(Component myComponent) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        myComponent.setLocation(dim.width/2-myComponent.getSize().width/2, dim.height/2-myComponent.getSize().height/2);
    }
            
    public static void main(String [] args){
        changeLookAndFeel();
        Analyzer analyser=new Analyzer();
    }
}
