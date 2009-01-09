package np.org.mpp.conv4.old_ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import np.org.mpp.conv4.old_ui.WidgetFactory;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1588375492735364352L;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    ToolBar toolBar;
    MenuBar menuBar;

    StartupPanel startPanel = new StartupPanel();
    ConversionPanelF2U conversionPanelf2u = new ConversionPanelF2U();
    ConversionPanelU2F conversionPanelu2f = new ConversionPanelU2F();
    ConversionPanelTrans conversionPaneltrans = new ConversionPanelTrans();

    StatusBar statusBar;
    WidgetFactory widgetFactory;


    JPanel jPanelSwitcher = new JPanel();
    CardLayout panelSwitcher = new CardLayout();

    static final String MODE_START = "start";
    static final String MODE_F2U = "f2u";
    static final String MODE_U2F = "u2f";
    static final String MODE_TRANS = "trans";

    public MainFrame() {
	super("Conversion Tools");
	widgetFactory = WidgetFactory.getInstance();
	setLayout(new BorderLayout(20, 2));

	// for positioning the window at the center of the screen
	int x = (screenSize.width - WIDTH) / 2;
	int y = (screenSize.height - HEIGHT) / 2;

	menuBar = MenuBar.getInstance();

	toolBar = ToolBar.getInstance();
	toolBar.setVisible(false);

	statusBar = new StatusBar();
	statusBar.setVisible(false);

	setJMenuBar(menuBar);

	add(toolBar, BorderLayout.NORTH);



  jPanelSwitcher.setLayout(panelSwitcher);

  add(jPanelSwitcher, java.awt.BorderLayout.CENTER);
  jPanelSwitcher.add(startPanel, MODE_START);
  jPanelSwitcher.add(conversionPanelf2u, MODE_F2U);
  jPanelSwitcher.add(conversionPanelu2f, MODE_U2F);
  jPanelSwitcher.add(conversionPaneltrans, MODE_TRANS);


	add(statusBar, BorderLayout.SOUTH);

	setIconImage(widgetFactory.createImage("icon.png"));
	setPreferredSize(new Dimension(WIDTH, HEIGHT));
	setLocation(x, y);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	pack();

      /*
      startPanel.btnF2U.addActionListener(this);
      startPanel.btnU2F.addActionListener(this);
      startPanel.btnTraslit.addActionListener(this);
      startPanel.btnExit.addActionListener(this);

      toolBar.btnBack.addActionListener(this);
      toolBar.btnOpen.addActionListener(this);
      toolBar.btnPaste.addActionListener(this);
      toolBar.btnRun.addActionListener(this);
      toolBar.btnStop.addActionListener(this);
      */
      addActionListeners(toolBar);
      addActionListeners(startPanel);
      addActionListeners(menuBar);
    }

    /**
     * Add this as actionlistener on all children and subchildren of this component.
     * (is't a hell to maintain manually :-)
     * @param container will get addActionListener( this ) its chuildren
     */
    private void addActionListeners(JComponent container) {
        for (int i=0; i<container.getComponentCount(); i++) {
            Component c = container.getComponent(i);
            if (c instanceof AbstractButton) {
                ((AbstractButton) c).addActionListener( this );
                //System.out.println("added "+c);
            }
            if (c instanceof JComponent) {
                addActionListeners((JComponent) c);
            }
        }
    }


    String currentMode = "";
    ConversionPanel currentConversionPanel = null;


    public void switchToMode(String newMode) {
        if (currentMode == newMode) return;
        panelSwitcher.show(jPanelSwitcher, newMode);

        toolBar.setVisible( newMode != MODE_START );


        toolBar.cmbFont.setVisible(true);
        toolBar.cmbTrans.setVisible(true);

        if (newMode == MODE_F2U) {
            currentConversionPanel = conversionPanelf2u;
        } else if (newMode == MODE_U2F) {
            currentConversionPanel = conversionPanelu2f;
        } else if (newMode == MODE_TRANS) {
            currentConversionPanel = conversionPaneltrans;
        }
        currentConversionPanel.confgureGui(this);

        currentMode = newMode;
        statusBar.lblStatus.setText(" Mode: " + currentMode);
    }


    public void actionPerformed(ActionEvent e) {
      System.out.println(""+e);
      Object s = e.getSource();

      if (s == toolBar.btnBack) switchToMode(MODE_START);
      else if (s == startPanel.btnF2U) switchToMode(MODE_F2U);
      else if (s == startPanel.btnU2F) switchToMode(MODE_U2F);
      else if (s == startPanel.btnTraslit) switchToMode(MODE_TRANS);
      else if (s == menuBar.mgoto_fontToUnicode) switchToMode(MODE_F2U);
      else if (s == menuBar.mgoto_unicodeToFont) switchToMode(MODE_U2F);
      else if (s == menuBar.mgoto_transliteration) switchToMode(MODE_TRANS);
      else if (s == menuBar.mhelpContents) JOptionPane.showMessageDialog(this, "Not ready ;-)");
      else if (s == menuBar.mprefs) JOptionPane.showMessageDialog(this, "Not ready ;-)");
      else if (s == menuBar.mseeSplash) JOptionPane.showMessageDialog(this, "Not ready ;-)");
      else if (s == startPanel.btnExit || s == menuBar.mexit) {
          System.out.println("Exiting from system!");
          System.exit(0);
      }


      // now for the signals to a specific conversion panel
      if (currentConversionPanel==null) return;

      if (s == toolBar.btnOpen) currentConversionPanel.openFileSelection();
      else if (s == menuBar.mopen) currentConversionPanel.openFileSelection();
      else if (s == toolBar.btnRun) currentConversionPanel.runFileConversion();
      else if (s == menuBar.mrun) currentConversionPanel.runFileConversion();
      else if (s == toolBar.btnStop) currentConversionPanel.stopRunningFileConversion();
      else if (s == menuBar.mstop) currentConversionPanel.stopRunningFileConversion();
      else if (s == toolBar.btnPaste) currentConversionPanel.convertClipboardAction();
      else if (s == menuBar.mpaste) currentConversionPanel.convertClipboardAction();
    }
}
