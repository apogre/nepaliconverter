package np.org.npp.conv.file;

import java.io.IOException;



public class FileAutodetectConverter extends FileConverter {
  public String name() { return "(autodetect)"; }

  public void convert() throws IOException {
    lastConversionRes = "not implemented";
  }
};

