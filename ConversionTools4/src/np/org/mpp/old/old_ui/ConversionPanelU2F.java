package np.org.mpp.old.old_ui;

import np.org.mpp.conv4.ui.dnd.ClipboardObserver;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConversionPanelU2F extends ConversionPanel {

    public ConversionPanelU2F() {
        appendLog("this is the U 2 F");
    }

    public void confgureGuiForThisPanel(ToolBar toolBar, MenuBar menuBar) {
        //toolBar.cmbFont.setVisible(false);
        toolBar.cmbTrans.setVisible(false);
    }

    private final String[] fonts = { "Preeti", "Kantipur" };

    public void confgureGui(MainFrame mainFrame) {

        if (mainFrame.toolBar.cmbFont.getModel().getSize() == 0) {
            // first time init
            mainFrame.toolBar.cmbFont.setModel(new DefaultComboBoxModel(fonts));
        }

        //mainFrame.toolBar.cmbFont.setVisible(false);
        mainFrame.toolBar.cmbTrans.setVisible(false);
    }

}
