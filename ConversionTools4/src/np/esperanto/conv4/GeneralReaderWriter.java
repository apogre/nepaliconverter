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

package np.esperanto.conv4;

import np.esperanto.conv4.ConversionHandler;
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
