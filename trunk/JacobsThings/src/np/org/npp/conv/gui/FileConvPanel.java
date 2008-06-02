package np.org.npp.conv.gui;

import java.beans.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.prefs.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import np.org.npp.conv.string.*;
import np.org.npp.conv.file.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;






public class FileConvPanel extends JPanel {


  JFileChooser jFileChooserSelectInput;

  Preferences prefs = Preferences.userRoot().node("myProgram");




  public FileConverter[] converters = new FileConverter[] { new FileAutodetectConverter(), new TextfileConverter(), new SwingHtml32FileConverter() };



  public FileConvPanel() {
    try {
      jbInit();


      initCharsets(false);

      jTextFieldInFile.setText(prefs.get("inputFile", ""));
      jTextFieldOutFile.setText(prefs.get("outputFile", ""));
      for (Object font : new Font2Unicode().knownFontNames()) jComboInpFont.addItem(font);
      jComboInpFont.setSelectedIndex(0);
      jComboInpFont.setSelectedItem(prefs.get("inputFont", null));

      for (FileConverter c : converters) jComboConvType.addItem(c.name());
      String sel = prefs.get("convType", null);
      if (sel!=null) jComboConvType.setSelectedItem(sel);
      else jComboConvType.setSelectedIndex(0);

    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }



  Vector charsetNames;
  String SHOWALL = "(show all...)";

  public void initCharsets(boolean showAll) {

    if (charsetNames==null) charsetNames = new Vector();

    for (String s : new String[] {"(auto)", Charset.defaultCharset().name(), "UTF-8", "UTF-16", "ISO-8859-1", SHOWALL}) {
      if (!charsetNames.contains(s)) charsetNames.add(s);
    }
    if (showAll) {
      for (String c : Charset.availableCharsets().keySet()) charsetNames.add(c);
      charsetNames.remove(SHOWALL);
    }


    for (int i = 0; i <= 1; i++) {
      JComboBox jc = i==0? jComboInpEnc : jComboOutEnc;
      String sel = (String) jc.getSelectedItem();
      jc.setModel(new DefaultComboBoxModel(charsetNames));
      if (charsetNames.contains(sel)) {
	jc.setSelectedItem(sel);
      } else {
	jc.setSelectedIndex(0);
	String p = prefs.get(i == 0 ? "inpEnc" : "outEnc", null);
	if (p != null) {
	  if (showAll == false && !charsetNames.contains(p)) {
	    initCharsets(true);
	    return;
	  }
	  if (charsetNames.contains(p))
	    jc.setSelectedItem(p);
	}
      }
    }
  }



  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    jLabelInputFile.setText("Input file name");
    jTextFieldInFile.setText("jTextField1");
    jTextFieldInFile.setColumns(19);
    jTextFieldInFile.addVetoableChangeListener(new
	FileConvPanel_jTextFieldInFile_vetoableChangeAdapter(this));
    jButtonBrowseInput.setText("Browse...");
    jButtonBrowseInput.addActionListener(new
	FileConvPanel_jButtonBrowseInput_actionAdapter(this));
    jLabelInputEnc.setText("Input Encoding");
    jLabelInputFont.setText("Input Font");
    jLabelConvType.setText("Conversion type");
    jLabelOutEnc.setText("Output Encoding");
    jLabelOutFile.setText("Output file name");
    jTextFieldOutFile.setText("jTextField2");
    jButtonConvert.setText("Convert!");
    jButtonConvert.addActionListener(new
				     FileConvPanel_jButtonConvert_actionAdapter(this));
    jCheckBoxPreviewInp.setSelected(true);
    jCheckBoxPreviewInp.setText("Preview");
    jCheckBoxPreviewInp.addActionListener(new
	FileConvPanel_jCheckBoxPreviewInp_actionAdapter(this));
    jTextPanePreview.setFont(new java.awt.Font("Dialog", Font.PLAIN, 24));
    jTextPanePreview.setText("jTextPane1");
    jScrollPane1.setPreferredSize(new Dimension(600, 400));
    jComboInpFont.addActionListener(new
				    FileConvPanel_jComboInpFont_actionAdapter(this));
    jComboConvType.addActionListener(new
				     FileConvPanel_jComboConvType_actionAdapter(this));
    jComboInpEnc.addActionListener(new FileConvPanel_jComboInpEnc_actionAdapter(this));

    this.add(jLabelInputFile, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	, GridBagConstraints.WEST, GridBagConstraints.NONE,
	new Insets(0, 0, 0, 0), 0, 0));

