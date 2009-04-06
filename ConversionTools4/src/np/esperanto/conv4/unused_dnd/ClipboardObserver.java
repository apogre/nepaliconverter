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


import java.awt.datatransfer.*;
import java.io.IOException;
import java.awt.event.*;
import java.util.*;


/**
 * An observer class for the clipboard
 * Inspired from http://code.google.com/p/witspirit/source/browse/trunk/FlickrStore/src/be/vanvlerken/bert/flickrstore/clipboardobserver
 * <p>Title: Nepaliconverter</p>
 *
 * <p>Description: Conversion tools for Nepali and devanagari text</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: Madan Puraskar Pustakalaya</p>
 *
 * @author Jacob Nordfalk
 * @version 4.0
 */
public class ClipboardObserver implements Runnable {

    private static final int DEFAULT_SLEEP_TIME = 300;
    private final Clipboard clipboard;
    private final DataFlavor observedFlavor;
    private boolean observing;
    private Object lastData; // Stores what we have last read, to see if anything has changed


    public ClipboardObserver(Clipboard clipboard, DataFlavor observedFlavor) {
        this.clipboard = clipboard;
        this.observedFlavor = observedFlavor;
        start();
        java.awt.event.ActionListener l;
    }

    public synchronized void start() {
        observing = true;
        lastData = null;
        setActive(true);
        Thread observerThread = new Thread(this, "ClipboardObserver");
        observerThread.start();
    }

    public synchronized void stop() {
        observing = false;
    }

    public void run() {
        while (isObserving()) {
            if (clipboard.isDataFlavorAvailable(observedFlavor)) {
                try {
                    Object data = clipboard.getData(observedFlavor);
                    if (isNewData(data)) {
                        lastData = data;
                        //System.out.println(" ClipboardEvent event = new ClipboardEvent(this, data);"+this+" "+data);

                        if (active) {
                          for (ActionListener l : listeners) {
                            l.actionPerformed(new ActionEvent(data, 0, observedFlavor.toString()));
                          }
                        }
                    }
                } catch (UnsupportedFlavorException e) {
                    // If this occurs, it actually means that the clipboard has been modified after our check...
                    // We do not care, we just ignore the contents in that case.
                } catch (Exception e) {
                    // This means we attempted to read valid data, but had a problem...
                    // Let's just report barebones, but proceed as normal afterwards
                    //e.printStackTrace();
                }
            }

            try {
                Thread.sleep(DEFAULT_SLEEP_TIME);
            } catch (InterruptedException e) {
                // Just waking up...
            }
        }
    }

    private boolean isNewData(Object data) {
        return data != null && !data.equals(lastData);
    }


    private boolean active = false;
    public void setActive(boolean a) {
        active = a;
        try {
            lastData = clipboard.getData(observedFlavor);
        } catch (Exception e) {
            // We ignore this altogether and assume an empty clipboard on the start
        }
    }


    public synchronized boolean isObserving() {
        return observing;
    }

    Set<ActionListener> listeners = new TreeSet<ActionListener>();

    public void addActionListener( ActionListener al) {
        listeners.add(al);
    }

}
