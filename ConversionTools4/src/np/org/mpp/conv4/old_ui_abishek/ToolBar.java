package np.org.mpp.conv4.old_ui_abishek;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import np.org.mpp.conv4.ConversionTools;
import np.org.mpp.exception.FileNotSelectedException;
import np.org.mpp.ui.WidgetFactory;

public class ToolBar extends JToolBar implements ActionListener {
    private static final long serialVersionUID = 7786176193025631322L;

    static JButton btnBack;
    static JButton btnOpen;
    static JButton btnPaste;
    static JButton btnRun;
    static JButton btnStop;

    static JComboBox cmbFont;
    static JComboBox cmbTrans;
    static ToolBar instance;

    private final String[] fonts = { "Preeti", "Kantipur" };
    private final String[] trans = { "ALA-LC", "MS-Nepal", "Local" };

    private WidgetFactory widgetFactory;

    private ToolBar() {
	// super(SwingConstants.HORIZONTAL);
	setLayout(new FlowLayout(FlowLayout.LEADING, 5, 0));
	setPreferredSize(new Dimension(MainFrame.WIDTH - 20, 30));
	widgetFactory = WidgetFactory.getInstance();

	btnBack = widgetFactory.createJButton("back", "back.png",
		"Return to startup");
	btnBack.addActionListener(this);

	btnOpen = widgetFactory.createJButton("open", "open.png", "Open");
	btnOpen.addActionListener(this);

	btnPaste = widgetFactory.createJButton("paste", "paste.png", "Paste");
	btnPaste.addActionListener(this);

	btnRun = widgetFactory.createJButton("convert", "run.gif",
		"Start Conversion");
	btnRun.addActionListener(this);

	btnStop = widgetFactory.createJButton("stop", "stop.gif",
		"Terminate Conversion");
	btnStop.addActionListener(this);

	cmbFont = new JComboBox(fonts);
	cmbTrans = new JComboBox(trans);
    }

    static JToolBar getInstance(String status) {
	instance = new ToolBar();

	if (status.equals("Font2Unicode")) {

	    instance.add(btnBack);
	    JSeparator line = new JSeparator(JSeparator.VERTICAL);
	    line.setPreferredSize(new Dimension(1, 20));
	    instance.add(line);
	    btnOpen.add(line);

	    instance.add(btnOpen);
	    instance.add(btnPaste);
	    instance.add(line);
	    instance.add(btnRun);
	    instance.add(btnStop);
	    instance.add(line);
	    instance.add(cmbFont);
	    instance.add(line);
	    instance.add(cmbTrans);
	    instance.add(line);
	}

	return instance;
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == btnBack) {
	    MainFrame.toolBar.setVisible(false);
	    MainFrame.statusBar.setVisible(false);
	    MainFrame.conversionPanel.setVisible(false);
	    ConversionTools.frame.add(MainFrame.startPanel);
	    MainFrame.startPanel.setVisible(true);
	} else if (e.getSource() == btnOpen) {
	    String filePath = "";
	    try {
		filePath = widgetFactory.invokeJFileChooser(this);
		filePath = "You chose to open this file: " + filePath;
	    } catch (FileNotSelectedException fe) {
		filePath = "Oops no file chosen!!!";
		// System.err.println(e.toString());
	    }
	    ConversionTools.appendLog(filePath);
	}
    }
}
