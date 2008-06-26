package np.org.mpp.conv4.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import np.org.mpp.ui.WidgetFactory;

public class MenuBar extends JMenuBar {
    private static final long serialVersionUID = -5440154862431565251L;
    JMenu file;
    JMenuItem mopen;
    JMenuItem mexit;

    JMenu edit;
    JMenuItem mpaste;

    JMenu option;
    JMenu goTo;
    ButtonGroup gotoGroup;
    JMenuItem mgoto_fontToUnicode;
    JMenuItem mgoto_unicodeToFont;
    JMenuItem mgoto_transliteration;
    JMenuItem mprefs;

    JMenu tools;
    JMenuItem mrun;
    JMenuItem mstop;

    JMenu help;
    JMenuItem mhelpContents;
    JMenuItem mseeSplash;

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

	file = widgetFactory.createJMenu("File", 'F', null, null);
	edit = widgetFactory.createJMenu("Edit", 'E', null, null);
	option = widgetFactory.createJMenu("Option", 'O', null, null);
	goTo = widgetFactory.createJMenu("Goto", 'G', null, null);
	tools = widgetFactory.createJMenu("Tools", 'T', null, null);
	help = widgetFactory.createJMenu("Help", 'H', null, null);

	gotoGroup = new ButtonGroup();

	mopen = widgetFactory.createJMenuItem(file, PLAIN, "Open File...",
		"open.png", KeyStroke.getKeyStroke(KeyEvent.VK_O,
			InputEvent.CTRL_MASK), "Open (Ctrl+O)", null, true);

	mexit = widgetFactory.createJMenuItem(file, PLAIN, "Exit", "exit.png",
		null, "Exit", null, false);

	mpaste = widgetFactory.createJMenuItem(edit, PLAIN, "Paste",
		"paste.png", KeyStroke.getKeyStroke(KeyEvent.VK_V,
			InputEvent.CTRL_MASK), "Paste(Ctrl+V)", null, true);

	mgoto_fontToUnicode = widgetFactory.createJMenuItem(goTo, RADIO,
		"FontToUnicode", "f2u.png", KeyStroke.getKeyStroke(
			KeyEvent.VK_U, InputEvent.CTRL_MASK),
		"Convert from non-Unicode font to Unicode (Ctrl+U)", null,
		false);
	mgoto_unicodeToFont = widgetFactory
		.createJMenuItem(goTo, RADIO, "UnicodeToFont", "u2f.png",
			KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_MASK),
			"Convert from non-Unicode font to Unicode (Ctrl+F)",
			null, true);
	mgoto_transliteration = widgetFactory.createJMenuItem(goTo, RADIO,
		"Transliteration", "trans.png", KeyStroke.getKeyStroke(
			KeyEvent.VK_T, InputEvent.CTRL_MASK),
		"Roman Transliteration (Ctrl+T)", null, false);
	gotoGroup.add(mgoto_fontToUnicode);
	gotoGroup.add(mgoto_unicodeToFont);
	gotoGroup.add(mgoto_transliteration);
	option.add(goTo);

	mprefs = widgetFactory.createJMenuItem(option, PLAIN, "Preferences...",
		"prefs.png", KeyStroke.getKeyStroke(KeyEvent.VK_P,
			InputEvent.CTRL_MASK), "Paste(Ctrl+P)", null, false);

	mrun = widgetFactory.createJMenuItem(tools, PLAIN, "Start Conversion",
		"run.gif", KeyStroke.getKeyStroke("F11"),
		"Start Conversion (F11)", null, true);
	mstop = widgetFactory.createJMenuItem(tools, PLAIN, "Stop Conversion",
		"stop.gif", KeyStroke.getKeyStroke("F12"),
		"Terminate Conversion (F10)", null, false);

	mhelpContents = widgetFactory.createJMenuItem(help, PLAIN,
		"Help Contents", "help.gif", null, "Help Contents", null, true);
	mseeSplash = widgetFactory.createJMenuItem(help, PLAIN, "Splash Screen",
		"splashIcon.png", null, "View Splash Screen", null, true);
	abtDevelopers = widgetFactory.createJMenuItem(help, PLAIN,
		"About Developers", "team.gif", null, "About Developers", null,
		false);
	abtMPP = widgetFactory.createJMenuItem(help, PLAIN, "About MPP",
		"mpp.png", null, "About MPP", null, true);
	abtConv4 = widgetFactory.createJMenuItem(help, PLAIN,
		"About ConversionTools4", "icon.png", null,
		"About ConversionTools4", null, false);

	add(file);
	add(edit);
	add(option);
	add(tools);
	add(help);
    }
}
