package np.org.mpp.conv4.utils.jodconverter;

import javax.swing.ProgressMonitor;

import java.util.*;
import org.apache.commons.io.*;
import java.io.*;
import np.org.mpp.conv4.utils.*;


/**
 * A ReaderWriter which uses jodConverter to convert files to/from
 * OOO file formats as needed before feeding to the real ReaderWriter
 * @author Jacob Nordfalk
 * @version 4.0
 */
public class AnyDocumentChainedReaderWriter implements GeneralReaderWriter {

  private GeneralReaderWriter chainedRw = null;
  private OODocumentConverter jodDocumentConverter = new OODocumentConverter();

  public Set supportedExtentions = new HashSet();

  private Set oooSupportedExtentions = new HashSet();


  public AnyDocumentChainedReaderWriter(GeneralReaderWriter rw) {
    chainedRw = rw;
    oooSupportedExtentions.addAll(jodDocumentConverter.formats.values());
    supportedExtentions.addAll(oooSupportedExtentions);
    supportedExtentions.addAll(jodDocumentConverter.formats.keySet());
  }


  public void convert(String inFile, String outFile, ConversionHandler conversionHandler) throws Exception {
    String orgInFile = inFile;
    String inExt = FilenameUtils.getExtension(inFile);
    String outExt = FilenameUtils.getExtension(outFile);

    if (!oooSupportedExtentions.contains(inExt)) {
      if (supportedExtentions.contains(inExt)) {

        String convInFile = "jodtemp." + jodDocumentConverter.formats.get(inExt);
        jodDocumentConverter.convert(new File(inFile), new File(convInFile));
        inFile = convInFile;
      } else {
        throw new IOException("File format not supported: " + inFile);
      }
    }

    String convOutFile = "jodtemp2." + FilenameUtils.getExtension(inFile);

    if (FilenameUtils.getExtension(convOutFile).equals(FilenameUtils.getExtension(outFile))) {
      // ext is direcly supported - just write to outout file
      convOutFile = outFile;
      chainedRw.convert(inFile, convOutFile, conversionHandler);
      if (!orgInFile.equals(inFile)) {
        new File(inFile).delete();
      }
    } else {
      // convert font, then use JODConverter to get right output format
      chainedRw.convert(inFile, convOutFile, conversionHandler);

      if (!oooSupportedExtentions.contains(outExt)) {
        if (supportedExtentions.contains(outExt)) {
          if (new File(convOutFile).exists()) { // file may not be there is there was no changes
            jodDocumentConverter.convert(new File(convOutFile), new File(outFile));
            new File(convOutFile).delete();
          }
        } else {
          // out file format not supported. Just rename and throw error
          String outFile2 = FilenameUtils.removeExtension(outFile) + "." + FilenameUtils.getExtension(convOutFile);
          boolean ok = new File(convOutFile).renameTo(new File(outFile2));
          throw new IOException("File format not supported: " + outFile
                                + (ok ? "\nFile written to " + outFile2 + " instead." : "\nFile written to " + convOutFile + " instead."));
        }
      }
    }
  }

  public void setProgressListener(ProgressMonitor b) {
    chainedRw.setProgressListener(b);
  }
}
