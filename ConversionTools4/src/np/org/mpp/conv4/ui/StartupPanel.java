/**
 * 
 */
package np.org.mpp.conv4.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import np.org.mpp.conv4.ConversionTools;
import np.org.mpp.ui.WidgetFactory;

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
	setPreferredSize(new Dimension(MainFrame.WIDTH, MainFrame.HEIGHT));
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
		"<html><body><b> Wel come to Conversion Tools!</b>");
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
	btnF2U.addActionListener(this);
	panel.add(btnF2U, GBC);

	GBC.gridx = 1;
	btnU2F = new JButton(
		"<html><body>Convert from Unicode to non-Unicode<br><b>Font</b> (like Preeti)");
	btnU2F.setPreferredSize(new Dimension(300, 60));
	btnU2F.setIcon(imgU2F);
	btnU2F.addActionListener(this);
	panel.add(btnU2F, GBC);

	GBC.gridx = 0;
	GBC.gridy = 3;
	btnTraslit = new JButton(
		"<html><body>Convert from Devanagari to <br><b>Roman</b> transliteration");
	btnTraslit.setPreferredSize(new Dimension(300, 60));
	btnTraslit.setIcon(imgTranslit);
	btnTraslit.addActionListener(this);
	panel.add(btnTraslit, GBC);

	GBC.gridx = 1;
	btnExit = new JButton("<html><body><b>Exit</b> from the Application");
	btnExit.setPreferredSize(new Dimension(300, 60));
	btnExit.setIcon(imgExit);
	btnExit.addActionListener(this);
	panel.add(btnExit, GBC);

	add(panel);
    }

    public void actionPerformed(ActionEvent e) {
	String mode = "";
	if (e.getSource() == btnF2U) {
	    mode = "Font To Unicode";
	    loadRequiredPanels();
	} else if (e.getSource() == btnU2F) {
	    mode = "Unicode To Font";
	    loadRequiredPanels();
	} else if (e.getSource() == btnTraslit) {
	    mode = "Trasnliteration";
	    loadRequiredPanels();
	} else if (e.getSource() == btnExit) {
	    System.out.println("Exiting from system!");
	    System.exit(0);
	}
	StatusBar.lblStatus.setText(" Mode: " + mode);
    }

    private void loadRequiredPanels() {
	setVisible(false);
	ConversionTools.frame.remove(MainFrame.startPanel);
	MainFrame.toolBar.setVisible(true);
	MainFrame.statusBar.setVisible(true);

	ConversionTools.frame.add(MainFrame.conversionPanel);
	MainFrame.conversionPanel.setVisible(true);
	ConversionPanel.console.setText(ConversionTools.initLog);
    }
}
