package np.org.mpp.cd.ui;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Splash {
	DialogFrame splash;
	JLabel background;

	public Splash(Menu menu, int width, int height, String imagePath) {
		splash = new DialogFrame(menu, "", false, width, height, "");
		// background = new JLabel(new ImageIcon(ClassLoader
		// .getSystemResource((imagePath))));
		background = new JLabel(new ImageIcon((imagePath)));
		splash.setLayout(new FlowLayout(FlowLayout.LEFT));
		splash.setUndecorated(true);

		splash.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				splash.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		splash.add(background);
	}

	public void show() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splash.createAndShowGUI();
			}
		});
	}
}
