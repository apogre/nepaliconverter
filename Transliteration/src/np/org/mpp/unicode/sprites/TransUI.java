package np.org.mpp.unicode.sprites;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TransUI implements ActionListener {

	JFrame frame;
	JLabel lblData;
	JLabel lblTrans;
	JLabel lblMessage;

	JTextField txtData;
	JTextField txtTrans;

	JButton btnTrans;
	JButton btnExit;

	public TransUI() {
		initComponents();
		addListeners();
		addComponents();

	}

	private void initComponents() {
		frame = new JFrame("Transliteration");

		lblData = new JLabel("Text to be transliterated:");
		lblTrans = new JLabel("Transliterated text:");
		lblMessage = new JLabel(" [Press Ctrl+v to paste transliterated text]");

		txtData = new JTextField();
		txtTrans = new JTextField();
		txtTrans.setEditable(false);

		btnTrans = new JButton("Transliterate");
		btnExit = new JButton("Exit");

		frame.setLayout(null);

		lblData.setBounds(5, 5, 200, 25);
		txtData.setBounds(165, 5, 350, 25);

		lblTrans.setBounds(5, 35, 200, 25);
		txtTrans.setBounds(165, 35, 350, 25);

		lblMessage.setBounds(5, 65, 310, 25);

		btnTrans.setBounds(270, 65, 120, 25);
		btnExit.setBounds(400, 65, 110, 25);
	}

	private void addListeners() {
		btnExit.addActionListener(this);
		btnTrans.addActionListener(this);
	}

	private void addComponents() {
		frame.getRootPane().setDefaultButton(btnTrans);

		frame.add(lblData);
		frame.add(lblTrans);
		frame.add(lblMessage);
		frame.add(txtData);
		frame.add(txtTrans);
		frame.add(btnTrans);
		frame.add(btnExit);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnExit){
			System.exit(0);
		}else if(event.getSource() == btnTrans){
			String data = txtData.getText();
			if(! data.equals("")){
				NepaliTransliteration transliterator  = new NepaliTransliteration();
				String trans = transliterator.transliterate(data);
				txtTrans.setText(trans);
				StringSelection ss = new StringSelection(trans);
		        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			}else{
				JOptionPane.showMessageDialog(null, "Please supply some text", "No data", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	void createAndShowGUI() {
		frame.setLocation(365, 100);
                frame.pack();
		frame.setSize(530, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
