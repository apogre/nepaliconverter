package np.org.npp.conv.file;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;


public class TextfileConverter extends FileConverter {

  public String name() { return "plain text"; }


  String read() throws IOException {
    System.out.println("convert("+p.inEnc+","+p.inFile);
    FileInputStream fis = new FileInputStream(p.inFile);
    FileChannel fc = fis.getChannel();
    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,Math.min(fc.size(), p.maxLen));

    // here some logic is needed to make a good guess if (auto) is selected... for now just use default.
    if (p.inEnc==null) p.inEnc = Charset.defaultCharset();

    CharBuffer cb = p.inEnc.decode(bb);

    String txt = new String(cb.array());
    return txt;
  }



  public void convert() throws IOException {
    String s = read();
    StringBuffer sb = new StringBuffer(s.length());
    String[] sa = s.split("\n");
    for (String e: sa) sb.append('\n').append(font2Unicode.toUnicode(p.inFont, e));
    lastConversionRes = sb.toString().substring(1);
  }
};
