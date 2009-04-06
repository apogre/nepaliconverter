/*
 * Copyright (C) 2009  Nepala Esperanto-Asocio, http://www.esperanto.org.np/
 * Author: Jacob Nordfalk
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */
package np.esperanto.conv4.unused_jodconverter;


import java.io.*;
import java.net.ConnectException;
import java.util.HashMap;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;




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
    File outputFile = new File("x.odt");

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
      //String cmd = "soffice -accept=\"sockethost=127.0.0.1,port=8100;urp;\"";
      String cmd = "soffice -headless -accept=\"sockethost=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
      Process p = Runtime.getRuntime().exec(cmd);
                                                  // -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
      try {

          InputStream std = p.getInputStream();
          InputStream err = p.getErrorStream();

          StringBuffer outsb = new StringBuffer(20);
          StringBuffer errsb = new StringBuffer(20);

          do {
            int ch =std.read();
            if (ch==-1) break;
            outsb.append( (char) ch);
          } while (true);

          do {
            int ch =err.read();
            if (ch==-1) break;
            errsb.append( (char) ch);
          } while (true);

          p.getOutputStream().close();
          std.close();
          err.close();

          System.out.println(cmd + "\n gave stdout: '" + outsb + "'");
          System.out.println("\n gave stderr: '" + errsb + "'");


          p.waitFor();
          Thread.sleep(5000);
      } catch (InterruptedException ex) {
          ex.printStackTrace();
      }
      if (verbose) {
        System.out.println("-- connecting to OpenOffice.org on port " + port);
      }
      connection.connect();
    } catch (Exception e) {

      System.err.println("ERROR: connection failed. Please make sure OpenOffice.org is installed ");

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
