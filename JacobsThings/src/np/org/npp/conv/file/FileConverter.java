package np.org.npp.conv.file;


import java.io.*;
import java.nio.charset.*;

import np.org.npp.conv.string.*;




public abstract class FileConverter {
  String lastConversionParms;
  public String lastConversionRes;
  public Font2Unicode font2Unicode = new Font2Unicode();

  public Parameters p = new Parameters();

  public static class Parameters {
    public Charset inEnc;
    public String inFile;
    public String inFont;
    public Charset outEnc;
    public String outFile;
    public int maxLen = Integer.MAX_VALUE;
  }

  public String name() { // default: Class name
    return getClass().getName().substring(getClass().getPackage().getName().length()+1);
  }



  public void convertCheckCache() throws IOException {
    String xx = p.inEnc + p.inFile + p.inFont;
    if (xx.equals(lastConversionParms)) return;
    lastConversionParms = xx;
    System.out.println("convert(" + lastConversionParms);
    lastConversionRes = "";
    convert();
  }

  public abstract void convert() throws IOException;

}











