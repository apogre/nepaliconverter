package np.org.mpp.ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import np.org.mpp.exception.FileNotSelectedException;
import java.net.URL;

/**
 * A class that serves as factory for providing widgets. It follows Singleton
 * Design Pattern
 *
 * @author Abhishek
 *
 */
public class WidgetFactory {
    private static WidgetFactory instance;

    // private static final int PLAIN = 0;
    private static final int CHECK = 1;
    private static final int RADIO = 2;

    private WidgetFactory() {
    }

    public static WidgetFactory getInstance() {
	return (instance == null ? instance = new WidgetFactory() : instance);
    }

    /**
     * a generic function to create JMenu
     *
     * @param name
     *                the name of the menu
     * @param mnemonic
     *                the mnemonic to be set for this menu
     * @param iconPath
     *                the path of the icon to be displayed
     * @param listener
     *                the {@link ActionListener}to be linked with
     * @return an instance of {@link JMenu}
     */
    public JMenu createJMenu(String name, char mnemonic, String iconPath,
	    ActionListener listener) {
	JMenu menu = new JMenu(name);

	if (mnemonic != ' ') {
	    menu.setMnemonic(mnemonic);
	}

	if (iconPath != null) {
	    menu.setIcon(new ImageIcon(instance.getClass()
		    .getResource(iconPath)));
	}
	menu.addActionListener(listener);

	return menu;
    }

    /**
     *
     * @param menu
     *                the parent menu
     * @param type
     *                the type of menuItem
     * @param text
     *                the text on the menuItem
     * @param iconPath
     *                the path of the icon t be dislayed
     * @param keys
     *                the combination of keys to invoke this menuItem
     * @param toolTip
     *                the toolTip for this menuItem
     * @param listener
     *                the ActionListener to be associated with this menuItem
     * @return and instance of {@link JMenuItem}
     */
    public JMenuItem createJMenuItem(JMenu menu, int type, String text,
	    String iconPath, KeyStroke keys, String toolTip,
	    ActionListener listener, boolean separate) {
	JMenuItem menuItem = new JMenuItem();
	switch (type) {
	case RADIO:
	    menuItem = new JRadioButtonMenuItem();
	    break;
	case CHECK:
	    menuItem = new JCheckBoxMenuItem();
	    break;
	default:
	    menuItem = new JMenuItem();
	    break;
	}

	// add the text
	menuItem.setText(text);

	// add icon
	if (iconPath != null) {
      URL url = this.getClass().getResource(iconPath);
      System.out.println(url);
	    menuItem.setIcon(new ImageIcon(url));
	}

	// add accelerator
	if (keys != null) {
	    menuItem.setAccelerator(keys);
	}

	// add Tooltip
	if (toolTip != null) {
	    menuItem.setToolTipText(toolTip);
	}

	// add the actionListener
	menuItem.addActionListener(listener);

	menu.add(menuItem);

	if (separate) {
	    menu.addSeparator();
	}
	return menuItem;
    }

    public JButton createJButton(String name, String imagePath, String toolTip) {
	JButton toolButton = new JButton();
	toolButton.setActionCommand(name);
	toolButton
		.setIcon(new ImageIcon(this.getClass().getResource(imagePath)));
	toolButton.setToolTipText(toolTip);

	return toolButton;
    }

    public Image createImage(String imagePath) {
	Image image = new ImageIcon(this.getClass().getResource(imagePath))
		.getImage();
	return image;
    }

    public String invokeJFileChooser(Component parent)
	    throws FileNotSelectedException {
	JFileChooser browse = new JFileChooser();

	int returnVal = browse.showOpenDialog(parent);
	System.out.println("re" + returnVal);
	if (returnVal != JFileChooser.CANCEL_OPTION) {
	    return browse.getSelectedFile().getAbsolutePath();
	} else {
	    System.out.println("asdf");
	    throw new FileNotSelectedException("");
	}
    }
}
