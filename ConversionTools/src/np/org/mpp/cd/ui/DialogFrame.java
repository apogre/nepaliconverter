/**
 * 
 */
package np.org.mpp.cd.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import np.org.mpp.cd.ui.lnf.LnF;

/**
 * All the dialog boxes that are needed by the {@link LnF} creates an instance
 * of this class.
 * 
 * It allows to create a unresizable frame that appears at the center of the
 * screen withe the icon specified below default close operation as exit and
 * layout as null.
 * 
 * @author Abhishek
 */
public class DialogFrame extends JDialog {
	private static final long serialVersionUID = -8490481949013824367L;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public DialogFrame(Menu owner, String title, boolean isResizable,
			int width, int height, String icon) {
		super(owner, title, true);
		setLayout(null);

		// for positioning the window at the center of the screen
		int x = (screenSize.width - width) / 2;
		int y = (screenSize.height - height) / 2;

		setLocation(x, y);
		setTitle(title);
		setResizable(isResizable);
		setSize(width, height);
		// setIconImage(new ImageIcon(ClassLoader.getSystemResource("res/images/"
		// + icon)).getImage());
		setIconImage(new ImageIcon("res/images/" + icon).getImage());
	}

	public void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel(LnF.lnfModel.getLookNFeel());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			System.err.println("Sorry, could not install the requested theme!");
		}

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
