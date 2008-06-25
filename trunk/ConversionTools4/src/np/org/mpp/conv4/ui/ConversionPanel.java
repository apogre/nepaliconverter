package np.org.mpp.conv4.ui;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.Action;
import javax.swing.border.EtchedBorder;

import np.org.mpp.conv4.ConversionTools;
import np.org.mpp.conv4.ui.dnd.ClipboardObserver;
import np.org.mpp.conv4.ui.dnd.FilesAndHtmlTransferHandler;

public class ConversionPanel extends JPanel {
  private static final long serialVersionUID = 6407038654026984919L;
  public JEditorPane console;

  FilesAndHtmlTransferHandler transferhandler = new FilesAndHtmlTransferHandler();

  public ConversionPanel() {

    console = new JEditorPane();
    JScrollPane scroller = new JScrollPane(console);

    scroller.setPreferredSize(new Dimension(MainFrame.WIDTH - 10,
                                            MainFrame.HEIGHT - 75));

    console.setEditable(false);
    console.setText(ConversionTools.initLog);

    console.setBackground(Color.WHITE);
    console.setPreferredSize(new Dimension(MainFrame.WIDTH - 10,
                                           MainFrame.HEIGHT - 75));
    console.setBorder(new EtchedBorder(2));

    setLayout(new BorderLayout());
    // setBorder(new EmptyBorder(2, 2, 2, 2));

    add(scroller, BorderLayout.CENTER);

    console.setTransferHandler(transferhandler);


    ActionMap map = console.getActionMap();
    map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
            TransferHandler.getPasteAction());

    InputMap imap = console.getInputMap(WHEN_IN_FOCUSED_WINDOW);
    imap.put(KeyStroke.getKeyStroke("ctrl V"),
             TransferHandler.getPasteAction().getValue(Action.NAME));
    console.setInputMap(WHEN_IN_FOCUSED_WINDOW, imap);

    transferhandler.console = console;


    // Observes the selection (copy/cut in text documents detected)
    new ClipboardObserver(Toolkit.getDefaultToolkit().getSystemClipboard(), transferhandler.htmlDf).addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
          if (isVisible())
              console.setText("copy/cut in text document: " + ae.getActionCommand() + "\n" + ae.getSource());
      }
    });



  }

    public void confgureMenus(ToolBar toolBar, MenuBar menuBar) {
        toolBar.cmbFont.setVisible(false);
    }
}