    this.add(jComboInpEnc, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
						  , GridBagConstraints.CENTER,
						  GridBagConstraints.BOTH,
						  new Insets(0, 0, 0, 0), 0, 0));
    this.add(jComboInpFont, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
						, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));

    this.add(jLabelConvType, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
						 , GridBagConstraints.WEST,
						 GridBagConstraints.NONE,
						 new Insets(0, 0, 0, 0), 0, 0));

 this.add(jComboConvType, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
						 , GridBagConstraints.CENTER,
						 GridBagConstraints.BOTH,
						 new Insets(0, 0, 0, 0), 0, 0));

 this.add(jLabelInputFont, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
     , GridBagConstraints.WEST, GridBagConstraints.NONE,
     new Insets(0, 0, 0, 0), 0, 0));

    this.add(jComboOutEnc, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
						, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabelOutFile, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
	, GridBagConstraints.WEST, GridBagConstraints.NONE,
	new Insets(0, 0, 0, 0), 0, 0));

    this.add(jTextFieldOutFile, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0
						 , GridBagConstraints.CENTER,
						 GridBagConstraints.BOTH,
						 new Insets(0, 0, 0, 0), 0, 0));
    this.add(jTextFieldInFile, new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0
						 , GridBagConstraints.WEST,
						 GridBagConstraints.BOTH,
						 new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabelOutEnc, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
						  , GridBagConstraints.CENTER,
						  GridBagConstraints.NONE,
						  new Insets(0, 0, 0, 10), 0, 0));
    this.add(jButtonBrowseInput, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
	, GridBagConstraints.EAST, GridBagConstraints.NONE,
	new Insets(0, 10, 0, 0), 0, 0));
    this.add(jCheckBoxPreviewInp, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
	, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
	new Insets(0, 0, 0, 0), 0, 0));
    this.add(jButtonConvert, new GridBagConstraints(2, 5, 1, 2, 0.0, 0.0
	, GridBagConstraints.EAST, GridBagConstraints.NONE,
	new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabelInputEnc, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	, GridBagConstraints.WEST, GridBagConstraints.NONE,
	new Insets(0, 0, 0, 0), 0, 0));
    this.add(jScrollPane1, new GridBagConstraints(0, 7, 3, 1, 1.0, 0.3
						  , GridBagConstraints.CENTER,
						  GridBagConstraints.BOTH,
						  new Insets(0, 0, 0, 0), 0, 0));
    jScrollPane1.getViewport().add(jTextPanePreview);
  }



  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabelInputFile = new JLabel();
  JTextField jTextFieldInFile = new JTextField();
  JButton jButtonBrowseInput = new JButton();
  JLabel jLabelInputEnc = new JLabel();
  JComboBox jComboInpEnc = new JComboBox();
  JLabel jLabelInputFont = new JLabel();
  JComboBox jComboInpFont = new JComboBox();
  JLabel jLabelConvType = new JLabel();
  JComboBox jComboConvType = new JComboBox();
  JLabel jLabelOutEnc = new JLabel();
  JComboBox jComboOutEnc = new JComboBox();
  JLabel jLabelOutFile = new JLabel();
  JTextField jTextFieldOutFile = new JTextField();
  JButton jButtonConvert = new JButton();
  JCheckBox jCheckBoxPreviewInp = new JCheckBox();
  JFileChooser jFileChooser1 = new JFileChooser();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextPane jTextPanePreview = new JTextPane();

  public void jButtonBrowseInput_actionPerformed(ActionEvent actionEvent) {
    //private

    if (jFileChooserSelectInput == null) {
      jFileChooserSelectInput = new JFileChooser(jTextFieldInFile.getText());
      //jFileChooserSelectInput.setFileView(new javax.swing.plaf.basic.BasicFileChooserUI.BasicFileView());
    }

    if (JFileChooser.APPROVE_OPTION == jFileChooserSelectInput.showOpenDialog(this)) {
      File f = jFileChooserSelectInput.getSelectedFile();
      String fs = f.getPath();
      jTextFieldInFile.setText(fs);


      int n = fs.lastIndexOf('.');
      fs = fs.substring(0,n-1) + "-unicode" + fs.substring(n);

      jTextFieldOutFile.setText(fs);


    }
  }




  public void jButtonConvert_actionPerformed(ActionEvent e) {
    prefs.put("inpEnc", (String)jComboInpEnc.getSelectedItem());
    prefs.put("outEnc", (String)jComboOutEnc.getSelectedItem());
    prefs.put("outputFile", jTextFieldOutFile.getText());
    prefs.put("inputFile", jTextFieldInFile.getText());
    prefs.put("inputFont", (String)jComboInpFont.getSelectedItem());
    prefs.put("convType", (String)jComboConvType.getSelectedItem());
  }





  public void updatePreviewNow() {
    System.err.println("updatePreviewNoe() "+jCheckBoxPreviewInp.isSelected());

    String convType = (String) jComboConvType.getSelectedItem();

    FileConverter c = null;
    for (FileConverter c2 : converters) if (c2.name().equals(convType)) c = c2;
    if (c==null) throw new IllegalStateException("Unknown coverter "+jComboConvType.getSelectedItem());

    convType = convType.toLowerCase();

    jComboInpFont.setEnabled(convType.startsWith("text"));
    jLabelInputFont.setEnabled(convType.startsWith("text"));


    if (!this.jCheckBoxPreviewInp.isSelected()) {
      this.jTextPanePreview.setText("");
      return;
    }

    try {

      c.font2Unicode.interactive = false;

      String inEncs = (String)jComboInpEnc.getSelectedItem();
      if (inEncs.equals("(default)")) c.p.inEnc = Charset.defaultCharset();
      else if (inEncs.equals("(auto)")) c.p.inEnc = null;
      else c.p.inEnc = Charset.forName(inEncs);


      c.p.inFile = jTextFieldInFile.getText();
      c.p.inFont = (String)jComboInpFont.getSelectedItem();
      c.p.maxLen = 5000;
      c.convertCheckCache();
      String txt = c.lastConversionRes;

      System.out.println(txt);
      this.jTextPanePreview.setText(txt);
      this.jTextPanePreview.setCaretPosition(0);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      this.jTextPanePreview.setText("[" + ex.getLocalizedMessage() + "]");
    }
  }

  private Runnable updatePreview = new Runnable() {
    public void run() {
      updatePreviewNow();
    }
  };

  public void updatePreview() {
    System.err.println("updatePreview()");
    SwingUtilities.invokeLater(updatePreview);
  }

  public void jComboInpEnc_actionPerformed(ActionEvent e) {
    if (SHOWALL.equals(((JComboBox) e.getSource()).getSelectedItem())) {
      initCharsets(true);
    } else
      updatePreview();
  }

}

