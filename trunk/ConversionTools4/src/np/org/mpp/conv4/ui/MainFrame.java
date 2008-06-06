package np.org.mpp.conv4.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1588375492735364352L;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public MainFrame(JPanel panel) {
	super("Conversion Tools");

	// for positioning the window at the center of the screen
	int x = (screenSize.width - WIDTH) / 2;
	int y = (screenSize.height - HEIGHT) / 2;

	setLayout(new FlowLayout(FlowLayout.CENTER));
	add(panel);
	pack();

	setLocation(x, y);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(WIDTH, HEIGHT));
	setSize(800, 600);
	// setVisible(true);
    }
}
