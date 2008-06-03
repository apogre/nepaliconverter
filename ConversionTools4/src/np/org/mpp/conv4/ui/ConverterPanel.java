package np.org.mpp.conv4.ui;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.List;
import java.io.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.nio.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Vector;

public class ConverterPanel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel jLabelConvTitle = new JLabel();
  JSplitPane jSplitPane1 = new JSplitPane();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JButton jButtonPaste = new JButton();
  JLabel jLabel2 = new JLabel();
  JButton jButton2 = new JButton();
  JCheckBox jCheckBoxAutoconvert = new JCheckBox();
  JTextPane statusText = new JTextPane();
  JButton jButtonBack = new JButton();

  ImageIcon imagePaste = new ImageIcon(np.org.mpp.conv4.ui.ConverterPanel.class.
                                       getResource("edit-paste.png"));
  ImageIcon imageBack = new ImageIcon(np.org.mpp.conv4.ui.ConverterPanel.class.
                                      getResource("back.png"));


  public ConverterPanel() {
    try {
      jbInit();
      statusText.setTransferHandler(transferhandler);
      this.setTransferHandler(transferhandler);

      ActionMap map = getActionMap();
      map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
              TransferHandler.getPasteAction());

      InputMap imap = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);
      imap.put(KeyStroke.getKeyStroke("ctrl V"),
               TransferHandler.getPasteAction().getValue(Action.NAME));
      this.setInputMap(WHEN_IN_FOCUSED_WINDOW, imap);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jLabelConvTitle.setFont(new java.awt.Font("SansSerif", Font.PLAIN, 24));
    jLabelConvTitle.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelConvTitle.setText("Conversion to Unicode");
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setOneTouchExpandable(true);
    jPanel1.setLayout(gridBagLayout1);
    jLabel1.setText("Drag and drop files or text here, or");
    jButtonPaste.setActionCommand("jButtonPaste");
    jButtonPaste.setIcon(imagePaste);
    jButtonPaste.setMnemonic('V');
    jButtonPaste.setText("Paste from Clipboard");
    jButtonPaste.addActionListener(new ConverterPanel_jButtonPaste_actionAdapter(this));
    jLabel2.setText("(Ctrl-V)");
    jButton2.setActionCommand("jButtonSelectFile");
    jButton2.setText("Select file...");
    jCheckBoxAutoconvert.setText("Automatically convert all clipboard text");
    jCheckBoxAutoconvert.addActionListener(new ConverterPanel_jCheckBoxAutoconvert_actionAdapter(this));
    statusText.setEditable(false);
    statusText.setText("Status text comes here");
    jButtonBack.setIcon(imageBack);
    jButtonBack.setText("Back");
    jButtonBack.addActionListener(new
                                  ConverterPanel_jButtonBack_actionAdapter(this));
    this.add(jLabelConvTitle, java.awt.BorderLayout.NORTH);
    this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
    jPanel1.add(jButtonPaste, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonBack, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButton2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jCheckBoxAutoconvert, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jSplitPane1.add(jScrollPane1, JSplitPane.BOTTOM);
    jScrollPane1.getViewport().add(statusText);
    jSplitPane1.add(jPanel1, JSplitPane.TOP);
    jSplitPane1.setDividerLocation(100);
  }

  public void jButtonBack_actionPerformed(ActionEvent e) {
    MainFrame.instance.cardLayout1.show(MainFrame.instance.jPanelCards, "introPanel");
  }



  private TransferHandler transferhandler = new TransferHandler() {
      public boolean canImport(JComponent arg0, DataFlavor[] arg1) {

//    public boolean canImport(TransferHandler.TransferSupport support) {

      return true;
    }


    public boolean importData(JComponent comp, Transferable t) {
    //public boolean importData(TransferHandler.TransferSupport support) {
    //Transferable t = support.getTransferable();


      StringBuffer status = new StringBuffer();

      //support.getDataFlavors()

      DataFlavor[] fl = t.getTransferDataFlavors();
      //DataFlavor[] flx = support.getDataFlavors();
      //System.err.println("t.getTransferDataFlavors()=" + flx.length);
      System.err.println("support.getDataFlavors()=" + fl.length);

      for (int i = 0; i < fl.length; i++) {
        DataFlavor f = fl[i];

        status.append("f[" + i + "]=" + f.getMimeType() + "\n");

        try {
          System.err.println(" f[" + i + "]=" + fl[i].toString() + " " + fl[i].getHumanPresentableName());
          System.err.println("  " + t.getTransferData(fl[i]));
        } catch (Exception e) {
          e.printStackTrace();
        }

        if (f.getHumanPresentableName().equals("text/uri-list")) {
          try {
            List l = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
            status.append("ITS A FILE LIST: " + l);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }

        if (f.getRepresentationClass() == InputStream.class) {
          try {
            InputStream l = (InputStream) t.getTransferData(f);
            new File("clipboardData").mkdirs();
            FileOutputStream fos = new FileOutputStream("clipboardData/clipboard"+i+"_"+f.getHumanPresentableName().replaceAll("/",""));
            int j;
            while ((j = l.read()) != -1) {
                fos.write(j);
              System.err.print((char) j);
            }
            System.err.println();
            fos.close();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }

      }

      for (int i = 0; i < fl.length; i++) {
        DataFlavor f = fl[i];

        /*
                 if (f.getHumanPresentableName().equals("text/html") && f.getRepresentationClass() == String.class) {
          try {
            Object l = t.getTransferData(f);
            status.append("ITS A TEXT: " + l);
            break;
          } catch (Exception ex) {
            ex.printStackTrace();
          }
                 }
         */
      }

      statusText.setText(status.toString());
      return true;
    }
  };
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JScrollPane jScrollPane1 = new JScrollPane();

  public void jButtonPaste_actionPerformed(ActionEvent e) {
    System.out.println("Paste");
  }

  public void jCheckBoxAutoconvert_actionPerformed(ActionEvent e) {
    System.err.println(Toolkit.getDefaultToolkit().getSystemClipboard());
    System.err.println(Toolkit.getDefaultToolkit().getSystemSelection());
    Toolkit.getDefaultToolkit().getSystemClipboard();
  }


}


class ConverterPanel_jCheckBoxAutoconvert_actionAdapter implements ActionListener {
  private ConverterPanel adaptee;
  ConverterPanel_jCheckBoxAutoconvert_actionAdapter(ConverterPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxAutoconvert_actionPerformed(e);
  }
}


class ConverterPanel_jButtonPaste_actionAdapter implements ActionListener {
  private ConverterPanel adaptee;
  ConverterPanel_jButtonPaste_actionAdapter(ConverterPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonPaste_actionPerformed(e);
  }
}


class ConverterPanel_jButtonBack_actionAdapter implements ActionListener {
  private ConverterPanel adaptee;
  ConverterPanel_jButtonBack_actionAdapter(ConverterPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonBack_actionPerformed(e);
  }
}
