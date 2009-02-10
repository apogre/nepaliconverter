package np.org.mpp.old.old_ui;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.prefs.Preferences;
import org.apache.commons.io.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import np.org.mpp.conv4.utils.ConversionHandler;
import np.org.mpp.conv4.f2u.*;
import np.org.mpp.conv4.utils.GeneralReaderWriter;
import np.org.mpp.conv4.utils.odfdom.OpenOfficeReaderWriter;
import java.util.*;
import np.org.mpp.conv4.utils.jodconverter.AnyDocumentChainedReaderWriter;
import np.org.mpp.conv4.utils.*;

public class ConversionPanelF2U extends ConversionPanel {

    // COPY from AnyDocumentChainedReaderWriter
    public static HashMap<String,String> formats = new HashMap<String,String>();

    static {
        formats.put("doc", "odt");
        formats.put("sxw", "odt");
        formats.put("rtf", "odt");
        formats.put("wpd", "odt");
        formats.put("html", "odt");

        formats.put("xls", "ods");
        formats.put("sxc", "ods");

        formats.put("ppt", "odp");
        formats.put("sxi", "odp");

        formats.put("odg", "odg");
    }

    public class OooFileFilter extends FileFilter {
        Collection exts = formats.values();
        public boolean accept(File f) {
              return f.isDirectory() || exts.contains(FilenameUtils.getExtension(f.getName()));
        }
        public String getDescription() {
            return "Folders and OpenOffice files";
        }
    }

    public class AllFileFilter extends FileFilter {
        Collection exts = formats.keySet();
        public boolean accept(File f) {
              return f.isDirectory() || exts.contains(FilenameUtils.getExtension(f.getName()));
        }
        public String getDescription() {
            return "Folders and documents";
        }
    }

    FileFilter oooFileFilter = new OooFileFilter();
    FileFilter allFileFilter = new AllFileFilter();

    Preferences prefs = Preferences.userNodeForPackage(this.getClass()).node("f2u");

    File[] inputFiles;

    public void openFileSelection() {
      JFileChooser browse = new JFileChooser();
      browse.setFileFilter(oooFileFilter);
      //browse.setFileFilter(allFileFilter);
      browse.setApproveButtonText("Convert");
      browse.setMultiSelectionEnabled(true);
      browse.setFileSelectionMode(browse.FILES_AND_DIRECTORIES);

      String inDir = prefs.get("inDir",null);
      if (inDir != null) browse.setCurrentDirectory(new File(inDir));

      int returnVal = browse.showOpenDialog(this);
      if (returnVal == browse.APPROVE_OPTION) {
          File f = browse.getSelectedFile();
          if (!f.isDirectory()) f = f.getParentFile();
          prefs.put("inDir", f.getPath());

          inputFiles = browse.getSelectedFiles();

          appendLog("Selected these files: " + Arrays.asList(inputFiles));
      }
    }




    public void runFileConversion() {
      if (inputFiles == null) {
          JOptionPane.showMessageDialog(this, "Please select some files first");
      }
      getRightConversionHandler();
      for (File f : inputFiles) {
          convertFile(f, f);
      }
    }

    static String CONVEXT = "_unicode";

    private void convertFile(File inf, File outf) {
        System.out.println("convertFile("+inf+" , "+outf);
        if (inf.isDirectory()) {
          if (outf.equals(inf)) {
            outf = new File(inf.getParentFile(), inf.getName()+CONVEXT);
            outf.mkdirs();
          }
          for (File f : inf.listFiles()) {
              convertFile(f, new File(outf, f.getName()));
          }
        } else {
            if (oooFileFilter.accept(inf)) {
            //if (allFileFilter.accept(inf)) {
                // convert!
                try {
                    String fn = outf.getName();
                    fn = FilenameUtils.getBaseName(fn) + CONVEXT + "." +FilenameUtils.getExtension(fn);
                    outf = new File(outf.getParent(), fn);


                    if (outf.exists()) {
                        int retVal = JOptionPane.showConfirmDialog(this, "Would you like to overwrite "+outf+"?");
                        if (retVal == JOptionPane.CANCEL_OPTION) {
                            return;
                        }
                    }

                    appendLog("Converting "+inf+" to "+outf);

                    documentReaderWriter.convert(inf.getPath(), outf.getPath(), conversionHandler);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    int retVal = JOptionPane.showConfirmDialog(this, "An error orrured. Continue?\n"+ex);
                    if (retVal == JOptionPane.CANCEL_OPTION) {
                        throw new RuntimeException("Interrupted by user", ex);
                    }
                }
            }
        }
    }


    ConversionHandler conversionHandler; // initialization delayed to later
    GeneralReaderWriter documentReaderWriter;

    private void getRightConversionHandler() {
        if (conversionHandler == null) {
          conversionHandler = new F2UConversionHandler();
          documentReaderWriter = new OpenOfficeJacobsOldReaderWriter();
          //documentReaderWriter = new OpenOfficeReaderWriter();
          //documentReaderWriter = new AnyDocumentChainedReaderWriter(documentReaderWriter);
        }
    }


    public void stopRunningFileConversion() {
      appendLog("stop runConversion()!");
    }


    public ConversionPanelF2U() {
        appendLog("this is the F 2 U");
    }

    public void confgureGui(OldMainFrame mainFrame) {
        mainFrame.toolBar.cmbFont.setVisible(false);
        mainFrame.toolBar.cmbTrans.setVisible(false);
    }

}
