package np.org.mpp.conv4.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.Action;
import javax.swing.border.EtchedBorder;

import np.org.mpp.conv4.ui.dnd.ClipboardObserver;
import np.org.mpp.conv4.ui.dnd.FilesAndHtmlTransferHandler;
import np.org.mpp.ui.WidgetFactory;
import java.io.*;
import java.awt.datatransfer.*;
import np.org.mpp.conv4.ui.dnd.HtmlSelection;

public class ConversionPanel extends JPanel {
  private static final long serialVersionUID = 6407038654026984919L;
  JEditorPane console = new JEditorPane();
  private JScrollPane scroller = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();

  FilesAndHtmlTransferHandler transferhandler = new FilesAndHtmlTransferHandler();
  JCheckBox jCheckBoxObserveAndConvertClipboard = new JCheckBox();

  // Abishek: Please DONT TOUCH this method. Write your code in the constructor
  private void jbInit() throws Exception {
    setLayout(borderLayout1);
    console.setEditable(false);
    console.setText("Please drag and drop file(s) or text, or\r\npress Ctrl+V to paste from " +
                    "clipboard.");

    console.setBackground(Color.WHITE);

    scroller.getViewport().add(console);

    jCheckBoxObserveAndConvertClipboard.setText("Observe and automaticcaly convert clipboard contents");
    jCheckBoxObserveAndConvertClipboard.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jCheckBoxObserveAndConvertClipboard_actionPerformed(e);
      }
    });

    this.add(scroller, java.awt.BorderLayout.CENTER);
    this.add(jCheckBoxObserveAndConvertClipboard, java.awt.BorderLayout.SOUTH);

  }


  public ConversionPanel() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    scroller.setPreferredSize(new Dimension(MainFrame.WIDTH - 10,
                                            MainFrame.HEIGHT - 75));

    console.setPreferredSize(new Dimension(MainFrame.WIDTH - 10,
                                           MainFrame.HEIGHT - 75));
    console.setBorder(new EtchedBorder(2));

    // setBorder(new EmptyBorder(2, 2, 2, 2));

    console.setTransferHandler(transferhandler);

    ActionMap map = console.getActionMap();
    map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
            TransferHandler.getPasteAction());

    InputMap imap = console.getInputMap(WHEN_IN_FOCUSED_WINDOW);
    imap.put(KeyStroke.getKeyStroke("ctrl V"),
             TransferHandler.getPasteAction().getValue(Action.NAME));
    console.setInputMap(WHEN_IN_FOCUSED_WINDOW, imap);

    transferhandler.console = console;

  }

  public void confgureGui(MainFrame mainFrame) {
    mainFrame.toolBar.cmbFont.setVisible(false);
  }


  public void openFileSelection() {
    String filePath = WidgetFactory.getInstance().invokeJFileChooser(this);
    appendLog("You chose to open this file: " + filePath);
  }


  void appendLog(String text) {
    System.out.println(text);
    String t = console.getText();
    t = t + "\n" + text;
    console.setText(t);

  }

  public void runFileConversion() {
    appendLog("runConversion()!");
  }

  public void stopRunningFileConversion() {
    appendLog("stop runConversion()!");
  }

  public void pasteClipboardAction() {
    appendLog("pasteClipboard()");
    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
        //transferhandler.logAllDataFlavors(cb.getContents(this), new StringBuffer());
        String html = transferhandler.getHtml( cb.getData(transferhandler.htmlDfByteUtf8) );

        String newHtml = convertClipboardHtml(html);


        HtmlSelection htmlSel = new HtmlSelection(newHtml);
        cb.setContents(htmlSel, null);

    } catch (UnsupportedFlavorException ex) {
        appendLog("Please select some text");
    } catch (Exception ex) {
        ex.printStackTrace();
    }


  }

    String convertClipboardHtml(String html) {
        appendLog("convertClipboardHtml() not implemented");
        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><HTML><HEAD></HEAD><BODY LANG=\"en\" DIR=\"LTR\">"
            +"<P>Hello to clipboard from Java</P></BODY></HTML>";
    }


    ClipboardObserver systemClipObserver = null;

  public void jCheckBoxObserveAndConvertClipboard_actionPerformed(ActionEvent e) {

    if (jCheckBoxObserveAndConvertClipboard.isSelected()) {
      if (systemClipObserver == null) {
        // Observes the selection (copy/cut in text documents detected)
        systemClipObserver = new ClipboardObserver(Toolkit.getDefaultToolkit().getSystemClipboard(), transferhandler.htmlDfByteUtf8);
        systemClipObserver.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            if (isVisible())
              console.setText("copy/cut in text document: " + ae.getActionCommand() + "\n" + ae.getSource());
          }
        });
      }
    } else {
      if (systemClipObserver != null) {
        systemClipObserver.stop();
        systemClipObserver = null;
      }
    }

  }
}
