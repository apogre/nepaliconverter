package np.org.mpp.conv4.ui;

import np.org.mpp.conv4.ui.dnd.ClipboardObserver;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionListener;

public class ConversionPanelTrans extends ConversionPanel {

    public ConversionPanelTrans() {

        // Observes the selection (also without copy)
        new ClipboardObserver(Toolkit.getDefaultToolkit().getSystemSelection(), DataFlavor.stringFlavor).addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
              if (isVisible())
                  console.setText("text marked: " + ae.getActionCommand() + "\n" + ae.getSource());
          }
        });

    }

    public void confgureGuiForThisPanel(ToolBar toolBar, MenuBar menuBar) {
        toolBar.cmbFont.setVisible(false);
        toolBar.cmbTrans.setVisible(false);
    }




}
