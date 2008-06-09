package np.org.mpp.conv4;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import np.org.mpp.conv4.ui.ConversionPanel;
import np.org.mpp.conv4.ui.MainFrame;

public class ConversionTools {
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    public static MainFrame frame;
    public static String initLog = "Please drag and drop file(s) or "
	    + "\r\nPress Ctrl+V to paste from Clipboard.";
    private static String log = initLog;

    // static WidgetFactory widgetFactory;

    public ConversionTools() {
	frame = new MainFrame();
	// widgetFactory = WidgetFactory.getInstance();
	/*
	 * Validate frames that have preset sizes. Pack frames that have useful
	 * preferred size info, e.g. from their layout
	 */
	if (packFrame) {
	    frame.pack();
	} else {
	    frame.validate();
	}

	// Center the window
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension frameSize = frame.getSize();
	if (frameSize.height > screenSize.height) {
	    frameSize.height = screenSize.height;
	}
	if (frameSize.width > screenSize.width) {
	    frameSize.width = screenSize.width;
	}
	frame.setLocation((screenSize.width - frameSize.width) / 2,
		(screenSize.height - frameSize.height) / 2);
	frame.setVisible(true);
    }

    /**
     * Application entry point.
     * 
     * @param args
     *                String[]
     */
    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		try {
		    UIManager.setLookAndFeel(UIManager
			    .getSystemLookAndFeelClassName());
		} catch (Exception exception) {
		    exception.printStackTrace();
		}

		new ConversionTools();
	    }
	});
    }

    public static void appendLog(String logText) {
	log += "\n" + logText;
	ConversionPanel.console.setText(log);
    }

    static public String getLog() {
	return log;
    }
}