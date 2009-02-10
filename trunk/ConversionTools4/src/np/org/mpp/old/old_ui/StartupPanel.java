/**
 *
 */
package np.org.mpp.old.old_ui;

import java.awt.*;

import javax.swing.*;

import np.org.mpp.old.old_ui.WidgetFactory;

/**
 * @author Abhishek
 *
 */
public class StartupPanel extends JPanel {

    private static final long serialVersionUID = 3553734976057624357L;

    JButton btnF2U;
    JButton btnU2F;
    JButton btnTraslit;
    JButton btnExit;

    private JLabel lblText1;
    private JLabel lblText2;
    private JPanel panel;

    private ImageIcon imgF2U = new ImageIcon(WidgetFactory.class
	    .getResource("f2u.png"));
    private ImageIcon imgU2F = new ImageIcon(WidgetFactory.class
	    .getResource("u2f.png"));
    private ImageIcon imgTranslit = new ImageIcon(WidgetFactory.class
	    .getResource("trans.png"));
    private ImageIcon imgExit = new ImageIcon(WidgetFactory.class
	    .getResource("exit.png"));

    public StartupPanel() {
	setPreferredSize(new Dimension(OldMainFrame.WIDTH, OldMainFrame.HEIGHT));
	setLayout(new FlowLayout(FlowLayout.LEADING));
	initComponents();
    }

    private void initComponents() {
	panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	GridBagConstraints GBC = new GridBagConstraints();
	GBC.weightx = 0.0;
	GBC.gridx = 0;
	GBC.gridy = 0;
	GBC.fill = GridBagConstraints.HORIZONTAL;
	GBC.insets = new Insets(10, 20, 10, 20); // top padding

	lblText1 = new JLabel(
		"<html><body><b> Welcome to Conversion Tools!</b>");
	lblText1.setPreferredSize(new Dimension(300, 20));
	panel.add(lblText1, GBC);

	GBC.gridx = 0;
	GBC.gridy = 1;
	lblText2 = new JLabel("Please select one from the following task:");
	lblText2.setPreferredSize(new Dimension(300, 20));
	panel.add(lblText2, GBC);

	GBC.weightx = 0.5;
	GBC.gridx = 0;
	GBC.gridy = 2;
	btnF2U = new JButton(
		"<html><body>Convert from non-Unicode font<br>to <b>Unicode</b>");
	btnF2U.setPreferredSize(new Dimension(300, 60));
	btnF2U.setIcon(imgF2U);
	panel.add(btnF2U, GBC);

	GBC.gridx = 1;
	btnU2F = new JButton(
		"<html><body>Convert from Unicode to non-Unicode<br><b>Font</b> (like Preeti)");
	btnU2F.setPreferredSize(new Dimension(300, 60));
	btnU2F.setIcon(imgU2F);
	panel.add(btnU2F, GBC);

	GBC.gridx = 0;
	GBC.gridy = 3;
	btnTraslit = new JButton(
		"<html><body>Convert from Devanagari to <br><b>Roman</b> transliteration");
	btnTraslit.setPreferredSize(new Dimension(300, 60));
	btnTraslit.setIcon(imgTranslit);
	panel.add(btnTraslit, GBC);

	GBC.gridx = 1;
	btnExit = new JButton("<html><body><b>Exit</b> from the Application");
	btnExit.setPreferredSize(new Dimension(300, 60));
	btnExit.setIcon(imgExit);
	panel.add(btnExit, GBC);

	add(panel);
    }
}
