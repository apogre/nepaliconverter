/*
 * @(#)Preeti.java	2006-2007
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.u2f.map;

/**
 * 
 * @author Prajol Shrestha
 * @modified Abhishek Shrestha
 * @date 2007-2008
 */
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.JOptionPane;

import np.org.mpp.cd.u2f.UnicodeToFontGUI;

public class Preeti {
    private boolean taskDone = false;
    private String inFile;
    private String outFile;
    private String inEncoding;

    private String input;
    private StringBuffer inputBuffer;

    private static int p, len, ref;
    private static StringBuffer outp;

    private final String VALID_EXTENSION = "txt";
    private final String SUFFIX = "_font";
    private int initProgress;
    int progress = 0;
    private float unitBlock;
    String fileName;

    public Preeti(String[] args) {
	File file = new File(args[0]);
	fileName = file.getName();
	inFile = file.getAbsolutePath();

	String[] fragment = inFile.split("\\.");
	int count = fragment.length;
	String fileName = fragment[0];
	String ext = fragment[count - 1];

	if (ext.equals(VALID_EXTENSION)) {
	    outFile = fileName + SUFFIX + "." + VALID_EXTENSION;
	}
	inEncoding = args[1];
	unitBlock = Float.parseFloat(args[2]);
	initProgress = Integer.parseInt(args[3]);

	convert();
    }

