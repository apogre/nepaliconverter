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
package np.esperanto.conv4.unused_dnd;

import java.io.*;
import java.awt.datatransfer.*;

public class HtmlSelection implements Transferable, ClipboardOwner {

    private DataFlavor[] flavors;

    private String data;

    private static FilesAndHtmlTransferHandler hth  = new FilesAndHtmlTransferHandler();

    /**
     * Creates a <code>Transferable</code> capable of transferring
     * the specified <code>String</code>.
     */
    public HtmlSelection(String data) {
        this.data = data;
        flavors = new DataFlavor[] {hth.htmlDfByteUtf8 };
    }

    /**
     * Returns an array of flavors in which this <code>Transferable</code>
     * can provide the data. <code>DataFlavor.stringFlavor</code>
     * is properly supported.
     * Support for <code>DataFlavor.plainTextFlavor</code> is
     * <b>deprecated</b>.
     *
     * @return an array of length two, whose elements are <code>DataFlavor.
     *         stringFlavor</code> and <code>DataFlavor.plainTextFlavor</code>
     */
    public DataFlavor[] getTransferDataFlavors() {
        // returning flavors itself would allow client code to modify
        // our internal behavior
      return (DataFlavor[])flavors.clone();
    }

    /**
     * Returns whether the requested flavor is supported by this
     * <code>Transferable</code>.
     *
     * @param flavor the requested flavor for the data
     * @return true if <code>flavor</code> is equal to
     *   <code>DataFlavor.stringFlavor</code> or
     *   <code>DataFlavor.plainTextFlavor</code>; false if <code>flavor</code>
     *   is not one of the above flavors
     * @throws NullPointerException if flavor is <code>null</code>
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (int i = 0; i < flavors.length; i++) {
          if (flavor.equals(flavors[i])) {
              return true;
          }
      }
      return false;
    }

    /**
     * Returns the <code>Transferable</code>'s data in the requested
     * <code>DataFlavor</code> if possible. If the desired flavor is
     * <code>DataFlavor.stringFlavor</code>, or an equivalent flavor,
     * the <code>String</code> representing the selection is
     * returned. If the desired flavor is
     * <code>DataFlavor.plainTextFlavor</code>,
     * or an equivalent flavor, a <code>Reader</code> is returned.
     * <b>Note:</b> The behavior of this method for
     * <code>DataFlavor.plainTextFlavor</code>
     * and equivalent <code>DataFlavor</code>s is inconsistent with the
     * definition of <code>DataFlavor.plainTextFlavor</code>.
     *
     * @param flavor the requested flavor for the data
     * @return the data in the requested flavor, as outlined above
     * @throws UnsupportedFlavorException if the requested data flavor is
     *         not equivalent to either <code>DataFlavor.stringFlavor</code>
     *         or <code>DataFlavor.plainTextFlavor</code>
     * @throws IOException if an IOException occurs while retrieving the data.
     *         By default, HtmlSelection never throws this exception, but a
     *         subclass may.
     * @throws NullPointerException if flavor is <code>null</code>
     * @see java.io.Reader
     */
    public Object getTransferData(DataFlavor flavor)
        throws UnsupportedFlavorException, IOException
    {
      if (flavor.equals(hth.htmlDfByteUtf8)) {
          return data.getBytes("UTF-8");
      }

      new IllegalStateException("Unsuported flavor: "+flavor).printStackTrace();
      return data.getBytes("UTF-8");

    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("lostOwnership()");
    }
}
