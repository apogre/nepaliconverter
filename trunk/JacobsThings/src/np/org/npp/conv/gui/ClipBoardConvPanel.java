package np.org.npp.conv.gui;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import np.org.mpp.old.old_f2u.*;
import np.org.npp.conv.file.*;

public class ClipBoardConvPanel
    extends JPanel {
  public ClipBoardConvPanel() {
    try {
      jbInit();
      //jEditorPane1.setContentType("text/html; charset=EUC-JP");
      //jEditorPane1.setText("<html>Hej med <b>dig!</b><br>text/html");

/*
      jEditorPane1.setContentType("text/html");
      jEditorPane1.setText(
	  "<html>\n  <head>\n    \n  </head>\n  <body>\n    Hej med <b>dig!</b><br>html\n" +
	  "  </body>\n</html>\n");
*/
      //jEditorPane1.setContentType("text/rtf");
      //jEditorPane1.setPage(new File("vorto03.html").toURL());

      //javax.swing.text.rtf.RTFEditorKit k;
      //k.getContentType();


      //((AbstractDocument) jEditorPane1.getDocument()).setAsynchronousLoadPriority(-1);

      Document d0 = jEditorPane1.getDocument();
      System.err.println(""+d0);
      System.err.println(""+d0.getProperty(d0.StreamDescriptionProperty));
      System.err.println(""+d0.getProperty(d0.TitleProperty));


      //jEditorPane1.setPage(new File("/home/local/games/enemy-territory/Docs/License.rtf").toURL());

      jEditorPane1.setPage(new File("MS-nepali.html").toURL());

      d0 = jEditorPane1.getDocument();
      System.err.println(""+d0);
      System.err.println(""+d0.getProperty(d0.StreamDescriptionProperty));
      System.err.println(""+d0.getProperty(d0.TitleProperty));

      //jEditorPane1.setPage(new File("/home/j/esperanto/nepala vortaro/vorto03delete.html").toURL());


      //jButtonToUnicode_actionPerformed(null);
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.setPreferredSize(new Dimension(800, 600));
    jButtonToUnicode.setText("Convert to Unicode");
    jButtonToUnicode.addActionListener(new
	ClipBoardConvPanel_jButtonToUnicode_actionAdapter(this));
    jScrollPane1.getViewport().add(jEditorPane1);
    this.add(jButtonToUnicode, java.awt.BorderLayout.SOUTH);
    this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
  }

  JScrollPane jScrollPane1 = new JScrollPane();
  JEditorPane jEditorPane1 = new JEditorPane();
  JButton jButtonToUnicode = new JButton();

  public void jButtonToUnicode_actionPerformed(ActionEvent e) {
    try {
    Document d0 = jEditorPane1.getDocument();
    System.err.println(""+d0);

    if (d0 instanceof HTMLDocument) {
      HTMLDocument d = (HTMLDocument) d0;
      //d.setAsynchronousLoadPriority(-1);

/*
      HTMLDocument.Iterator it = d.getIterator(HTML.Tag.FONT);
      for (int i=0; i<50 && it.isValid(); it.next()) {
	System.out.println(it.getStartOffset());
	System.out.println(it.getAttributes());
	System.out.println("txt="+d.getText(it.getStartOffset(), it.getEndOffset()-it.getStartOffset()));
	System.out.println(it.getTag());
	System.out.println(it);
      }
*/
    }
    //javax.swing.text.rtf.RTFEditorKit d2;

    Element el = d0.getDefaultRootElement();
      //new SwingHtml32FileConverter().toUnicode(el);
    }


    catch (Exception ex1) {
      ex1.printStackTrace();
    }

  }

  //static Font2Unicode font2Unicode = new Font2Unicode();
  BorderLayout borderLayout1 = new BorderLayout();

}

class ClipBoardConvPanel_jButtonToUnicode_actionAdapter
    implements ActionListener {
  private ClipBoardConvPanel adaptee;
  ClipBoardConvPanel_jButtonToUnicode_actionAdapter(ClipBoardConvPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonToUnicode_actionPerformed(e);
  }
}
