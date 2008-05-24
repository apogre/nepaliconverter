/**
 * 
 */
package np.org.mpp.cd.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import np.org.mpp.cd.ui.lnf.LnF;

/**
 * @author Abhishek
 * 
 */
public class RadioPanel extends JPanel implements ItemListener {
    private static final long serialVersionUID = -6505653191566798575L;
    private ButtonGroup group;
    int index;

    public RadioPanel(String[] labels) {
	index = 0;
	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	group = new ButtonGroup();

	int num = labels.length;
	JRadioButton[] radio = new JRadioButton[num];
	for (int i = 0; i < num; i++) {
	    String label = labels[i];

	    radio[i] = new JRadioButton(label);
	    radio[i] = new JRadioButton(label);
	    group.add(radio[i]);
	    add(radio[i], "Center");
	    radio[i].addItemListener(this);
	    radio[i].setActionCommand("" + i);
	}
	radio[0].setSelected(true);
    }

    @Override
    public void itemStateChanged(ItemEvent i) {
	JRadioButton radio = (JRadioButton) i.getSource();
	try {
	    if (i.getStateChange() == ItemEvent.SELECTED) {
		setOptionIndex(Integer.parseInt(radio.getActionCommand()));
		try {
		    UIManager.setLookAndFeel(LnF.laf[index].getClassName());
		    SwingUtilities.updateComponentTreeUI(this);
		    SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception x) {
		    System.err
			    .println("Sorry, could install the requested theme!");
		}
	    }
	} catch (Exception e) {
	}
    }

    public int getOptionIndex() {
	return index;
    }

    public void setOptionIndex(int index) {
	this.index = index;
    }
}