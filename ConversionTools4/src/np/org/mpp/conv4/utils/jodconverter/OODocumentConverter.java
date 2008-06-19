package np.org.mpp.conv4.utils.jodconverter;


import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import java.util.HashMap;




public class OODocumentConverter {

/*
  http://www.artofsolving.com/opensource/jodconverter/guide/supportedformats

 http://sourceforge.net/mailarchive/forum.php?thread_name=ef62c2be0710101347k74380e1cr9752a693ed8fdef0%40mail.gmail.com&forum_name=jodconverter-devel
 OOoConnection has a simple isConnected() method. If the connection goes
down - most likely because the OOo process crashed or you killed it - it
will be a notified. You can also register a listener.
AutoReconnectingOOoConnection decorates an OOoConnection to auto-restart the OOo process if it crashes.



*/
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

  public static String getCorrespondingOOformat(String ext) {
      return formats.get(ext);
  }

  public static void main(String[] arguments) throws Exception {
    File inputFile = new File("/home/j/esperanto/nepaliconverter/nepalitext/MS/ICOC/new Lang meterial/BLT2 Lesson 20-23 Review 2 240303.doc");
    File outputFile = new File("x.pdf");

    OODocumentConverter ooDocumentConverter = new OODocumentConverter();
    ooDocumentConverter.convert(inputFile, outputFile);
  }

  boolean verbose = true;
  int port = SocketOpenOfficeConnection.DEFAULT_PORT;

  public void convert(File inputFile, File outputFile) throws IOException {

    OpenOfficeConnection connection = new SocketOpenOfficeConnection(port);
    try {
      if (verbose) {
        System.out.println("-- connecting to OpenOffice.org on port " + port);
      }
      connection.connect();
    } catch (ConnectException officeNotRunning) {
      System.err
          .println("ERROR: connection failed. Please make sure OpenOffice.org is running and listening on port "
                   + port + ".");

      if (verbose) {
        System.out.println("-- starting OpenOffice.org on port " + port);
      }
      Process p = Runtime.getRuntime().exec("soffice -headless -accept=\"socket,port=8100;urp;\" -nofirststartwizard");
      // -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard

      try {
          p.waitFor();
          Thread.sleep(200);
      } catch (InterruptedException ex) {
          ex.printStackTrace();
      }
      if (verbose) {
        System.out.println("-- connecting to OpenOffice.org on port " + port);
      }
      connection.connect();
    } catch (Exception e) {

      System.err
          .println("ERROR: connection failed. Please make sure OpenOffice.org is installed ");

    }

    try {
      DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
      convertOne(converter, inputFile, outputFile, verbose);
    } finally {
      if (verbose) {
        System.out.println("-- disconnecting");
      }
      connection.disconnect();
    }
  }

  private static void convertOne(DocumentConverter converter, File inputFile, File outputFile, boolean verbose) {
    if (verbose) {
      System.out.println("-- converting " + inputFile + " to " + outputFile);
    }
    converter.convert(inputFile, outputFile);
  }
}