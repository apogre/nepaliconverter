package np.org.mpp.conv4.ui.dnd;


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
        try {
            lastData = clipboard.getData(observedFlavor);
        } catch (Exception e) {
            // We ignore this altogether and assume an empty clipboard on the start
        }
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
                } catch (IOException e) {
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


    private boolean active = true;
    public void setActive(boolean a) {
        active = a;
    }


    public synchronized boolean isObserving() {
        return observing;
    }

    Set<ActionListener> listeners = new TreeSet<ActionListener>();

    public void addActionListener( ActionListener al) {
        listeners.add(al);
    }

}
