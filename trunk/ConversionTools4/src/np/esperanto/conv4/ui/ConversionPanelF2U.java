/*
 * Copyright (C) 2009  Nepala Esperanto-Asocio, http://www.esperanto.org.np/
 * Author: Jacob Nordfalk
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */
/*
 * ConversionPanelF2U.java
 *
 * Created on December 3, 2008, 12:11 PM
 */

package np.esperanto.conv4.ui;

import np.esperanto.conv4.ui.ConversionPanel;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author  j
 */
public class ConversionPanelF2U extends ConversionPanel {

    /** Creates new form ConversionPanelF2U */
    public ConversionPanelF2U() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabelInfo = new javax.swing.JLabel();
    jButtonOpenFile = new javax.swing.JButton();
    jLabelSelectedFiles = new javax.swing.JLabel();
    jButtonRunConversion = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();

    jLabelInfo.setText("<html><body>Convert files with non-Unicode (Preeti, Kantipur ...) font to Unicode.<br><b>Note:</b> Only <a href=\"http://OpenOffice.org\">OpenOffice.org</a> (ODT) files are currently supported. <br>For other formats, you must install OpenOffice, open the files and save them in OpenOffice format.");

    jButtonOpenFile.setText("Choose file to convert...");
    jButtonOpenFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonOpenFileActionPerformed(evt);
      }
    });

    jButtonRunConversion.setText("Do conversion");
    jButtonRunConversion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonRunConversionActionPerformed(evt);
      }
    });

    jLabel3.setText("<html><body> Aŭtoro: Jacob Nordfalk,  Nepala Esperanto-Asocio<br> <font size=\"+2\">अन्तराष्त्रिय भाषल एस्पेरान्तो सिकौ  ...  संसार भरी साथी बनाऔ </font><br> http://www.esperanto.org.np/ ");

    jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+6));
    jLabel1.setText("नेपाल एस्पेरान्तो संघको Conversions Tools");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
          .addComponent(jButtonOpenFile)
          .addComponent(jLabelSelectedFiles)
          .addComponent(jButtonRunConversion)
          .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabelInfo)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonOpenFile)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabelSelectedFiles)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jButtonRunConversion)
        .addGap(171, 171, 171)
        .addComponent(jLabel3)
        .addContainerGap(107, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

private void jButtonOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenFileActionPerformed
// TODO add your handling code here:
  openFileSelection();
  if (inputOutputFiles.isEmpty()) {
    jLabelSelectedFiles.setText("No files selected. Pleace choose a file or dir with OpenOffice.org files");    
  } else {
    String sel = inputOutputFiles.toString();
// Selected these files: {/home/j/Dokumenter/foredrag/oopj_alle2.odp=/home/j/Dokumenter/foredrag_unicode/oopj_alle2.odp, /home/j/Dokumenter/foredrag/oopj_alle7.odp=/home/j/Dokumenter/foredrag_unicode/oopj_alle7.odp, /home/j/Dokumenter/foredrag/oopj_alle12.odp=/home/j/Dokumenter/foredrag_unicode/oopj_alle12.odp, /home/j/Dokumenter/foredrag/oopj_alle14.odp=/home/j/Dokumenter/foredrag_unicode/oopj_alle14.odp}

    sel = sel.substring(1, sel.length()-1).replaceAll(", ", "<br>").replaceAll("=", " -> ");
    jLabelSelectedFiles.setText("<html><body>You chose these files: <br>"+sel);
  }
  jButtonRunConversion.setEnabled(!inputOutputFiles.isEmpty());
}//GEN-LAST:event_jButtonOpenFileActionPerformed

private void jButtonRunConversionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunConversionActionPerformed
// TODO add your handling code here:
  runFileConversion();
}//GEN-LAST:event_jButtonRunConversionActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonOpenFile;
  private javax.swing.JButton jButtonRunConversion;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabelInfo;
  private javax.swing.JLabel jLabelSelectedFiles;
  // End of variables declaration//GEN-END:variables

}