package np.org.mpp.conv4.utils;

import javax.swing.ProgressMonitor;

public interface GeneralReaderWriter {
    /**
     * Converts a file f.ex.: from Preeti font to Unicode, translatiterates it or something else.
     * If the conversionHandler didnt change anytext the the output file will NOT be written.
     * @param inFile Input file name
     * @param outFile Output file name to be created. If it exists it will be overwritten. Only if the conversionHandler actually changed something the file will be written.
     * @param conversionHandler The ConversionHandler which will be applied on all text in the file
     * @throws Exception
     */
    public void convert(String inFile, String outFile, ConversionHandler conversionHandler) throws Exception;

    /**
     * If you want to monitor the prograss then a ProgressMonitor can be informed
     * @param b ProgressMonitor
     */
    public void setProgressListener(ProgressMonitor b);
}
