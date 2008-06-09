package np.org.mpp.conv4.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import np.org.mpp.conv4.ConversionTools;

public class ConversionPanel extends JPanel {
    private static final long serialVersionUID = 6407038654026984919L;
    public static JEditorPane console;

    public ConversionPanel() {

	console = new JEditorPane();
	JScrollPane scroller = new JScrollPane(console);

	scroller.setPreferredSize(new Dimension(MainFrame.WIDTH - 10,
		MainFrame.HEIGHT - 75));

	console.setEditable(false);
	console.setText(ConversionTools.initLog);

	console.setBackground(Color.WHITE);
	console.setPreferredSize(new Dimension(MainFrame.WIDTH - 10,
		MainFrame.HEIGHT - 75));
	console.setBorder(new EtchedBorder(2));

	setLayout(new BorderLayout());
	// setBorder(new EmptyBorder(2, 2, 2, 2));

	add(scroller, BorderLayout.CENTER);
    }
}
