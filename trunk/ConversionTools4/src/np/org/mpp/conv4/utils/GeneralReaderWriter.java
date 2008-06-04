package np.org.mpp.conv4.utils;

import javax.swing.ProgressMonitor;

public interface GeneralReaderWriter {

  public void convert(String inFile, String outFile, ConversionHandler conversionHandler) throws Exception;

  public void setProgressListener(ProgressMonitor b);
}
