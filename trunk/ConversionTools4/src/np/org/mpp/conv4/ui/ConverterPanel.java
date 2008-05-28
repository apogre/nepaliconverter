package np.org.mpp.conv4.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class ConverterPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel jLabelConvTitle = new JLabel();
    JSplitPane jSplitPane1 = new JSplitPane();
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel jLabel1 = new JLabel();
    JButton jButtonPaste = new JButton();
    JLabel jLabel2 = new JLabel();
    JButton jButton2 = new JButton();
    JCheckBox jCheckBoxAutoconvert = new JCheckBox();
    JTextPane statusText = new JTextPane();
    JButton jButtonBack = new JButton();

    public ConverterPanel() {
        try {
            jbInit();
            this.setTransferHandler(transferhandler);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jLabelConvTitle.setFont(new java.awt.Font("SansSerif", Font.PLAIN, 24));
        jLabelConvTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelConvTitle.setText("Conversion to Unicode");
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);
        jPanel1.setLayout(flowLayout1);
        jLabel1.setText("Drag and drop file or text here, or");
        jButtonPaste.setActionCommand("jButtonPaste");
        jButtonPaste.setText("Paste");
        jLabel2.setText("(Ctrl-V) or ");
        jButton2.setActionCommand("jButtonSelectFile");
        jButton2.setText("Select file...");
        jCheckBoxAutoconvert.setText("Autoconvert clipboard text");
        statusText.setText("Status text comes here");
        jButtonBack.setText("Back");
        jButtonBack.addActionListener(new
                                      ConverterPanel_jButtonBack_actionAdapter(this));
        this.add(jLabelConvTitle, java.awt.BorderLayout.NORTH);
        this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jLabel1);
        jPanel1.add(jButtonPaste);
        jPanel1.add(jLabel2);
        jPanel1.add(jButton2);
        jPanel1.add(jCheckBoxAutoconvert);
        jPanel1.add(jButtonBack);
        jSplitPane1.add(statusText, JSplitPane.BOTTOM);
        jSplitPane1.add(jPanel1, JSplitPane.TOP);
        jSplitPane1.setDividerLocation(100);
    }

    public void jButtonBack_actionPerformed(ActionEvent e) {
        MainFrame.instance.cardLayout1.show(MainFrame.instance.jPanelCards, "introPanel");
    }



    private TransferHandler transferhandler = new TransferHandler() {
        public boolean canImport(TransferHandler.TransferSupport support) {
            /*
            //System.err.println("canImport("+support);
            if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                return false;
            }
            */

            support.setDropAction(COPY);

            return true;
        }

        public boolean importData(TransferHandler.TransferSupport support) {
            System.err.println("import("+support);
            if (!canImport(support)) {
                return false;
            }


            //support.getDataFlavors()
            Transferable t = support.getTransferable();


            DataFlavor[] f = support.getDataFlavors(); //.getTransferDataFlavors();
            for (int i = 0; i<f.length; i++) {
                System.err.println(" f["+i+"]="+f[i].toString()+" "+f[i].getHumanPresentableName());


                try {
                    System.err.println("  "+t.getTransferData(f[i]));
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            return true;
        }
    };


}


class ConverterPanel_jButtonBack_actionAdapter implements ActionListener {
    private ConverterPanel adaptee;
    ConverterPanel_jButtonBack_actionAdapter(ConverterPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonBack_actionPerformed(e);
    }
}
