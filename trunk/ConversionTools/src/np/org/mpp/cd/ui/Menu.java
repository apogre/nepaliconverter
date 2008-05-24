/**
 *
 */
package np.org.mpp.cd.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import np.org.mpp.cd.ui.lnf.LnF;
import np.org.mpp.cd.ui.lnf.LnFSelector;

/**
 * @author Abhishek
 * 
 */
public class Menu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4202328775681932050L;
	private final String HELP_FILE_PATH = "manual/help.html";

	private JMenuBar menuBar;

	private JMenu file;
	private JMenu tool;
	private JMenu option;
	private JMenu help;

	private JMenuItem exit;
	private JMenuItem fontToUnicode;
	private JMenuItem unicodeToFont;
	private JMenuItem lnf;
	private JMenuItem seeSplash;
	private JMenuItem abtCD;

	private JToolBar toolBar;

	final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	final int WIDTH = screen.width;
	final int HEIGHT = screen.height - 30;

	public Menu() {
		super("Conversion Tools © Madan Puraskar Pustakalaya");

		addMenuBar();
		addToolBar();

		setJMenuBar(menuBar);
		// setIconImage(new ImageIcon(ClassLoader
		// .getSystemResource("res/images/icon.png")).getImage());
		setIconImage(new ImageIcon("res/images/icon.png").getImage());
		setLocation(0, 0);
		setSize(WIDTH, HEIGHT);
		setMinimumSize(new Dimension(550, 250));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setVisible(true);
	}

	private void addMenuBar() {
		menuBar = new JMenuBar();

		file = new JMenu("File");
		file.setMnemonic('F');

		tool = new JMenu("Tools");
		tool.setMnemonic('T');

		option = new JMenu("Option");
		option.setMnemonic('O');

		help = new JMenu("Help");
		help.setMnemonic('H');

		exit = new JMenuItem("Exit");
		// exit.setIcon((Icon) new ImageIcon(ClassLoader
		// .getSystemResource("res/images/power.png")));
		exit.setIcon((Icon) new ImageIcon("res/images/power.png"));

		fontToUnicode = new JMenuItem("FontToUnicode");
		fontToUnicode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				InputEvent.CTRL_MASK));
		// fontToUnicode.setIcon((Icon) new ImageIcon(ClassLoader
		// .getSystemResource("res/images/f2u.png")));
		fontToUnicode.setIcon((Icon) new ImageIcon("res/images/f2u.png"));

		unicodeToFont = new JMenuItem("UnicodeToFont");
		unicodeToFont.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_MASK));
		// unicodeToFont.setIcon((Icon) new ImageIcon(ClassLoader
		// .getSystemResource("res/images/u2f.png")));
		unicodeToFont.setIcon((Icon) new ImageIcon("res/images/u2f.png"));
		lnf = new JMenuItem("Change Theme");
		lnf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				InputEvent.CTRL_MASK));
		// lnf.setIcon((Icon) new ImageIcon(ClassLoader
		// .getSystemResource("res/images/theme.png")));
		lnf.setIcon((Icon) new ImageIcon("res/images/theme.png"));

		seeSplash = new JMenuItem("View Splash Screen");
		abtCD = new JMenuItem("About Converter/Deconverter");

		option.addActionListener(this);
		exit.addActionListener(this);
		fontToUnicode.addActionListener(this);
		unicodeToFont.addActionListener(this);
		lnf.addActionListener(this);
		abtCD.addActionListener(this);
		seeSplash.addActionListener(this);

		file.add(exit);

		tool.add(fontToUnicode);
		tool.addSeparator();
		tool.add(unicodeToFont);

		option.addSeparator();
		option.add(lnf);

		help.add(abtCD);
		help.addSeparator();
		help.add(seeSplash);

		menuBar.add(file);
		menuBar.add(tool);
		menuBar.add(option);
		menuBar.add(help);
	}

	private void addToolBar() {
		toolBar = new JToolBar(SwingConstants.HORIZONTAL);
		addToolBarButtons("f2u", "f2u.png", "Convert Font To Unicode");
		addToolBarButtons("theme", "theme.png", "Change Theme");
		addToolBarButtons("u2f", "u2f.png", "Convert Unicode To Font");
		addToolBarButtons("power", "power.png", "Exit");

		toolBar.setBounds(0, 0, WIDTH, 25);
		add(toolBar, BorderLayout.NORTH);
	}

	private void addToolBarButtons(String name, String imagePath, String toolTip) {
		JButton toolButton = new JButton();
		toolButton.setActionCommand(name);
		// toolButton.setIcon((Icon) (new ImageIcon(ClassLoader
		// .getSystemResource("res/images/" + imagePath))));
		toolButton.setIcon((Icon) (new ImageIcon("res/images/" + imagePath)));
		toolButton.setToolTipText(toolTip);
		toolButton.addActionListener(this);
		toolBar.add(toolButton);
		toolBar.addSeparator();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit || e.getActionCommand() == "power") {
			System.exit(0);
		} else if (e.getSource() == lnf || e.getActionCommand() == "theme") {
			LnFSelector lnf = new LnFSelector(LnF.lnfModel, this, "theme.png");
			lnf.createAndShowGUI();
		} else if (e.getSource() == abtCD) {
			File file = new File(HELP_FILE_PATH);
			String absPath = file.getAbsolutePath();
			try {
				// displaying of the file is platform specific so below are few:
				String os = System.getProperty("os.name");
				if (os.equals("linux")) {
					// assume mozilla is installed in the linux system
					Runtime.getRuntime().exec("mozilla " + absPath);
				} else {
					// its windows system
					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler " + absPath);
				}
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(null,
						"Could not find the help file", "File not found!",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == seeSplash) {
			new Splash(this, 460, 300, "res/images/splash.png").show();
		} else {
			final Frame converter;
			if (e.getSource() == fontToUnicode || e.getActionCommand() == "f2u") {
				converter = new Frame(this, "FontToUnicode", "f2u.png");
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						converter.createAndShowGUI(true);
					}
				});
			} else if (e.getSource() == unicodeToFont
					|| e.getActionCommand() == "u2f") {
				converter = new Frame(this, "UnicodeToFont", "u2f.png");

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						converter.createAndShowGUI(false);
					}
				});
			}
		}
	}
}
