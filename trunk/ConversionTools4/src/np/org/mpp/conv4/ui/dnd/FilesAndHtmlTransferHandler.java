package np.org.mpp.conv4.ui.dnd;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;

import javax.swing.*;
import java.awt.*;



/**
 * A transfer handler for file(s) and HTML
 * We will need a HTML parser/generator.
 *
 * Perhaps
 * http://lobobrowser.org/cobra.jsp or
 * http://htmlparser.sourceforge.net/
 * <p>Title: Nepaliconverter</p>
 *
 * <p>Description: Conversion tools for Nepali and devanagari text</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: Madan Puraskar Pustakalaya</p>
 *
 * @author Jacob Nordfalk
 * @version 4.0
 */

public class FilesAndHtmlTransferHandler extends TransferHandler {
  public JEditorPane console;

  public DataFlavor fileDf, htmlDf;

  public FilesAndHtmlTransferHandler() {
      try {
          fileDf = new DataFlavor("text/uri-list; class=java.lang.String");
          htmlDf = new DataFlavor("text/html; class=java.lang.String"); // charset=unicode;
      } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
      }
  }

  public static void main(String[] args) {
    FilesAndHtmlTransferHandler th = new FilesAndHtmlTransferHandler();

    // Observes the selection (copy/cut in text documents detected)
    new ClipboardObserver(Toolkit.getDefaultToolkit().getSystemClipboard(), th.htmlDf);

    // Observes the selection (also without copy)
    new ClipboardObserver(Toolkit.getDefaultToolkit().getSystemSelection(), DataFlavor.stringFlavor);
    th.console = new JEditorPane("text/plain", "drag files/html here");
    th.console.setPreferredSize(new Dimension(600,200));
    th.console.setTransferHandler(th);
    JOptionPane.showConfirmDialog(null, new JScrollPane( th.console));
  }


  public boolean canImport(JComponent arg0, DataFlavor[] arg1) {

//    public boolean canImport(TransferHandler.TransferSupport support) {

    return true;
  }



  public boolean importData(JComponent comp, Transferable t) {


    try {

        if (t.isDataFlavorSupported(fileDf)) {
            String s = (String) t.getTransferData(fileDf);
            System.out.println("s = " + s);
            console.setText("FILES\n"+s);
            return true;
        }


        if (t.isDataFlavorSupported(htmlDf)) {
            //status.append("HRML"+htmlDf);
            String s = (String) t.getTransferData(htmlDf);
            System.out.println("s = " + s);
            console.setText("HTML\n"+s);
            return true;
        }


    } catch (Exception ex) {
        ex.printStackTrace();
    }



    StringBuffer status = new StringBuffer();

    logAllDataFlavors(t, status);
    /*
    if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
        status.append("FILE LIST!");
    }

    if (t.isDataFlavorSupported(DataFlavor.getTextPlainUnicodeFlavor())) {
        status.append("TEXT! getTextPlainUnicodeFlavor()");
    }

    if (t.isDataFlavorSupported(DataFlavor.selectBestTextFlavor(t.getTransferDataFlavors()))) {
        status.append("TEXT! selectBestTextFlavor()");
        DataFlavor df = DataFlavor.selectBestTextFlavor(t.getTransferDataFlavors());
        //t.getTransferData(df);
    }
*/



    if (console != null) console.setText(status.toString());
    return true;
  }

  private void logAllDataFlavors(Transferable t, StringBuffer status) {
      //support.getDataFlavors()

      DataFlavor[] fl = t.getTransferDataFlavors();
      //DataFlavor[] flx = support.getDataFlavors();
      //System.err.println("t.getTransferDataFlavors()=" + flx.length);
      System.err.println("support.getDataFlavors()=" + fl.length);

      for (int i = 0; i < fl.length; i++) {
        DataFlavor f = fl[i];

        status.append("f[" + i + "]=" + f.getMimeType() + "\n");

        try {
          System.err.println();
          System.err.println(" f[" + i + "]=" + fl[i].toString() + " " + fl[i].getHumanPresentableName());
          System.err.println("  " + t.getTransferData(fl[i]));
        } catch (Exception e) {
          e.printStackTrace();
        }

        /*
             if (f.getHumanPresentableName().equals("text/uri-list")) {
          try {
            List l = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
            status.append("ITS A FILE LIST: " + l);
          } catch (Exception ex) {
            //ex.printStackTrace();
          }
             }
         */

        if (f.getRepresentationClass() == InputStream.class) {
          try {
            InputStream l = (InputStream) t.getTransferData(f);
            new File("clipboardData").mkdirs();
            FileOutputStream fos = new FileOutputStream("clipboardData/clipboard" + i + "_" + f.getHumanPresentableName().replaceAll("/", ""));
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
  }

}
