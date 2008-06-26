package np.org.mpp.conv4.ui;

import np.org.mpp.conv4.ui.dnd.ClipboardObserver;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionListener;

public class ConversionPanelU2F extends ConversionPanel {

    public ConversionPanelU2F() {
        appendLog("this is the U 2 F");
    }

    public void confgureGuiForThisPanel(ToolBar toolBar, MenuBar menuBar) {
        //toolBar.cmbFont.setVisible(false);
        toolBar.cmbTrans.setVisible(false);
    }

    public void confgureGui(MainFrame mainFrame) {
        //mainFrame.toolBar.cmbFont.setVisible(false);
        mainFrame.toolBar.cmbTrans.setVisible(false);
    }

}
