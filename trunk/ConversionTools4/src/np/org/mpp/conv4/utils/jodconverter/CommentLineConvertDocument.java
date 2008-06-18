package np.org.mpp.conv4.utils.jodconverter;

//
// JODConverter - Java OpenDocument Converter
// Copyright (C) 2004-2007 - Mirko Nasato <mirko@artofsolving.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// http://www.gnu.org/copyleft/lesser.html
//

import java.io.File;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;

/**
 * Command line tool to convert a document into a different format.
 * <p>
 * Usage: can convert a single file
 *
 * <pre>
 * ConvertDocument test.odt test.pdf
 * </pre>
 *
 * or multiple files at once by specifying the output format
 *
 * <pre>
 * ConvertDocument -f pdf test1.odt test2.odt
 * ConvertDocument -f pdf *.odt
 * </pre>
 */
public class CommentLineConvertDocument {

  private static final Option OPTION_OUTPUT_FORMAT = new Option("f", "output-format", true, "output format (e.g. pdf)");
  private static final Option OPTION_PORT = new Option("p", "port", true, "OpenOffice.org port");
  private static final Option OPTION_VERBOSE = new Option("v", "verbose", false, "verbose");
  private static final Options OPTIONS = initOptions();

  private static final int EXIT_CODE_CONNECTION_FAILED = 1;
  private static final int EXIT_CODE_TOO_FEW_ARGS = 255;

  private static Options initOptions() {
    Options options = new Options();
    options.addOption(OPTION_OUTPUT_FORMAT);
    options.addOption(OPTION_PORT);
    options.addOption(OPTION_VERBOSE);
    return options;
  }

  public static void main(String[] arguments) throws Exception {
    CommandLineParser commandLineParser = new PosixParser();
    CommandLine commandLine = commandLineParser.parse(OPTIONS, arguments);

    int port = SocketOpenOfficeConnection.DEFAULT_PORT;
    if (commandLine.hasOption(OPTION_PORT.getOpt())) {
      port = Integer.parseInt(commandLine.getOptionValue(OPTION_PORT.getOpt()));
    }

    String outputFormat = null;
    if (commandLine.hasOption(OPTION_OUTPUT_FORMAT.getOpt())) {
      outputFormat = commandLine.getOptionValue(OPTION_OUTPUT_FORMAT.getOpt());
    }

    boolean verbose = true;
    if (commandLine.hasOption(OPTION_VERBOSE.getOpt())) {
      verbose = true;
    }

    String[] fileNames = commandLine.getArgs();
    if ((outputFormat == null && fileNames.length != 2) || fileNames.length < 1) {
      String syntax = "convert [options] input-file output-file; or\n"
                      + "[options] -f output-format input-file [input-file...]";
      HelpFormatter helpFormatter = new HelpFormatter();
      helpFormatter.printHelp(syntax, OPTIONS);
      System.exit(EXIT_CODE_TOO_FEW_ARGS);
    }

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
      System.exit(EXIT_CODE_CONNECTION_FAILED);
    }
    try {
      DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
      if (outputFormat == null) {
        File inputFile = new File(fileNames[0]);
        File outputFile = new File(fileNames[1]);
        convertOne(converter, inputFile, outputFile, verbose);
      } else {
        for (int i = 0; i < fileNames.length; i++) {
          File inputFile = new File(fileNames[i]);
          File outputFile = new File(FilenameUtils.getFullPath(fileNames[i])
                                     + FilenameUtils.getBaseName(fileNames[i]) + "." + outputFormat);
          convertOne(converter, inputFile, outputFile, verbose);
        }
      }
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