class FileConvPanel_jComboInpEnc_actionAdapter
    implements ActionListener {
  private FileConvPanel adaptee;
  FileConvPanel_jComboInpEnc_actionAdapter(FileConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jComboInpEnc_actionPerformed(e);
  }
}

class FileConvPanel_jComboConvType_actionAdapter
    implements ActionListener {
  private FileConvPanel adaptee;
  FileConvPanel_jComboConvType_actionAdapter(FileConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.updatePreview();
  }
}

class FileConvPanel_jComboInpFont_actionAdapter
    implements ActionListener {
  private FileConvPanel adaptee;
  FileConvPanel_jComboInpFont_actionAdapter(FileConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.updatePreview();
  }
}

class FileConvPanel_jCheckBoxPreviewInp_actionAdapter
    implements ActionListener {
  private FileConvPanel adaptee;
  FileConvPanel_jCheckBoxPreviewInp_actionAdapter(FileConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.updatePreview();
  }
}

class FileConvPanel_jTextFieldInFile_vetoableChangeAdapter
    implements VetoableChangeListener {
  private FileConvPanel adaptee;
  FileConvPanel_jTextFieldInFile_vetoableChangeAdapter(FileConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void vetoableChange(PropertyChangeEvent evt) {
    adaptee.updatePreview();
  }
}

class FileConvPanel_jButtonConvert_actionAdapter
    implements ActionListener {
  private FileConvPanel adaptee;
  FileConvPanel_jButtonConvert_actionAdapter(FileConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonConvert_actionPerformed(e);
  }
}

class FileConvPanel_jButtonBrowseInput_actionAdapter
    implements ActionListener {
  private FileConvPanel adaptee;
  FileConvPanel_jButtonBrowseInput_actionAdapter(FileConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jButtonBrowseInput_actionPerformed(actionEvent);
  }
}
