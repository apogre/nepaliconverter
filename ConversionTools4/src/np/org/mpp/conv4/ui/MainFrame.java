package np.org.mpp.conv4.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.*;

public class MainFrame extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  ImageIcon image_f2u = new ImageIcon(np.org.mpp.conv4.ui.MainFrame.class.
                                      getResource("f2u.png"));
  ImageIcon image_u2f = new ImageIcon(np.org.mpp.conv4.ui.MainFrame.class.
                                      getResource("u2f.png"));
  ImageIcon image_tranlit = new ImageIcon(np.org.mpp.conv4.ui.MainFrame.class.
                                          getResource("help.png"));
  JLabel statusBar = new JLabel();
  JPanel jPanelCards = new JPanel();
  CardLayout cardLayout1 = new CardLayout();
  JPanel introPanel = new JPanel();
  JTextPane jTextPane1 = new JTextPane();
  JButton jButtonConvUnic = new JButton();
  JButton jButtonConvFont = new JButton();
  JButton jButtonTranslit = new JButton();

  /**
   * Component initialization.
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    contentPane = (JPanel) getContentPane();
    contentPane.setLayout(borderLayout1);
    setSize(new Dimension(600, 400));
    setTitle("Conversion tools -  Madan Puraskar Pustakalaya");
    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new Frame_jMenuFileExit_ActionAdapter(this));
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jPanelCards.setLayout(cardLayout1);
    jTextPane1.setContentType("text/html");
    jTextPane1.setEditable(false);
    jTextPane1.setText(
        "<html><body>Welcome to MPP\'s converter.<br>Please choose which type " +
        "of conversion you would like to to:");
    jButtonConvUnic.setIcon(image_f2u);
    jButtonConvUnic.setText("<html><body>Convert from<br>non-Unicode font<br>to <b>Unicode</b>");
    jButtonConvUnic.addActionListener(new
                                      Frame_jButtonConvUnic_actionAdapter(this));
    jButtonConvFont.setIcon(image_u2f);
    jButtonConvFont.setText(
        "<html><body>Convert from Unicode<br>to non-Unicode<br><b>font</b> (like Preeti)");
    jButtonConvFont.addActionListener(new
                                      Frame_jButtonConvFont_actionAdapter(this));
    jButtonTranslit.setIcon(image_tranlit);
    jButtonTranslit.setText(
        "<html><body>Convert from<br>devanagari to <br><b>roman</b> transliteration");
    jButtonTranslit.addActionListener(new
                                      Frame_jButtonTranslit_actionAdapter(this));
    introPanel.setLayout(gridLayout1);
    gridLayout1.setColumns(1);
    gridLayout1.setRows(0);
    jMenuBar1.add(jMenuFile);
    jMenuFile.add(jMenuFileExit);
    jMenuBar1.add(jMenuHelp);
    jMenuHelp.add(jMenuHelpAbout);
    setJMenuBar(jMenuBar1);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(jPanelCards, java.awt.BorderLayout.CENTER);
    jPanelCards.add(introPanel, "introPanel");
    introPanel.add(jTextPane1);
    introPanel.add(jButtonConvUnic);

    introPanel.add(jButtonConvFont);
    introPanel.add(jButtonTranslit);
    jPanelCards.add(converterPanelUnic, "converterPanelUnic");
    jPanelCards.add(converterPanelFont, "converterPanelFont");
    jPanelCards.add(converterPanelTranslit, "converterPanelTranslit");
  }


  ConverterPanel converterPanelUnic = new ConverterPanel();
  ConverterPanel converterPanelFont = new ConverterPanel();
  ConverterPanel converterPanelTranslit = new ConverterPanel();

  static MainFrame instance = null;
  GridLayout gridLayout1 = new GridLayout();

  public MainFrame() {
    try {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    instance = this;
  }


  /**
   * File | Exit action performed.
   *
   * @param actionEvent ActionEvent
   */
  void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
    System.exit(0);
  }


  public void jButtonConvUnic_actionPerformed(ActionEvent e) {
    converterPanelUnic.jLabelConvTitle.setText("Conversion to Unicode");
    cardLayout1.show(jPanelCards, "converterPanelUnic");
  }

  public void jButtonConvFont_actionPerformed(ActionEvent e) {
    converterPanelFont.jLabelConvTitle.setText("Conversion to non-Unicode font");
    cardLayout1.show(jPanelCards, "converterPanelFont");
  }

  public void jButtonTranslit_actionPerformed(ActionEvent e) {
    converterPanelTranslit.jLabelConvTitle.setText("Devanagari transliteration to roman letters");
    cardLayout1.show(jPanelCards, "converterPanelTranslit");
  }
}


class Frame_jButtonTranslit_actionAdapter implements ActionListener {
  private MainFrame adaptee;
  Frame_jButtonTranslit_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonTranslit_actionPerformed(e);
  }
}


class Frame_jButtonConvFont_actionAdapter implements ActionListener {
  private MainFrame adaptee;
  Frame_jButtonConvFont_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonConvFont_actionPerformed(e);
  }
}


class Frame_jButtonConvUnic_actionAdapter implements ActionListener {
  private MainFrame adaptee;
  Frame_jButtonConvUnic_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonConvUnic_actionPerformed(e);
  }
}


class Frame_jMenuFileExit_ActionAdapter implements ActionListener {
  MainFrame adaptee;

  Frame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jMenuFileExit_actionPerformed(actionEvent);
  }
}
