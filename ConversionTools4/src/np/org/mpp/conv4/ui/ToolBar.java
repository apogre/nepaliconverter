package np.org.mpp.conv4.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

import np.org.mpp.ui.WidgetFactory;

public class ToolBar extends JToolBar {
    private static final long serialVersionUID = 7786176193025631322L;

    JButton btnBack;
    JButton btnOpen;
    JButton btnPaste;
    JButton btnRun;
    JButton btnStop;

    JComboBox cmbFont;
    JComboBox cmbTrans;

    static ToolBar instance;

    private final String[] fonts = { "Preeti", "Kantipur" };
    private final String[] trans = { "ALA-LC", "MS-Nepal", "Local" };

    private WidgetFactory widgetFactory;

    private ToolBar() {
      instance = this;

	// super(SwingConstants.HORIZONTAL);
	setLayout(new FlowLayout(FlowLayout.LEADING, 5, 0));
	setPreferredSize(new Dimension(MainFrame.WIDTH - 20, 30));
	widgetFactory = WidgetFactory.getInstance();

	btnBack = widgetFactory.createJButton("back", "back.png",
		"Return to startup");

	btnOpen = widgetFactory.createJButton("open", "open.png", "Open");

	btnPaste = widgetFactory.createJButton("paste", "paste.png", "Paste");

	btnRun = widgetFactory.createJButton("convert", "run.gif",
		"Start Conversion");

	btnStop = widgetFactory.createJButton("stop", "stop.gif",
		"Terminate Conversion");

	cmbFont = new JComboBox(fonts);
	cmbTrans = new JComboBox(trans);

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

    static ToolBar getInstance(String status) {
	if (instance == null) instance = new ToolBar();
  	return instance;
    }

}
