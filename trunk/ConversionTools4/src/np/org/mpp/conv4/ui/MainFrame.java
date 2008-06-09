package np.org.mpp.conv4.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import np.org.mpp.ui.WidgetFactory;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1588375492735364352L;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    static JToolBar toolBar;
    static MenuBar menuBar;
    static StartupPanel startPanel;
    static ConversionPanel conversionPanel;
    static StatusBar statusBar;
    WidgetFactory widgetFactory;

    public MainFrame() {
	super("Conversion Tools");
	widgetFactory = WidgetFactory.getInstance();
	setLayout(new BorderLayout(20, 2));

	// for positioning the window at the center of the screen
	int x = (screenSize.width - WIDTH) / 2;
	int y = (screenSize.height - HEIGHT) / 2;

	menuBar = MenuBar.getInstance();

	startPanel = new StartupPanel();

	toolBar = ToolBar.getInstance("Font2Unicode");
	toolBar.setVisible(false);
	conversionPanel = new ConversionPanel();

	statusBar = new StatusBar();
	statusBar.setVisible(false);

	setJMenuBar(menuBar);

	add(toolBar, BorderLayout.NORTH);
	add(startPanel, BorderLayout.CENTER);
	add(statusBar, BorderLayout.SOUTH);

	setIconImage(widgetFactory.createImage("icon.png"));
	setPreferredSize(new Dimension(WIDTH, HEIGHT));
	setLocation(x, y);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	pack();
    }
}
