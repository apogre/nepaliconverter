/**
 * 
 */
package np.org.mpp.conv4.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * @author Abhishek
 * 
 */
public class StartupPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 3553734976057624357L;

	private JButton btnF2U;
	private JButton btnU2F;
	private JButton btnTraslit;
	private JButton btnExit;

	private JLabel lblText1;
	private JLabel lblText2;

	private JToolBar toolBar;

	private ImageIcon imgF2U = new ImageIcon(
			np.org.mpp.conv4.ui.StartupPanel.class.getResource("f2u.png"));

	private ImageIcon imgU2F = new ImageIcon(
			np.org.mpp.conv4.ui.StartupPanel.class.getResource("u2f.png"));

	private ImageIcon imgTranslit = new ImageIcon(
			np.org.mpp.conv4.ui.StartupPanel.class.getResource("help.png"));

	private ImageIcon imgExit = new ImageIcon(
			np.org.mpp.conv4.ui.StartupPanel.class.getResource("exit.png"));

	public StartupPanel() {

		setPreferredSize(new Dimension(800, 600));
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		initComponents();
	}

	private void initComponents() {
		lblText1 = new JLabel(
				"<html><body><b> Wel come to Conversion Tools!</b>");
		lblText1.setPreferredSize(new Dimension(600, 20));

		add(lblText1);

		lblText2 = new JLabel("Please select one from the following task:");
		lblText2.setPreferredSize(new Dimension(600, 20));
		add(lblText2);

		btnF2U = new JButton(
				"<html><body>Convert from non-Unicode font<br>to <b>Unicode</b>");
		btnF2U.setPreferredSize(new Dimension(300, 60));
		btnF2U.setIcon(imgF2U);
		btnF2U.addActionListener(this);
		add(btnF2U);

		btnU2F = new JButton(
				"<html><body>Convert from Unicode to non-Unicode<br><b>font</b> (like Preeti)");
		btnU2F.setPreferredSize(new Dimension(300, 60));
		btnU2F.setIcon(imgU2F);
		btnU2F.addActionListener(this);
		add(btnU2F);

		btnTraslit = new JButton(
				"<html><body>Convert from Devanagari to <br><b>Roman</b> transliteration");
		btnTraslit.setPreferredSize(new Dimension(300, 60));
		btnTraslit.setIcon(imgTranslit);
		btnTraslit.addActionListener(this);
		add(btnTraslit);

		btnExit = new JButton("<html><body><b>Exit</b> from the Application");
		btnExit.setPreferredSize(new Dimension(300, 60));
		btnExit.setIcon(imgExit);
		btnExit.addActionListener(this);
		add(btnExit);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnF2U) {
			toolBar = ToolBar.getInstance("Font2Unicode");
			ConversionTools.frame.remove(this);
			ConversionTools.frame.add(toolBar, BorderLayout.NORTH);
			ConversionTools.frame.repaint();
			ConversionTools.frame.setVisible(false);

			ConversionTools.frame.setVisible(true);
		} else if (e.getSource() == btnU2F) {

		} else if (e.getSource() == btnTraslit) {

		} else if (e.getSource() == btnExit) {
			System.out.println("Exiting from system!");
			System.exit(0);
		}
	}
}
