package np.org.mpp.conv4.ui;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import np.org.mpp.ui.WidgetFactory;
import java.awt.event.*;
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
    ConversionPanel conversionPanelu2f = new ConversionPanel();
    ConversionPanel conversionPaneltrans = new ConversionPanel();

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

	toolBar = ToolBar.getInstance("Font2Unicode");
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

  //jPanelCards.add(converterPanelUnic, "converterPanelUnic");
  //jPanelCards.add(converterPanelFont, "converterPanelFont");
  //jPanelCards.add(converterPanelTranslit, "converterPanelTranslit");

      /*
	add(panelSwitcherPanel, BorderLayout.CENTER);

      panelSwitcherPanel.setLayout(panelSwitcher);
      panelSwitcher.addLayoutComponent(startPanel,"start");
      panelSwitcher.show(panelSwitcherPanel, "start");
*/

	add(statusBar, BorderLayout.SOUTH);

	setIconImage(widgetFactory.createImage("icon.png"));
	setPreferredSize(new Dimension(WIDTH, HEIGHT));
	setLocation(x, y);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	pack();


      startPanel.btnF2U.addActionListener(this);
      startPanel.btnU2F.addActionListener(this);
      startPanel.btnTraslit.addActionListener(this);
      startPanel.btnExit.addActionListener(this);

      toolBar.btnBack.addActionListener(this);
      toolBar.btnOpen.addActionListener(this);
      toolBar.btnPaste.addActionListener(this);
      toolBar.btnRun.addActionListener(this);
      toolBar.btnStop.addActionListener(this);

    }


    /*

        public void actionPerformed(ActionEvent e) {
          if (e.getSource() == btnBack) {
              MainFrame.toolBar.setVisible(false);
              MainFrame.statusBar.setVisible(false);
              MainFrame.conversionPanel.setVisible(false);
              ConversionTools.frame.add(MainFrame.startPanel);
              MainFrame.startPanel.setVisible(true);
          } else if (e.getSource() == btnOpen) {
              String filePath = "";
              try {
                filePath = widgetFactory.invokeJFileChooser(this);
                filePath = "You chose to open this file: " + filePath;
              } catch (FileNotSelectedException fe) {
                filePath = "Oops no file chosen!!!";
                // System.err.println(e.toString());
              }
              ConversionTools.appendLog(filePath);
          }
        }
    */



    String mode = "";

    public void switchToMode(String newMode) {
        if (mode == newMode) return;
        panelSwitcher.show(jPanelSwitcher, newMode);

        toolBar.setVisible( newMode != MODE_START );


        toolBar.cmbFont.setVisible(true);
        toolBar.cmbTrans.setVisible(true);

        if (newMode == MODE_F2U) {
            conversionPanelf2u.confgureMenus(toolBar, menuBar);
        } else if (newMode == MODE_U2F) {
            conversionPanelu2f.confgureMenus(toolBar, menuBar);
        } else if (newMode == MODE_TRANS) {
            conversionPaneltrans.confgureMenus(toolBar, menuBar);
        }


        mode = newMode;
    }

    public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();

      if (e.getSource() == startPanel.btnF2U) {
          switchToMode(MODE_F2U);
      } else if (e.getSource() == startPanel.btnU2F) {
          switchToMode(MODE_U2F);
      } else if (e.getSource() == startPanel.btnTraslit) {
          switchToMode(MODE_TRANS);
      } else if (e.getSource() == startPanel.btnExit) {
          System.out.println("Exiting from system!");
          System.exit(0);
      } else if (src == toolBar.btnBack) {
          switchToMode(MODE_START);
      }


      //StatusBar.lblStatus.setText(" Mode: " + mode);

/*
    private void loadRequiredPanels() {
      setVisible(false);
      ConversionTools.frame.remove(MainFrame.startPanel);
      MainFrame.toolBar.setVisible(true);
      MainFrame.statusBar.setVisible(true);

      ConversionTools.frame.add(MainFrame.conversionPanel);
      MainFrame.conversionPanel.setVisible(true);
      ConversionPanel.console.setText(ConversionTools.initLog);
    }
*/

    }
}
