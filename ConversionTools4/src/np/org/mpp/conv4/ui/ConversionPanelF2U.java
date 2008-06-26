package np.org.mpp.conv4.ui;

import np.org.mpp.conv4.ui.dnd.ClipboardObserver;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionListener;

public class ConversionPanelF2U extends ConversionPanel {

    public ConversionPanelF2U() {
        appendLog("this is the F 2 U");
    }

    public void confgureGuiForThisPanel(ToolBar toolBar, MenuBar menuBar) {
        toolBar.cmbFont.setVisible(false);
        toolBar.cmbTrans.setVisible(false);
    }




}
