package analyzer;

import java.awt.Dimension;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author rojan
 */
public class LoginF extends javax.swing.JFrame {

    /**
     * Creates new form LoginF
     */
    private void changeBackground() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("photo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(1150,500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        setContentPane(new JLabel(imageIcon));
    }
    
    public LoginF() {
        //changeBackground();
        setTitle("Transcript Analyzer");
        setVisible(true);
        setSize(1150,500);
        Analyzer.setToMiddle(this);
        initComponents();
    }
    
    public void RankFrame(){
        if(transcriptT.getText().compareTo("") == 0){
            String error = "No transcript selected";
            MessageFrame(error);
        }
        else{
        RankFrame.setTitle("Student Ranks");
        RankFrame.setVisible(true);
        RankFrame.setSize(400,200);
        Analyzer.setToMiddle(RankFrame);
        DefaultListModel model = new DefaultListModel();
        TranscriptList testList = TranscriptFileReader.readTranscriptsFromFolder(transcriptT.getText());
               
        Map<String, Integer> studentsPerYear = testList.getStudentsPerYear();
        ranks.setModel(model);
              
        for (String year : studentsPerYear.keySet()) {
                model.addElement(year + ": " + studentsPerYear.get(year)); 
        }
    }
    }
    
    public void TranscriptListFrame(){
        
        if(transcriptT.getText().compareTo("") == 0){
            String error = "No transcript selected";
            MessageFrame(error);
        }
        else{
        TranscriptListFrame.getContentPane().setBackground(Color.pink);
        TranscriptListFrame.setVisible(true);
        TranscriptListFrame.setSize(650,300);
        Analyzer.setToMiddle(TranscriptListFrame);
        DefaultListModel model = new DefaultListModel();
        TranscriptList tList = TranscriptFileReader.readTranscriptsFromFolder(transcriptT.getText());
        System.out.println("hello");
        tl.setModel(model);
        Set<String> set = tList.getAllCourseNames();
        ArrayList<String> list = new ArrayList<>(set);
        Collections.sort(list);
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
             model.addElement(iter.next().toString());
        }
        } 
 }
    
     public void MessageFrame(String e){
        MessageFrame.setVisible(true);
        MessageFrame.setSize(650,300);
        Analyzer.setToMiddle(MessageFrame);
        message.setText(e);
     }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TranscriptListFrame = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tl = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        RankFrame = new javax.swing.JFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        ranks = new javax.swing.JList<>();
        ranks.setPreferredSize(new Dimension(120, 130));
        MessageFrame = new javax.swing.JFrame();
        message = new javax.swing.JTextField();
        message.setEditable(false);
        inputT = new javax.swing.JTextField();
        inputT.setEditable(false);
        areasB = new javax.swing.JButton();
        getOutputsT = new javax.swing.JButton();
        getMasterB = new javax.swing.JButton();
        transcriptT = new javax.swing.JTextField();
        transcriptT.setEditable(false);
        transcriptB = new javax.swing.JButton();
        gradeLevelsT = new javax.swing.JTextField();
        gradeLevelsT.setText(new File("./level-schema.txt").getAbsolutePath());
        GradeSchema.SetGradeSchema(gradeLevelsT.getText());
        gradeLevelsT.setEditable(false);
        gradeLevelsB = new javax.swing.JButton();
        rankB = new javax.swing.JButton();

        jScrollPane1.setViewportView(tl);

        jLabel1.setText("Master Course List");

        javax.swing.GroupLayout TranscriptListFrameLayout = new javax.swing.GroupLayout(TranscriptListFrame.getContentPane());
        TranscriptListFrame.getContentPane().setLayout(TranscriptListFrameLayout);
        TranscriptListFrameLayout.setHorizontalGroup(
            TranscriptListFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TranscriptListFrameLayout.createSequentialGroup()
                .addGroup(TranscriptListFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TranscriptListFrameLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TranscriptListFrameLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel1)))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        TranscriptListFrameLayout.setVerticalGroup(
            TranscriptListFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TranscriptListFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(ranks);

        javax.swing.GroupLayout RankFrameLayout = new javax.swing.GroupLayout(RankFrame.getContentPane());
        RankFrame.getContentPane().setLayout(RankFrameLayout);
        RankFrameLayout.setHorizontalGroup(
            RankFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RankFrameLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );
        RankFrameLayout.setVerticalGroup(
            RankFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RankFrameLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MessageFrameLayout = new javax.swing.GroupLayout(MessageFrame.getContentPane());
        MessageFrame.getContentPane().setLayout(MessageFrameLayout);
        MessageFrameLayout.setHorizontalGroup(
            MessageFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessageFrameLayout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        MessageFrameLayout.setVerticalGroup(
            MessageFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessageFrameLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inputT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTActionPerformed(evt);
            }
        });

        areasB.setText("Attach Area  Schema and Equivalency Schema");
        areasB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                areasBActionPerformed(evt);
            }
        });

        getOutputsT.setText("Get Area and Raw Distributions");
        getOutputsT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getOutputsTActionPerformed(evt);
            }
        });

        getMasterB.setText("Get Master Course List");
        getMasterB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getMasterBActionPerformed(evt);
            }
        });

        transcriptB.setText("Set Transcript Folder Path");
        transcriptB.setActionCommand("Attach Trancript(s)");
        transcriptB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transcriptBActionPerformed(evt);
            }
        });

        gradeLevelsT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeLevelsTActionPerformed(evt);
            }
        });

        gradeLevelsB.setText("Reset Grade Level Schema");
        gradeLevelsB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeLevelsBActionPerformed(evt);
            }
        });

        rankB.setText("Get Rank Schema");
        rankB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(gradeLevelsB)
                        .addGap(33, 33, 33)
                        .addComponent(gradeLevelsT, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(transcriptB)
                            .addComponent(areasB)
                            .addComponent(getMasterB))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(getOutputsT)
                                .addGap(57, 57, 57)
                                .addComponent(rankB))
                            .addComponent(transcriptT, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputT, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(areasB)
                    .addComponent(inputT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(transcriptB)
                    .addComponent(transcriptT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getOutputsT)
                    .addComponent(getMasterB)
                    .addComponent(rankB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gradeLevelsB)
                    .addComponent(gradeLevelsT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void transcriptBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transcriptBActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        transcriptT.setText(filename);
    }//GEN-LAST:event_transcriptBActionPerformed

    private void areasBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_areasBActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        inputT.setText(filename);
    }//GEN-LAST:event_areasBActionPerformed

    private void rankBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankBActionPerformed
        RankFrame();
    }//GEN-LAST:event_rankBActionPerformed

    private void gradeLevelsTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeLevelsTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gradeLevelsTActionPerformed

    private void inputTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputTActionPerformed

    private void gradeLevelsBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeLevelsBActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        gradeLevelsT.setText(filename);
        GradeSchema.SetGradeSchema(gradeLevelsT.getText());
        MessageFrame("done");
    }//GEN-LAST:event_gradeLevelsBActionPerformed

    private void getMasterBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getMasterBActionPerformed
        TranscriptListFrame();
        BufferedWriter bw = null;
	    FileWriter fw = null;

		try {

			fw = new FileWriter("Master Course List");
			bw = new BufferedWriter(fw);
		
        if(transcriptT.getText().compareTo("") != 0){ 
        TranscriptList tList = TranscriptFileReader.readTranscriptsFromFolder(transcriptT.getText());
        Set<String> set = tList.getAllCourseNames();
        List list = new ArrayList(set);
        Collections.sort(list);
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
             bw.write(iter.next().toString());
             bw.write("\n");
        }
        } 
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }//GEN-LAST:event_getMasterBActionPerformed

    private void getOutputsTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getOutputsTActionPerformed
        //call methods to read and write to excel  
        EquivalencySchema.setEquivalenciesFromFile(inputT.getText());
        ExcelParser.readExcelArea(inputT.getText());
        TranscriptList list = TranscriptFileReader.readTranscriptsFromFolder(transcriptT.getText());
        AreaDistribution areaDist = new AreaDistribution(list);
        RawDistribution rawDist = new RawDistribution(list);
        ExcelParser.parse(rawDist, areaDist, inputT.getText());
    }//GEN-LAST:event_getOutputsTActionPerformed

    /**
     * @param args the command line arguments
     */
    
    
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LoginF().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame MessageFrame;
    private javax.swing.JFrame RankFrame;
    private javax.swing.JFrame TranscriptListFrame;
    private javax.swing.JButton areasB;
    private javax.swing.JButton getMasterB;
    private javax.swing.JButton getOutputsT;
    private javax.swing.JButton gradeLevelsB;
    private javax.swing.JTextField gradeLevelsT;
    private javax.swing.JTextField inputT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField message;
    private javax.swing.JButton rankB;
    private javax.swing.JList<String> ranks;
    private javax.swing.JList<String> tl;
    private javax.swing.JButton transcriptB;
    private javax.swing.JTextField transcriptT;
    // End of variables declaration//GEN-END:variables

    
}
