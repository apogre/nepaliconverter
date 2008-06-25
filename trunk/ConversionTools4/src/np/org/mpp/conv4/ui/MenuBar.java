package np.org.mpp.conv4.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import np.org.mpp.conv4.ConversionTools;
import np.org.mpp.exception.FileNotSelectedException;
import np.org.mpp.ui.WidgetFactory;

public class MenuBar extends JMenuBar implements ActionListener {
    private static final long serialVersionUID = -5440154862431565251L;
    JMenu file;
    JMenuItem open;
    JMenuItem exit;

    JMenu edit;
    JMenuItem paste;

    JMenu option;
    JMenu goTo;
    ButtonGroup gotoGroup;
    JMenuItem fontToUnicode;
    JMenuItem unicodeToFont;
    JMenuItem transliteration;
    JMenuItem prefs;

    JMenu tools;
    JMenuItem run;
    JMenuItem stop;

    JMenu help;
    JMenuItem helpContents;
    JMenuItem seeSplash;

    JMenuItem abtConv4;
    JMenuItem abtDevelopers;
    JMenuItem abtMPP;

    // JMenuItem types
    private final int PLAIN = 0;
    private final int CHECK = 1;
    private final int RADIO = 2;

    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    final int WIDTH = screen.width;
    final int HEIGHT = screen.height - 30;

    private static MenuBar instance;
    private WidgetFactory widgetFactory;

    private MenuBar() {
	super();
	widgetFactory = WidgetFactory.getInstance();
	initMenuBar();
    }

    static MenuBar getInstance() {
	if (instance == null) instance = new MenuBar();
	return instance;
    }

    private void initMenuBar() {

	file = widgetFactory.createJMenu("File", 'F', null, this);
	edit = widgetFactory.createJMenu("Edit", 'E', null, this);
	option = widgetFactory.createJMenu("Option", 'O', null, this);
	goTo = widgetFactory.createJMenu("Goto", 'G', null, this);
	tools = widgetFactory.createJMenu("Tools", 'T', null, this);
	help = widgetFactory.createJMenu("Help", 'H', null, this);

	gotoGroup = new ButtonGroup();

	open = widgetFactory.createJMenuItem(file, PLAIN, "Open File...",
		"open.png", KeyStroke.getKeyStroke(KeyEvent.VK_O,
			InputEvent.CTRL_MASK), "Open (Ctrl+O)", this, true);

	exit = widgetFactory.createJMenuItem(file, PLAIN, "Exit", "exit.png",
		null, "Exit", this, false);

	paste = widgetFactory.createJMenuItem(edit, PLAIN, "Paste",
		"paste.png", KeyStroke.getKeyStroke(KeyEvent.VK_V,
			InputEvent.CTRL_MASK), "Paste(Ctrl+V)", this, true);

	fontToUnicode = widgetFactory.createJMenuItem(goTo, RADIO,
		"FontToUnicode", "f2u.png", KeyStroke.getKeyStroke(
			KeyEvent.VK_U, InputEvent.CTRL_MASK),
		"Convert from non-Unicode font to Unicode (Ctrl+U)", this,
		false);
	unicodeToFont = widgetFactory
		.createJMenuItem(goTo, RADIO, "UnicodeToFont", "u2f.png",
			KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_MASK),
			"Convert from non-Unicode font to Unicode (Ctrl+F)",
			this, true);
	transliteration = widgetFactory.createJMenuItem(goTo, RADIO,
		"Transliteration", "trans.png", KeyStroke.getKeyStroke(
			KeyEvent.VK_T, InputEvent.CTRL_MASK),
		"Roman Transliteration (Ctrl+T)", this, false);
	gotoGroup.add(fontToUnicode);
	gotoGroup.add(unicodeToFont);
	gotoGroup.add(transliteration);
	option.add(goTo);

	prefs = widgetFactory.createJMenuItem(option, PLAIN, "Preferences...",
		"prefs.png", KeyStroke.getKeyStroke(KeyEvent.VK_P,
			InputEvent.CTRL_MASK), "Paste(Ctrl+P)", this, false);

	run = widgetFactory.createJMenuItem(tools, PLAIN, "Start Conversion",
		"run.gif", KeyStroke.getKeyStroke("F11"),
		"Start Conversion (F11)", this, true);
	stop = widgetFactory.createJMenuItem(tools, PLAIN, "Stop Conversion",
		"stop.gif", KeyStroke.getKeyStroke("F12"),
		"Terminate Conversion (F10)", this, false);

	helpContents = widgetFactory.createJMenuItem(help, PLAIN,
		"Help Contents", "help.gif", null, "Help Contents", this, true);
	seeSplash = widgetFactory.createJMenuItem(help, PLAIN, "Splash Screen",
		"splashIcon.png", null, "View Splash Screen", this, true);
	abtDevelopers = widgetFactory.createJMenuItem(help, PLAIN,
		"About Developers", "team.gif", null, "About Developers", this,
		false);
	abtMPP = widgetFactory.createJMenuItem(help, PLAIN, "About MPP",
		"mpp.png", null, "About MPP", this, true);
	abtConv4 = widgetFactory.createJMenuItem(help, PLAIN,
		"About ConversionTools4", "icon.png", null,
		"About ConversionTools4", this, false);

	add(file);
	add(edit);
	add(option);
	add(tools);
	add(help);
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == open) {
	    String filePath = "";
	    try {
		filePath = widgetFactory.invokeJFileChooser(this);
		filePath = "You chose to open this file: " + filePath;
	    } catch (FileNotSelectedException fe) {
		filePath = "Oops no file chosen!!!";
	    }
	    ConversionTools.appendLog(filePath);
	} else if (e.getSource() == exit) {
	    System.out.println("Exiting from system!");
	    System.exit(0);
	}
    }
}
