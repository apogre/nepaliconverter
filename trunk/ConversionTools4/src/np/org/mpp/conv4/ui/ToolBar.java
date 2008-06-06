package np.org.mpp.conv4.ui;

import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ToolBar {
    static JButton btnBack;
    static JButton btnOpen;
    static JButton btnPaste;
    static JButton btnRun;

    static JComboBox cmbFontTrans;

    static JToolBar toolBar;

    private ToolBar() {
	btnBack = ToolBar.buttonInstance("back", "back.png",
		"Return to startup");
	btnOpen = ToolBar.buttonInstance("open", "open.png", "Open");
	btnPaste = ToolBar.buttonInstance("paste", "paste.png", "Paste");
	btnRun = ToolBar.buttonInstance("exit", "run.png", "Exit");
    }

    private static JButton buttonInstance(String name, String imagePath,
	    String toolTip) {
	JButton toolButton = new JButton();
	toolButton.setActionCommand(name);
	toolButton.setIcon((Icon) (new ImageIcon("res/images/" + imagePath)));
	toolButton.setToolTipText(toolTip);
	return toolButton;
    }

    static JToolBar getInstance(String status) {
	new ToolBar();
	toolBar = new JToolBar(SwingConstants.HORIZONTAL);
	toolBar.setLayout(new FlowLayout());
	if (status.equals("Font2Unicode")) {
	    toolBar.add(btnBack);
	    toolBar.addSeparator();
	    toolBar.add(btnOpen);
	    toolBar.add(btnPaste);
	    toolBar.addSeparator();
	    toolBar.add(btnRun);
	}

	return toolBar;
    }

}
