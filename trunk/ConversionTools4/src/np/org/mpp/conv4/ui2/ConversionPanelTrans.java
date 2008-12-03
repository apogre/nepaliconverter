/*
 * ConversionPanelTrans.java
 *
 * Created on December 3, 2008, 1:40 PM
 */

package np.org.mpp.conv4.ui2;

/**
 *
 * @author  j
 */
public class ConversionPanelTrans extends ConversionPanel {

    /** Creates new form ConversionPanelTrans */
    public ConversionPanelTrans() {
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
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel2 = new javax.swing.JPanel();
    jSplitPane1 = new javax.swing.JSplitPane();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTextArea2 = new javax.swing.JTextArea();
    jPanel1 = new javax.swing.JPanel();
    jButtonOpenFile = new javax.swing.JButton();
    jLabelSelectedFiles = new javax.swing.JLabel();
    jButtonRunConversion = new javax.swing.JButton();
    jComboBoxTransliterationScheme = new javax.swing.JComboBox();
    jCheckBoxMakeExceptions = new javax.swing.JCheckBox();

    jLabelInfo.setText("<html><body>Transliterate Devanagari text to roman letters.<br><b>Note:</b> the text must be in Unicode");

    jSplitPane1.setDividerLocation(150);
    jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

    jTextArea1.setColumns(20);
    jTextArea1.setRows(5);
    jTextArea1.setText("Type in or paste text here...\nक ख ग घ");
    jScrollPane1.setViewportView(jTextArea1);

    jSplitPane1.setTopComponent(jScrollPane1);

    jTextArea2.setColumns(20);
    jTextArea2.setEditable(false);
    jTextArea2.setRows(5);
    jTextArea2.setText("Transliteration will come here....\nka kha ga gha");
    jScrollPane2.setViewportView(jTextArea2);

    jSplitPane1.setRightComponent(jScrollPane2);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(45, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("Clipboard conversion", jPanel2);

    jButtonOpenFile.setText("Choose file to convert...");
    jButtonOpenFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonOpenFileActionPerformed(evt);
      }
    });

    jLabelSelectedFiles.setText("jLabelSelectedFiles");

    jButtonRunConversion.setText("Do conversion");
    jButtonRunConversion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonRunConversionActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jButtonOpenFile)
          .addComponent(jLabelSelectedFiles)
          .addComponent(jButtonRunConversion))
        .addContainerGap(359, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jButtonOpenFile)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabelSelectedFiles)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonRunConversion)
        .addContainerGap(229, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("File conversion", jPanel1);

    jComboBoxTransliterationScheme.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    jCheckBoxMakeExceptions.setText("<html><body>Make<br>exceptions");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabelInfo)
            .addGap(105, 105, 105)
            .addComponent(jComboBoxTransliterationScheme, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jCheckBoxMakeExceptions)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabelInfo)
            .addGap(10, 10, 10))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jCheckBoxMakeExceptions)
              .addComponent(jComboBoxTransliterationScheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        .addGap(12, 12, 12))
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
  private javax.swing.JCheckBox jCheckBoxMakeExceptions;
  private javax.swing.JComboBox jComboBoxTransliterationScheme;
  private javax.swing.JLabel jLabelInfo;
  private javax.swing.JLabel jLabelSelectedFiles;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JSplitPane jSplitPane1;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTextArea jTextArea1;
  private javax.swing.JTextArea jTextArea2;
  // End of variables declaration//GEN-END:variables

}