    private void convert() {
	ref = 0;
	try {
	    File file = new File(inFile);

	    InputStream filereader1 = new BufferedInputStream(
		    new DataInputStream(new FileInputStream(file)));

	    byte[] data = new byte[(int) file.length()];

	    filereader1.read(data);
	    input = new String(data, inEncoding);
	    inputBuffer = new StringBuffer(input);

	    filereader1.close();

	    len = inputBuffer.length();
	    p = 0;
	    char ch;
	    outp = new StringBuffer(0);

	    while (p < len) {
		ch = inputBuffer.charAt(p);
		outp.append(value(ch));
		p++;
		float progressF = (p + initProgress) / unitBlock;
		progress = (int) progressF;

		if (p == input.length()) {
		    taskDone(true);
		    JOptionPane.showMessageDialog(null, fileName
			    + " has been successfully converted.!!!",
			    "Conversion complete!",
			    JOptionPane.INFORMATION_MESSAGE);
		} else {
		    taskDone(false);
		}
		updateProgressBar(progress);
	    }

	    Writer out = new BufferedWriter(new OutputStreamWriter(
		    new FileOutputStream(outFile), inEncoding));
	    out.write(outp.toString());
	    out.close();

	    System.out.println("Finished....");

	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!",
		    "Unsupported Encoding", JOptionPane.INFORMATION_MESSAGE);
	} catch (IOException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!", "Failure",
		    JOptionPane.INFORMATION_MESSAGE);
	} catch (StringIndexOutOfBoundsException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!", "Failure",
		    JOptionPane.INFORMATION_MESSAGE);
	} catch (Exception e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!", "Failure",
		    JOptionPane.INFORMATION_MESSAGE);
	}
    }

    private boolean taskDone(boolean taskStatus) {
	taskDone = taskStatus;
	return taskDone;
    }

    private void updateProgressBar(int progress) {
	UnicodeToFontGUI.progressBar.setValue(progress);
	UnicodeToFontGUI.progressBar.updateUI();
	UnicodeToFontGUI.progressBar.repaint();
    }

    public String value(char ascii_value) {
	String unicode_string, tmp;
	// char a;
	switch (ascii_value) {

	case '\u0009': // "\t" tabSpace
	    unicode_string = String.valueOf((char) 9);
	    break;
	case '\u002d': // "-" hyphen
	    unicode_string = String.valueOf((char) 173);
	    break;
	case '\u0020': // " " space
	    unicode_string = String.valueOf((char) 32);
	    break;
	case '\u003a':// "â€Œ"
	    unicode_string = String.valueOf((char) 77);
	    break;
	case '\n': // "/n" newline
	    unicode_string = String.valueOf((char) 10);
	    break;
	case '\u2028':
	    unicode_string = String.valueOf((char) 13);
	    break;
	// DEVNAGARI Unicode value starts from here:
	case '\u0901':// "â€Œà¤�"
	    unicode_string = String.valueOf((char) 70);
	    break;
	case '\u0902':// "à¤‚"
	    unicode_string = String.valueOf((char) 43);
	    break;
	case '\u0903':// "à¤ƒ"

	case '\u0905': // "à¤…"
	    unicode_string = String.valueOf((char) 99);
	    break;
	case '\u0906':// "à¤†"
	    unicode_string = String.valueOf((char) 99)
		    + String.valueOf((char) 102);
	    break;
	case '\u0907':// "à¤‡"
	    unicode_string = String.valueOf((char) 79);
	    break;
	case '\u0908':// "à¤ˆ"
	    unicode_string = String.valueOf((char) 79)
		    + String.valueOf((char) 123);
	    break;
	case '\u0909':// "à¤‰â€Œ"
	    unicode_string = String.valueOf((char) 112);
	    break;
	case '\u090a':// "à¤Šâ€Œ"
	    unicode_string = String.valueOf((char) 112)
		    + String.valueOf((char) 109);
	    break;
	case '\u090b':// "â€Œà¤‹"
	    unicode_string = String.valueOf((char) 67);
	    break;
	// '\u090c' '\u090d' '\u090e' not applicable to Nepali system
	case '\u090f':// "â€Œà¤�"
	    unicode_string = String.valueOf((char) 80);
	    break;
	case '\u0910':// "â€Œà¤�"
	    unicode_string = String.valueOf((char) 80)
		    + String.valueOf((char) 93);
	    break;
	// '\u0911' '\u0912' not applicable to Nepali system
	case '\u0913':// "â€Œà¤“"
	    unicode_string = String.valueOf((char) 99)
		    + String.valueOf((char) 102) + String.valueOf((char) 93);
	    break;
	case '\u0914':// "â€Œà¤”"
	    unicode_string = String.valueOf((char) 99)
		    + String.valueOf((char) 102) + String.valueOf((char) 125);
	    break;
	case '\u0915':// "à¤•"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') { // "à¥�"
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {// "à¤°"
		    unicode_string = String.valueOf((char) 106)// "à¤•à¥�à¤°"
			    + String.valueOf((char) 124)
			    + String.valueOf((char) 109);
		} else if (inputBuffer.charAt(p) == '\u0937') {// "à¤•à¥�à¤·"
		    p++;
		    if (inputBuffer.charAt(p) == '\u094d') {// "à¤•à¥ˆ"
			unicode_string = String.valueOf((char) 73);
		    } else {
			p--;
			unicode_string = String.valueOf((char) 73)
				+ String.valueOf((char) 102);
		    }
		} else if (inputBuffer.charAt(p) == '\u0924') {
		    unicode_string = String.valueOf((char) 81)
			    + String.valueOf((char) 109);
		}

		else {
		    unicode_string = String.valueOf((char) 83);
		    p--;
		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 115);
	    }

	    break;
	case '\u0916':// "â€Œà¤–"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 118)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 86);
		}
	    } else {
		unicode_string = String.valueOf((char) 118);
		p--;
	    }
	    break;
	case '\u0917':// "â€Œà¤—"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 117)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 85);
		}
	    } else {
		unicode_string = String.valueOf((char) 117);
		p--;
	    }

	    break;

	case '\u0918':// "â€Œà¤˜"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 51)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 163);
		}
	    } else {
		unicode_string = String.valueOf((char) 51);
		p--;
	    }

	    break;
	case '\u0919':// "â€Œà¤™"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0917') {
		    p++;
		    if (inputBuffer.charAt(p) == '\u094d') {
			p++;
			if (inputBuffer.charAt(p) == '\u0930') {
			    unicode_string = String.valueOf((char) 170)
				    + String.valueOf((char) 92)
				    + String.valueOf((char) 117)
				    + String.valueOf((char) 124);
			} else {
			    p -= 2;
			    unicode_string = String.valueOf((char) 203)
				    + String.valueOf((char) 92);
			}

		    } else {
			p--;
			unicode_string = String.valueOf((char) 203);
		    }

		} else if (inputBuffer.charAt(p) == '\u0915') {
		    unicode_string = String.valueOf((char) 205);

		} else if (inputBuffer.charAt(p) == '\u0916') {
		    unicode_string = String.valueOf((char) 206);

		} else if (inputBuffer.charAt(p) == '\u0922') {
		    unicode_string = String.valueOf((char) 176);

		} else {
		    p -= 2;
		    unicode_string = String.valueOf((char) 170);
		}
	    } else {
		unicode_string = String.valueOf((char) 170);
		p--;
	    }
	    break;
	case '\u091a':// "â€Œà¤š"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 114)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 82);
		}
	    } else {
		unicode_string = String.valueOf((char) 114);
		p--;
	    }

	    break;

	case '\u091b':// "à¤›â€Œ"

	    unicode_string = String.valueOf((char) 53);
	    break;
	case '\u091c':// "â€Œà¤œ"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 104)
			    + String.valueOf((char) 124);

		} else {
		    if (inputBuffer.charAt(p) == '\u091e') {
			unicode_string = String.valueOf((char) 49);
		    } else {
			p--;
			unicode_string = String.valueOf((char) 72);
		    }
		}
	    } else {
		unicode_string = String.valueOf((char) 104);
		p--;
	    }
	    break;
	case '\u091d':// "â€Œà¤�"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 101)
			    + String.valueOf((char) 124)
			    + String.valueOf((char) 109);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 180)
			    + String.valueOf((char) 92);

		}
	    } else {
		unicode_string = String.valueOf((char) 180);
		p--;
	    }
	    break;
	case '\u091e':// "â€Œà¤ž"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 96)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 126);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 96);
	    }
	    break;
	case '\u091f':// "à¤Ÿâ€Œ"
	    unicode_string = String.valueOf((char) 54);
	    break;
	case '\u0920':// "â€Œà¤ "
	    unicode_string = String.valueOf((char) 55);
	    break;
	case '\u0921':// "à¤¡â€Œ"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		/*
		 * if(inputBuffer.charAt(p)=='\u200d') { unicode_string =
		 * String.valueOf((char)56)+String.valueOf((char)92);
		 * 
		 * }else
		 */
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 56)
			    + String.valueOf((char) 171);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 56)
			    + String.valueOf((char) 92);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 56);
	    }
	    break;
	case '\u0922':// "à¤¢"
	    unicode_string = String.valueOf((char) 57);
	    break;
	case '\u0923':// "à¤£"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		unicode_string = String.valueOf((char) 48);
	    } else {
		p--;
		unicode_string = String.valueOf((char) 48)
			+ String.valueOf((char) 102);
	    }
	    break;
	case '\u0924':// "à¤¤"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0020') {
		    unicode_string = String.valueOf((char) 116)
			    + String.valueOf((char) 92)
			    + String.valueOf((char) 32);

		} else if (inputBuffer.charAt(p) == '\u0930') {
		    p++;
		    if (inputBuffer.charAt(p) == '\u093f') {
			unicode_string = String.valueOf((char) 108)
				+ String.valueOf((char) 113);

		    } else {
			p--;
			unicode_string = String.valueOf((char) 113);
		    }
		} else if (inputBuffer.charAt(p) == '\u0924') {

		    unicode_string = String.valueOf((char) 81);
		} else {
		    p--;
		    unicode_string = String.valueOf((char) 84);
		}

	    } else {
		p--;
		unicode_string = String.valueOf((char) 116);
	    }
	    break;
	case '\u0925':// "à¤¥"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 121)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 89);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 121);
	    }
	    break;
	case '\u0926':// "à¤¦"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0926') {
		    unicode_string = String.valueOf((char) 50);

		} else if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 98)
			    + String.valueOf((char) 124);

		} else if (inputBuffer.charAt(p) == '\u0935') {
		    unicode_string = String.valueOf((char) 229);
		} else if (inputBuffer.charAt(p) == '\u0927') {
		    unicode_string = String.valueOf((char) 52);
		} else if (inputBuffer.charAt(p) == '\u092e') {
		    unicode_string = String.valueOf((char) 223);
		} else {
		    p--;
		    unicode_string = String.valueOf((char) 98)
			    + String.valueOf((char) 92);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 98);
	    }
	    break;
	case '\u0927':// "à¤§"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 119)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 119)
			    + String.valueOf((char) 92);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 119);
	    }
	    break;
	case '\u0928':// "à¤¨"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u003b') {
		    unicode_string = String.valueOf((char) 103)
			    + String.valueOf((char) 92)
			    + String.valueOf((char) 217);
		} else if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 103)
			    + String.valueOf((char) 124);

		} else if (inputBuffer.charAt(p) == '\u0928') {
		    p++;
		    if (inputBuffer.charAt(p) == '\u093f') {
			unicode_string = String.valueOf((char) 108)
				+ String.valueOf((char) 204);
		    } else {
			p--;
			unicode_string = String.valueOf((char) 204);
		    }
		} else if (inputBuffer.charAt(p) == '\u0020'
			|| inputBuffer.charAt(p) == '\u0964'
			|| inputBuffer.charAt(p) == '\u002c') {
		    unicode_string = String.valueOf((char) 103)
			    + String.valueOf((char) 92);
		    p--;
		} else

		{
		    p--;
		    unicode_string = String.valueOf((char) 71);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 103);
	    }
	    break;
	// '\u0928' not applicable to Nepali system
	case '\u092a':// "à¤�"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 107)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 75);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 107);
	    }
	    break;
	case '\u092b':// "à¤«"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 107)
			    + String.valueOf((char) 124)
			    + String.valueOf((char) 109);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 107)
			    + String.valueOf((char) 92)
			    + String.valueOf((char) 109);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 107)
			+ String.valueOf((char) 109);
	    }
	    break;
	case '\u092c':// "à¤¬"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 97)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 65);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 97);
	    }
	    break;
	case '\u092d':// "à¤­"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 101)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 69);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 101);
	    }
	    break;
	case '\u092e':// "à¤®"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 100)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 68);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 100);
	    }
	    break;
	case '\u092f':// "à¤¯"
	    unicode_string = String.valueOf((char) 111);
	    break;
	case '\u0930':// // "à¤°"
	    p++;
	    if (inputBuffer.charAt(p) == '\u0942') {
		unicode_string = String.valueOf((char) 191);
	    } else if (inputBuffer.charAt(p) == '\u0941') {
		unicode_string = String.valueOf((char) 63);
	    } else if (inputBuffer.charAt(p + 1) == '\u200d') {
		unicode_string = String.valueOf((char) 165);
		p++;
	    } else if (inputBuffer.charAt(p) == '\u094d') {
		p += 2;
		if (inputBuffer.charAt(p) == '\u094d') {
		    p += 2;
		    if (inputBuffer.charAt(p) == '\u0940'
			    || inputBuffer.charAt(p) == '\u0947'
			    || inputBuffer.charAt(p) == '\u0948'
			    || inputBuffer.charAt(p) == '\u094b'
			    || inputBuffer.charAt(p) == '\u094c'
			    || inputBuffer.charAt(p) == '\u094e') {
			p = p - 3;
			unicode_string = value(inputBuffer.charAt(p))
				+ value(inputBuffer.charAt(p + 1))
				+ value(inputBuffer.charAt(p + 2))
				+ String.valueOf((char) 123);
			p += 2;
		    } else {
			unicode_string = value(inputBuffer.charAt(p - 3))
				+ value(inputBuffer.charAt(p - 1))
				+ String.valueOf((char) 123);
			p--;
		    }

		} else if (inputBuffer.charAt(p) == '\u200d') {
		    unicode_string = String.valueOf((char) 165);
		} else if (inputBuffer.charAt(p) == '\u093f') {
		    ref = 1;
		    unicode_string = "";
		    p -= 2;

		} else if (inputBuffer.charAt(p) == '\u0940'
			|| inputBuffer.charAt(p) == '\u0947'
			|| inputBuffer.charAt(p) == '\u0948'
			|| inputBuffer.charAt(p) == '\u094b'
			|| inputBuffer.charAt(p) == '\u094c'
			|| inputBuffer.charAt(p) == '\u094e') {
		    unicode_string = value(inputBuffer.charAt(p - 1))
			    + value(inputBuffer.charAt(p))
			    + String.valueOf((char) 123);
		} else {
		    p--;
		    unicode_string = value(inputBuffer.charAt(p))
			    + String.valueOf((char) 123);
		}

	    } else {
		p--;
		unicode_string = String.valueOf((char) 47);
	    }
	    break;
	case '\u0932':// "à¤²"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		unicode_string = String.valueOf((char) 78);
	    } else {
		p--;
		unicode_string = String.valueOf((char) 110);
	    }
	    break;
	// '\u0933' '\u0934' not applicable to Nepali system
	case '\u0935':// "à¤µ"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		unicode_string = String.valueOf((char) 74);

	    } else {
		p--;
		unicode_string = String.valueOf((char) 106);
	    }
	    break;
	case '\u0936':// "à¤¶"

	    if (inputBuffer.substring(p + 1, p + 3).compareTo("\u094d\u0930") == 0) {
		p += 2;
		unicode_string = String.valueOf((char) 62);
	    } else if (inputBuffer.charAt(p + 1) == '\u094d') {
		unicode_string = String.valueOf((char) 90);
		p++;
	    } else {
		unicode_string = String.valueOf((char) 122);
	    }
	    break;
	case '\u0937':// "à¤·"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		unicode_string = String.valueOf((char) 105);
	    } else {
		p--;
		unicode_string = String.valueOf((char) 105)
			+ String.valueOf((char) 102);
	    }
	    break;
	case '\u0938':// "à¤¸"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 59)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 58);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 59);
	    }
	    break;
	case '\u0939':// "à¤¹"
	    p++;
	    if (inputBuffer.charAt(p) == '\u094d') {
		p++;
		if (inputBuffer.charAt(p) == '\u0930') {
		    unicode_string = String.valueOf((char) 120)
			    + String.valueOf((char) 124);

		} else {
		    p--;
		    unicode_string = String.valueOf((char) 88);

		}
	    } else {
		p--;
		unicode_string = String.valueOf((char) 120);
	    }

	    break;
	// 'u093a' 'u093b' not mapped in the Unicode Table (as of Feb 13 2008)
	// 'u093c' 'u093d' applicable to Nepali system
	case '\u093e':// "à¤¾"
	    unicode_string = String.valueOf((char) 102);
	    break;

	case '\u093f':// "à¤¿"
	    p--;

	    if (inputBuffer.charAt(p) == '\u0930') {
		p--;
		if (inputBuffer.charAt(p) == '\u094d') {
		    p--;
		    if (inputBuffer.charAt(p) == '\u0915'
			    || inputBuffer.charAt(p) == '\u091d'
			    || inputBuffer.charAt(p) == '\u092b')// 3
		    // character
		    // kra
		    {
			tmp = outp.substring(outp.length() - 3, outp.length());
			outp.replace(outp.length() - 3, outp.length(), "");
			if (ref == 1) {
			    unicode_string = String.valueOf((char) 108) + tmp
				    + String.valueOf((char) 123);
			    ref = 0;
			} else {
			    unicode_string = String.valueOf((char) 108) + tmp;
			}
			p += 3;
		    } else// 2 character gra.
		    {
			tmp = outp.substring(outp.length() - 2, outp.length());
			outp.replace(outp.length() - 2, outp.length(), "");
			if (ref == 1) {
			    unicode_string = String.valueOf((char) 108) + tmp
				    + String.valueOf((char) 123);
			    ref = 0;
			} else {
			    unicode_string = String.valueOf((char) 108) + tmp;
			}

			p += 3;
		    }
		} else {
		    tmp = outp.substring(outp.length() - 1, outp.length());
		    outp.replace(outp.length() - 1, outp.length(), "");
		    if (ref == 1) {
			unicode_string = String.valueOf((char) 108) + tmp
				+ String.valueOf((char) 123);
			ref = 0;
		    } else {
			unicode_string = String.valueOf((char) 108) + tmp;
		    }
		    p += 2;
		}
	    } else

	    if (inputBuffer.charAt(p) == '\u0937'
		    || inputBuffer.charAt(p) == '\u0923') {
		tmp = outp.substring(outp.length() - 2, outp.length());
		outp.replace(outp.length() - 2, outp.length(), "");
		if (ref == 1) {
		    unicode_string = String.valueOf((char) 108) + tmp
			    + String.valueOf((char) 123);
		    ref = 0;
		} else {
		    unicode_string = String.valueOf((char) 108) + tmp;
		}
		p++;
		// System.out.println("done dena done");

	    } else {
		p--;
		if (inputBuffer.charAt(p) == '\u094d') {
		    p--;
		    if (inputBuffer.charAt(p) == '\u0930') {
			tmp = outp.substring(outp.length() - 1, outp.length());
			outp.replace(outp.length() - 1, outp.length(), "");
			if (ref == 1) {
			    unicode_string = String.valueOf((char) 108) + tmp
				    + String.valueOf((char) 123);
			    ref = 0;
			} else {
			    unicode_string = String.valueOf((char) 108) + tmp;
			}
			p += 3;
		    } else if (inputBuffer.charAt(p) == '\u0926') {
			tmp = outp.substring(outp.length() - 1, outp.length());
			outp.replace(outp.length() - 1, outp.length(), "");
			unicode_string = String.valueOf((char) 108) + tmp;
			p += 3;
		    } else {
			tmp = outp.substring(outp.length() - 2, outp.length());
			outp.replace(outp.length() - 2, outp.length(), "");
			if (ref == 1) {
			    unicode_string = String.valueOf((char) 108) + tmp
				    + String.valueOf((char) 123);
			    ref = 0;
			} else {
			    unicode_string = String.valueOf((char) 108) + tmp;
			}
			p += 3;
		    }
		} else {
		    tmp = outp.substring(outp.length() - 1, outp.length());
		    outp.replace(outp.length() - 1, outp.length(), "");
		    if (ref == 1) {
			unicode_string = String.valueOf((char) 108) + tmp
				+ String.valueOf((char) 123);
			ref = 0;
		    } else {
			unicode_string = String.valueOf((char) 108) + tmp;
		    }
		    p += 2;
		}
	    }
	    break;
	case '\u0940':// "à¥€"
	    unicode_string = String.valueOf((char) 76);
	    break;
	case '\u0941':// "à¥�"
	    unicode_string = String.valueOf((char) 39);
	    break;
	case '\u0942':// "à¥‚"
	    unicode_string = String.valueOf((char) 34);
	    break;
	case '\u0943':// "à¥ƒ"
	    unicode_string = String.valueOf((char) 91);
	    break;
	// '\u0944' '\u0945' '\u0946' not applicable to Nepali system
	case '\u0947':// "à¥‡"
	    unicode_string = String.valueOf((char) 93);
	    break;
	case '\u0948':// "à¥ˆ"
	    unicode_string = String.valueOf((char) 125);
	    break;
	// '\u0949' '\u094a' not applicable to Nepali
	case '\u094b':// "â€�â€�â€�"
	    unicode_string = String.valueOf((char) 102)
		    + String.valueOf((char) 93);
	    break;
	case '\u094c':// "à¥Œ"
	    unicode_string = String.valueOf((char) 102)
		    + String.valueOf((char) 125);
	    break;
	case '\u094d':// "à¥�"
	    unicode_string = String.valueOf((char) 92);
	    break;
	// '\u094e' '\u094f' not mapped in the Unicode Table (as of Feb 13 2008)
	case '\u0950':// "à¥�"
	    unicode_string = String.valueOf((char) 231);
	    break;
	// '\u0951' through '\u0963' not applicable to Nepali system
	case '\u0964':// "à¥¤"
	    unicode_string = String.valueOf((char) 46);
	    break;
	// '\u0965' not applicable to Nepali system
	case '\u0966':// "à¥¦"
	    unicode_string = String.valueOf((char) 33);
	    break;
	case '\u0967':// "à¥§"
	    unicode_string = String.valueOf((char) 33);
	    break;
	case '\u0968':// "à¥¨"
	    unicode_string = String.valueOf((char) 64);
	    break;
	case '\u0969':// "à¥©"
	    unicode_string = String.valueOf((char) 35);
	    break;
	case '\u096a':// "à¥ª"
	    unicode_string = String.valueOf((char) 36);
	    break;
	case '\u096b':// "à¥«"
	    unicode_string = String.valueOf((char) 37);
	    break;
	case '\u096c':// "à¥¬"
	    unicode_string = String.valueOf((char) 94);
	    break;
	case '\u096d':// "à¥­"
	    unicode_string = String.valueOf((char) 38);
	    break;
	case '\u096e':// "à¥®"
	    unicode_string = String.valueOf((char) 42);
	    break;
	case '\u096f':// "à¥¯"
	    unicode_string = String.valueOf((char) 40);
	    break;
	case '\u003f':// "?"
	    unicode_string = String.valueOf((char) 60);
	    break;
	case '\u002c':// ","
	    unicode_string = String.valueOf((char) 44);
	    break;
	case '\u0028':// "("
	    unicode_string = String.valueOf((char) 45);
	    break;
	case '\u0029':// ")"
	    unicode_string = String.valueOf((char) 95);
	    break;
	case '\u0030':// "("
	    unicode_string = String.valueOf((char) 41);
	    break;
	case '\u0021':// "!"
	    unicode_string = String.valueOf((char) 219);
	    break;
	case '\u200c':// ""
	    unicode_string = "";
	    break;
	case '\u200d':// ""
	    unicode_string = "";
	    break;
	case '\u003b':// ";"
	    unicode_string = String.valueOf((char) 217);
	    break;
	case '\u2018':// "'"
	    unicode_string = String.valueOf((char) 218);
	    break;
	case '\u2019':// "'"
	    unicode_string = String.valueOf((char) 218);
	    break;
	case '\u201d':// '"'
	    unicode_string = String.valueOf((char) 198);
	    break;
	case '\u201c':// ""
	    unicode_string = String.valueOf((char) 201);
	    break;
	case '\u0022':// '"'
	    unicode_string = String.valueOf((char) 230);
	    break;
	case '\u002e':// "."
	    unicode_string = String.valueOf((char) 61);
	    break;
	case '\'':// "'"
	    unicode_string = String.valueOf((char) 218);
	    break;
	case '\ufeff':// ""
	    unicode_string = "";
	    break;

	default:// ""
	    unicode_string = new Character(ascii_value).toString();
	    break;
	}
	return unicode_string;
    }
}
