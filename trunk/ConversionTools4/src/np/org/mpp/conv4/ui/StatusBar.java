package np.org.mpp.conv4.ui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

public class StatusBar extends JToolBar {
    private static final long serialVersionUID = 7097866625742998538L;

    /**
     * displays the progress of conversion
     */
    static JProgressBar progressBar;
    static JLabel lblStatus;

    StatusBar() {
	// setLayout(new BorderLayout());
	setFloatable(false);
	setPreferredSize(new Dimension(MainFrame.WIDTH - 20, 25));
	initComponents();
    }

    private void initComponents() {
	lblStatus = new JLabel("Status Test");
	lblStatus.setPreferredSize(new Dimension(160, 20));

	progressBar = new JProgressBar();
	progressBar.setValue(50);
	progressBar.setStringPainted(true);
	progressBar.setPreferredSize(new Dimension(200, 15));

	add(lblStatus);// , BorderLayout.WEST);
	JSeparator line = new JSeparator(JSeparator.VERTICAL);
	add(line);
	add(Box.createRigidArea(new Dimension(10, 20)));
	add(progressBar);// , BorderLayout.EAST);
    }
}
