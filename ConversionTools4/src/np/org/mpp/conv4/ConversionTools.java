package np.org.mpp.conv4;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//import np.org.mpp.conv4.old_ui_abishek.*;
import np.org.mpp.conv4.ui.*;
import java.io.*;
import np.org.mpp.conv4.translit.NepaliTransliterationJacob;

public class ConversionTools {
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    public static MainFrame frame;

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
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            // start the GUI

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
        } else {
            // CLI interface
          String arg1 = args[0].toLowerCase();
          if (arg1.startsWith("-tr")) {
              // Transliteration mode

              BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
              NepaliTransliterationJacob tr = new NepaliTransliterationJacob("res/transliteration/NepaliALA-LC.xml", true);
              String lin = null;
              while ( (lin = br.readLine()) != null) {
                  String outLin = tr.convertText(null, lin);
                  System.out.println(outLin);
              }

          }
        }
    }

    public static void appendLog(String logText) {
        System.out.println(logText);
	//log += "\n" + logText;
	//ConversionPanel.console.setText(log);
    }
}
