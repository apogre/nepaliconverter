/*
 * ConversionPanel.java
 *
 * Created on December 3, 2008, 12:10 PM
 */

package np.org.mpp.conv4.ui2;


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

/**
 *
 * @author  j
 */
public class ConversionPanel extends javax.swing.JPanel {
/*

 
 */
  
  
    /** Creates new form ConversionPanel */
    public ConversionPanel() {
        initComponents();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 300, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables



  void appendLog(String string) {
    System.out.println(string);
  }
    
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

    Preferences prefs = Preferences.userNodeForPackage(this.getClass()).node(this.getClass().getSimpleName());

    LinkedHashMap<File,File> inputOutputFiles = new LinkedHashMap<File,File>();
    

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
          inputOutputFiles.clear();
          for (File f2 : browse.getSelectedFiles()) {
            addToInputOutputFiles(f2, f2);
          }

          appendLog("Selected these files: " + inputOutputFiles);
      }
    }

  private void addToInputOutputFiles(File inf, File outf) {
        System.out.println("convertFile("+inf+" , "+outf);
        if (inf.isDirectory()) {
          if (outf.equals(inf)) {
            outf = new File(inf.getParentFile(), inf.getName()+CONVEXT);
            outf.mkdirs();
          }
          for (File f : inf.listFiles()) {
              addToInputOutputFiles(f, new File(outf, f.getName()));
          }
        } else {
            if (oooFileFilter.accept(inf)) {
                System.out.println("convertFile("+inf+" , "+outf);
                String fn = outf.getName();
                fn = FilenameUtils.getBaseName(fn) + CONVEXT + "." +FilenameUtils.getExtension(fn);
                outf = new File(outf.getParent(), fn);

                inputOutputFiles.put(inf, outf);              
            }
        }
  }



    public void runFileConversion() {
      if (inputOutputFiles.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Please select some files first");
      }
      getRightConversionHandler();
      for (Map.Entry<File,File>  e : inputOutputFiles.entrySet()) {
          convertFile(e.getKey(), e.getValue());
      }
    }

    String CONVEXT = "_unicode";

    void convertFile(File inf, File outf) {
        {
            {
            //if (allFileFilter.accept(inf)) {
                // convert!
                try {

                    if (outf.exists()) {
                        int retVal = JOptionPane.showConfirmDialog(this, "Would you like to overwrite "+outf+"?");
                        if (retVal == JOptionPane.CANCEL_OPTION) {
                            return;
                        }
                    }

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

    void getRightConversionHandler() {
        if (conversionHandler == null) {
          conversionHandler = new F2UConversionHandler();
          //documentReaderWriter = new OpenOfficeJacobsOldReaderWriter();
          documentReaderWriter = new OpenOfficeReaderWriter();
          //documentReaderWriter = new OpenOfficeReaderWriter();
          //documentReaderWriter = new AnyDocumentChainedReaderWriter(documentReaderWriter);
        }
    }


}