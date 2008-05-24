/*
 * @(#)Himali.java	2006-2007
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.f2u.map;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.JOptionPane;

public class Himali extends NonUniodeFont {
    int ascii_value;
    String unicode_string, flag, rahswa_fin, str;
    char tmp;

    public Himali(String[] args) {
	super(args);
	try {
	    InputStream fileReader = new BufferedInputStream(
		    new DataInputStream(new FileInputStream(getFile())));
	    StringBuffer output = new StringBuffer(1);
	    byte[] data = new byte[(int) getFile().length()];
	    int length = fileReader.read(data);

	    String input = new String(data, getInputEncoding());

	    int array[] = new int[10];

	    int p = 0;
	    tmp = input.charAt(p);
	    ascii_value = (int) tmp;
	    p++;

	    while (p < (length)) {
		flag = "yes";
		rahswa_fin = "no";

		// *******************************chya ra
		// anra********************************* //
		if (ascii_value == 73 || ascii_value == 48) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		    if (ascii_value == 102 || ascii_value == 70) {
			if (array[1] == 73) {
			    output.append("\u0915\u094d\u0937");
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			} else {
			    output.append("\u0923");
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			}
		    } else {
			output.append(value(array[1]));
		    }
		} else
		// ************************************a,o,aa,au************************************//

		if (ascii_value == 99) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;

		    if (ascii_value == 102 || ascii_value == 70) {// aa
			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
			if (ascii_value == 93) {// o
			    output.append("\u0913");
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			} else

			if (ascii_value == 125) {// au
			    output.append("\u0914");
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			} else {
			    output.append("\u0906");
			}
		    } else {
			output.append(value(array[1]));
		    }
		}

		else
		// ***************************************for
		// ref***************************//

		if (ascii_value == 123) {
		    int k = 1;
		    String str[] = new String[10];
		    str[k] = output.substring(output.length() - k, output
			    .length());
		    if (str[k].equals("\u0907")) {
			output.delete(output.length() - k, output.length());
			output.append("\u0908");

		    } else {
			if (str[k].equals("\u0901") || str[k].equals("\u0902")
				|| str[k].equals("\u094b")
				|| str[k].equals("\u093e")
				|| str[k].equals("\u093f")
				|| str[k].equals("\u0940")
				|| str[k].equals("\u0947")
				|| str[k].equals("\u0941")
				|| str[k].equals("\u0942")
				|| str[k].equals("\u0948")
				|| str[k].equals("\u094c")
				|| str[k].equals("\u0943")) {
			    k++;
			    str[k] = output.substring(output.length() - k,
				    output.length() - (k - 1));
			}
			k++;
			str[k] = output.substring(output.length() - k, output
				.length()
				- (k - 1));

			if (str[k].equals("\u094d")) {
			    k++;
			    str[k] = output.substring(output.length() - k,
				    output.length() - (k - 1));
			} else {
			    k--;
			}

			output.delete(output.length() - k, output.length());// output.substring(0,output.length()-k);
			output.append("\u0930\u094d");

			for (int m = k; m > 0; m--) {
			    output.append(str[m]);
			}
		    }

		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		} else
		// *************************************************for
		// Purnabiram and other
		// mark***************************************//
		if (ascii_value == 46 || ascii_value == 60
			|| ascii_value == 134) {
		    int k = 1;
		    String str[] = new String[10];
		    str[k] = output.substring(output.length() - k, output
			    .length());

		    if (str[k].equals("\u0020")) {
			output.delete(output.length() - k, output.length());
		    }
		    output.append(value(ascii_value));
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		} else
		// /***********************for kta in
		// yukta****************************************///
		if (ascii_value == 81) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;

		    if (ascii_value == 109) {

			output.append("\u0915\u094d\u0924");
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
		    } else {
			output.append(value(array[1]));
		    }

		} else

		// *************************************************for
		// Purnabiram and other
		// mark***************************************//
		if (ascii_value == 77) {
		    int k = 1;
		    String str[] = new String[10];
		    str[k] = output.substring(output.length() - k, output
			    .length());

		    if (str[k].equals("\u0020")) {
			output.delete(output.length() - k, output.length());
			output.append("\u003A");
		    } else
			output.append("\u0903");
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		} else
		/***************************************************************
		 * //////***************************for bisarga and column
		 * if(ascii_value==77) { array[1]=ascii_value; tmp =
		 * input.charAt(p-2); ascii_value=(int)tmp; //p++;
		 * 
		 * if(ascii_value!=32) { output.append("\u0903"); tmp =
		 * input.charAt(p);ascii_value=(int)tmp;p++; } else {
		 * output.append(value(array[1])); } } else
		 */

		// /*****************for dirga u********************************
		if (ascii_value == 112) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;

		    if (ascii_value == 109) {
			output.append("\u090a");
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
		    } else {
			output.append(value(array[1]));
		    }
		} else

		// //***************************tra****************************//
		if (ascii_value == 54) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		    if (ascii_value == 171) {
			output.append("\u091f\u094d\u0930");
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
		    } else {
			output.append(value(array[1]));
		    }
		} else

		// ***************************ngra******************//
		/*
		 * if(ascii_value==203){ array[1]=ascii_value; tmp =
		 * input.charAt(p);ascii_value=(int)tmp;p++;
		 * if(ascii_value==124){
		 * 
		 * output.append("\u0919\u094d\u0917\u094d\u0930"); tmp =
		 * input.charAt(p);ascii_value=(int)tmp;p++; } else{
		 * 
		 * output.append(value(array[1])); } } else
		 */

		// ****************************for pet chireko
		// sa**********************//
		if (ascii_value == 105) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		    if (ascii_value == 102 || ascii_value == 70) {
			output.append("\u0937");
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
		    } else {
			unicode_string = value(array[1]);
			output.append(value(array[1]));
			;
		    }
		} else

		// ********************************for
		// kra**************************//
		if (ascii_value == 113) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;

		    if (ascii_value == 109) {
			output.append("\u0915\u094d\u0930");
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
		    } else {
			output.append(value(array[1]));
			;
		    }
		} else

		// ********************************************** for pha,Jha,ka
		// and other**********************************************
		if (ascii_value == 107 || ascii_value == 106
			|| ascii_value == 101) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		    boolean boo = false;
		    boolean boo1 = false;
		    boolean boo2 = false;
		    boolean boo3 = false;
		    boolean boo4 = false;
		    int num = 1;
		    int pha_array[] = new int[50];
		    int f = 0;
		    int gh = 0;
		    gh = ascii_value;
		    f = p;
		    while (ascii_value == 34 || ascii_value == 39
			    || ascii_value == 92 || ascii_value == 91
			    || ascii_value == 93 || ascii_value == 123
			    || ascii_value == 125 || ascii_value == 70
			    || ascii_value == 43 || ascii_value == 124) {
			pha_array[num] = ascii_value;
			num++;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
			boo = true;
		    }
		    if (ascii_value == 109) {
			if (boo == true) {
			    for (int h = 1; h < num; h++) {
				if (pha_array[h] == 123) {
				    if (boo1 == true) {
					output.append("\u0930\u094d");
				    } else {
					if (array[1] == 107) {
					    output.append("\u0930\u094d\u092b");
					} else if (array[1] == 106) {
					    output.append("\u0930\u094d\u0915");
					} else if (array[1] == 101) {
					    output.append("\u0930\u094d\u091d");
					}

					boo1 = true;

				    }
				}
			    }

			    for (int h = 1; h < num; h++) {
				if (pha_array[h] == 124) {
				    if (boo2 == true || boo1 == true) {
					output.append("\u094d\u0930");
				    } else {
					if (array[1] == 107) {
					    output.append("\u092b\u094d\u0930");
					} else if (array[1] == 106) {
					    output.append("\u0915\u094d\u0930");
					} else if (array[1] == 101) {
					    output.append("\u091d\u094d\u0930");
					}

					boo2 = true;
				    }
				}
			    }

			    for (int h = 1; h < num; h++) {
				if (pha_array[h] == 92) {
				    if (boo2 == true || boo1 == true) {
					output.append("\u094d");
				    } else {
					if (array[1] == 107) {
					    output.append("\u092b\u094d");
					} else if (array[1] == 106) {
					    output.append("\u0915\u094d");
					} else if (array[1] == 101) {
					    output.append("\u091d\u094d");
					}
				    }
				}
			    }
			    for (int h = 1; h < num; h++) {
				if (pha_array[h] == 34 || pha_array[h] == 39
					|| pha_array[h] == 91
					|| pha_array[h] == 93
					|| pha_array[h] == 125) {
				    if (boo3 == true || boo2 == true
					    || boo1 == true) {
					output.append(value(pha_array[h]));
				    } else {
					if (array[1] == 107) {
					    output.append("\u092b"
						    + value(pha_array[h]));
					} else if (array[1] == 106) {
					    output.append("\u0915"
						    + value(pha_array[h]));
					} else if (array[1] == 101) {
					    output.append("\u091d"
						    + value(pha_array[h]));
					}
					boo3 = true;
				    }
				}
			    }

			    for (int h = 1; h < num; h++) {
				if (pha_array[h] == 43 || pha_array[h] == 70) {
				    if (boo4 == true || boo2 == true
					    || boo1 == true || boo3 == true) {
					output.append(value(pha_array[h]));
				    } else {
					if (array[1] == 107) {
					    output.append("\u092b"
						    + value(pha_array[h]));
					} else if (array[1] == 106) {
					    output.append("\u0915"
						    + value(pha_array[h]));
					} else if (array[1] == 101) {
					    output.append("\u091d"
						    + value(pha_array[h]));
					}

					boo4 = true;
				    }
				}
			    }
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			} else if (boo != true) {
			    p = f;
			    if (array[1] == 107) {
				output.append("\u092b");
			    } else if (array[1] == 106) {
				output.append("\u0915");
			    } else if (array[1] == 101) {
				output.append("\u091d");
			    }

			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			}
		    } else if (ascii_value != 109) {
			p = f;
			output.append(value(array[1]));
			;
			ascii_value = gh;
		    }
		} else

		// **************for aae********************//
		if (ascii_value == 80) {
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		    if (ascii_value == 93) {
			output.append("\u0910");
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
		    } else {
			output.append(value(array[1]));
			;
		    }
		}
		/*
		 * else
		 * 
		 * if(ascii_value==79) {// raswa e and dirgha e array[1] =
		 * ascii_value; tmp = input.charAt(p);ascii_value=(int)tmp;p++;
		 * 
		 * if(ascii_value==123) { output.append("\u0908"); tmp =
		 * input.charAt(p);ascii_value=(int)tmp;p++; } else {
		 * output.append("\u0907"); } }
		 */

		else

		if (ascii_value == 102) {// for okar and aukar //
		    array[1] = ascii_value;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		    if (ascii_value == 93 || ascii_value == 125) {
			if (ascii_value == 93) {
			    output.append("\u094b");
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			} else {
			    output.append("\u094c");
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			}
		    } else {
			output.append(value(array[1]));
			;
		    }
		} else

		// **************************************for rashwa
		// ekar******************************//
		if (ascii_value == 108) {

		    array[1] = ascii_value;
		    int i = 1;
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;

		    // for ??? in yukti
		    if (ascii_value == 81) {
			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;

			if (ascii_value == 109) {
			    output.append("\u0915\u094d\u0924"
				    + value(array[1]));
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}

		    } else

		    // ******************kra***********************//
		    if (ascii_value == 115) {
			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;

			if (ascii_value == 124) {
			    output.append("\u0915\u094d\u0930"
				    + value(array[1]));
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}
		    } else

		    // ***********for dra**************//
		    if (ascii_value == 56) {
			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;

			if (ascii_value == 171) {
			    output.append("\u0921\u094d\u0930"
				    + value(array[1]));
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}

		    } else

		    // ***********for dra another da**************//
		    if (ascii_value == 98) {
			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;

			if (ascii_value == 124) {

			    output.append("\u0926\u094d\u0930"
				    + value(array[1]));
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}

		    } else

		    // *************for fa************//
		    if (ascii_value == 107) {

			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
			if (ascii_value == 109) {

			    output.append("\u092b" + value(array[1]));
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else // ************pra**************//
			if (ascii_value == 124) {

			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    if (ascii_value == 109) {
				output.append("\u092b\u094d\u0930"
					+ value(array[1]));
				flag = "no";
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
				rahswa_fin = "yes";
			    } else {
				output.append("\u092a\u094d\u0930"
					+ value(array[1]));
				flag = "no";
				// tmp =
				// input.charAt(p);ascii_value=(int)tmp;p++;
				rahswa_fin = "yes";
			    }
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			    // flag = "no";//test
			}

		    } else

		    // *************jha*****************//
		    if (ascii_value == 101) {

			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;

			if (ascii_value == 109) {

			    output.append("\u091d" + value(array[1]));
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			}

			else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}

		    } else

		    // ************kra*********************//
		    if (ascii_value == 113) {

			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
			if (ascii_value == 109) {
			    output.append("\u0915\u094d\u0930"
				    + value(array[1]));
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else if (ascii_value == 92) {
			    output.append("\u0924\u094d\u0930\u094d");
			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    output.append(value(ascii_value));
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    output.append(value(array[1]));
			    rahswa_fin = "yes";
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}

		    } else

		    // ************bra*********************//
		    if (ascii_value == 97) {

			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
			if (ascii_value == 124) {

			    output.append("\u092c\u094d\u0930"
				    + value(array[1]));

			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}

		    } else

		    // ****************gra************************//
		    if (ascii_value == 117) {

			array[2] = ascii_value;
			tmp = input.charAt(p);
			ascii_value = (int) tmp;
			p++;
			if (ascii_value == 124) {

			    output.append("\u0917\u094d\u0930"
				    + value(array[1]));

			    flag = "no";
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;
			    rahswa_fin = "yes";
			} else {
			    output.append(value(array[2]));
			    output.append(value(array[1]));
			    ;
			    rahswa_fin = "yes";
			}

		    } else

		    // for the half characters that are present after the rahswa
		    // ekar//
		    if (rahswa_fin != "yes") {
			while (ascii_value == 48 || ascii_value == 58
				|| ascii_value == 65 || ascii_value == 69
				|| ascii_value == 68 || ascii_value == 71
				|| ascii_value == 72 || ascii_value == 73
				|| ascii_value == 74 || ascii_value == 75
				|| ascii_value == 78 || ascii_value == 82
				|| ascii_value == 83 || ascii_value == 84
				|| ascii_value == 85 || ascii_value == 86
				|| ascii_value == 87 || ascii_value == 88
				|| ascii_value == 89 || ascii_value == 90
				|| ascii_value == 105 || ascii_value == 124
				|| ascii_value == 126 || ascii_value == 161
				|| ascii_value == 163 || ascii_value == 165
				|| ascii_value == 140) {
			    i++;
			    array[i] = ascii_value;
			    tmp = input.charAt(p);
			    ascii_value = (int) tmp;
			    p++;

			}

			if (i != 1) {
			    for (int j = 2; j < (i + 1); j++) {
				output.append(value(array[j]));
			    }

			    if (ascii_value == 102) {// anra, chya, pet
				// chireko sha

				output.delete(output.length() - 1, output
					.length());// =output.substring(0,
				// output.length()-1);
				output.append(value(array[1]));
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
			    }

			    else

			    // for tra in rashtriya
			    if (ascii_value == 54) {
				output.append(value(ascii_value));
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
				if (ascii_value == 171) {// if tra
				    output.append("\u094d\u0930"
					    + value(array[1]));
				    tmp = input.charAt(p);
				    ascii_value = (int) tmp;
				    p++;
				} else { // only ta
				    output.append(value(array[1]));
				    ;
				}

			    } else // ****************************for
			    // dra**********************************//
			    if (ascii_value == 56) {

				output.append(value(ascii_value));
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
				if (ascii_value == 171) {// if dra
				    output.append("\u094d\u0930"
					    + value(array[1]));
				    tmp = input.charAt(p);
				    ascii_value = (int) tmp;
				    p++;
				} else { // only da
				    output.append(value(array[1]));
				    ;
				}
			    } else if (ascii_value == 98) {
				output.append(value(ascii_value));
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
				if (ascii_value == 124) {// if dra
				    output.append("\u094d\u0930"
					    + value(array[1]));
				    tmp = input.charAt(p);
				    ascii_value = (int) tmp;
				    p++;
				} else { // only da
				    output.append(value(array[1]));
				    ;
				}
			    } else

			    // ****************for bra***************//
			    if (ascii_value == 97) {
				array[9] = ascii_value;
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
				if (ascii_value == 124) {// if bra
				    output.append("\u092c\u094d\u0930"
					    + value(array[1]));
				    tmp = input.charAt(p);
				    ascii_value = (int) tmp;
				    p++;
				} else { // only b
				    output.append(value(array[9]));
				    output.append(value(array[1]));
				    ;
				}
			    } else {
				output.append(value(ascii_value)
					+ value(array[1]));
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
			    }
			}// }
			else {
			    if (unicode_string == "\u0915\u094d\u0937"
				    || unicode_string == "\u0923") {
				output.append(value(array[1]));
				;
				tmp = input.charAt(p);
				ascii_value = (int) tmp;
				p++;
			    } else {
				if (flag != "no") {

				    output.append(value(ascii_value)
					    + value(array[1]));
				    tmp = input.charAt(p);
				    ascii_value = (int) tmp;
				    p++;
				}
			    }

			}

		    }
		    rahswa_fin = "no";
		}

		else {
		    output.append(value(ascii_value));
		    tmp = input.charAt(p);
		    ascii_value = (int) tmp;
		    p++;
		}
		float progressF = (p + getInitialProgress()) / getUnitBlock();
		updateProgressBar(progressF);
		if (p == input.length()) {
		    setConversionComplete(true);
		    JOptionPane.showMessageDialog(null, getFileName()
			    + " has been successfully converted.!!!",
			    "Conversion complete!",
			    JOptionPane.INFORMATION_MESSAGE);
		}
	    }// end of while

	    output.append(value(ascii_value));
	    fileReader.close();
	    str = output.toString();
	    Writer out = new BufferedWriter(new OutputStreamWriter(
		    new FileOutputStream(getOutputFile()), getOutputEncoding()));
	    out.write(str);
	    out.close();
	    System.out.println("Finished....");
	} catch (UnsupportedEncodingException e) {
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!",
		    "Unsupported Encoding", JOptionPane.INFORMATION_MESSAGE);
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!", "Failure",
		    JOptionPane.INFORMATION_MESSAGE);
	} catch (StringIndexOutOfBoundsException e) {
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!", "Failure",
		    JOptionPane.INFORMATION_MESSAGE);
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(null,
		    "Conversion could not be completed!", "Failure",
		    JOptionPane.INFORMATION_MESSAGE);
	}
    }

    public String value(int ascii_value) {

	switch (ascii_value) {

	case 9:
	    unicode_string = "\u0009";// \u0009tab
	    break;
	case 13:
	    unicode_string = "\u0020";// \u0020space
	    break;
	case 32:
	    unicode_string = "\u0020";// \u0020space
	    break;
	case 10:
	    unicode_string = "\n";// "\u2028";//newline
	    break;
	case 33:
	    unicode_string = "\u0967";
	    break;
	case 34:
	    unicode_string = "\u0942";
	    break;
	case 35:
	    unicode_string = "\u0969";
	    break;
	case 36:
	    unicode_string = "\u096a";
	    break;
	case 37:
	    unicode_string = "\u096b";
	    break;
	case 38:
	    unicode_string = "\u096d";
	    break;
	case 39:
	    unicode_string = "\u0941";
	    break;
	case 40:
	    unicode_string = "\u096f";
	    break;
	case 41:
	    unicode_string = "\u0966";
	    break;
	case 42:
	    unicode_string = "\u096e";
	    break;
	case 43:
	    unicode_string = "\u0902";
	    break;
	case 44:
	    unicode_string = "\u002c";
	    break;
	case 45:
	    unicode_string = "\u0028";
	    break;
	case 46:
	    unicode_string = "\u0964";
	    break;
	case 47:
	    unicode_string = "\u0930";
	    break;
	case 48:
	    unicode_string = "\u0923\u094d";
	    break;
	case 49:
	    unicode_string = "\u091c\u094d\u091e";
	    break;

	case 50:
	    unicode_string = "\u0926\u094d\u0926";
	    break;
	case 51:
	    unicode_string = "\u0918";
	    break;
	case 52:
	    unicode_string = "\u0926\u094d\u0927";
	    break;
	case 53:
	    unicode_string = "\u091b";
	    break;
	case 54:
	    unicode_string = "\u091f";
	    break;
	case 55:
	    unicode_string = "\u0920";
	    break;
	case 56:
	    unicode_string = "\u0921";
	    break;
	case 57:
	    unicode_string = "\u0922";
	    break;
	case 58:
	    unicode_string = "\u0938\u094d";
	    break;
	case 59:
	    unicode_string = "\u0938";
	    break;
	case 60:
	    unicode_string = "\u003f";
	    break;
	case 61:
	    unicode_string = "\u002e";
	    break;
	case 62:
	    unicode_string = "\u0936\u094d\u0930";
	    break;
	case 63:
	    unicode_string = "\u0930\u0941";
	    break;
	case 64:
	    unicode_string = "\u0968";
	    break;
	case 65:
	    unicode_string = "\u092c\u094d";
	    break;
	case 66:
	    unicode_string = "\u0926\u094d\u092f";
	    break;
	case 67:
	    unicode_string = "\u090b";
	    break;
	case 68:
	    unicode_string = "\u092e\u094d";
	    break;
	case 69:
	    unicode_string = "\u092d\u094d";
	    break;
	case 70:
	    unicode_string = "\u093e";
	    break;
	case 71:
	    unicode_string = "\u0928\u094d";
	    break;
	case 72:
	    unicode_string = "\u091c\u094d";
	    break;
	case 73:
	    unicode_string = "\u0915\u094d\u0937\u094d";
	    break;
	case 74:
	    unicode_string = "\u0935\u094d";
	    break;
	case 75:
	    unicode_string = "\u092a\u094d";
	    break;
	case 76:
	    unicode_string = "\u0940";
	    break;
	case 77:
	    unicode_string = "\u003A";
	    break;
	case 78:
	    unicode_string = "\u0932\u094d";
	    break;
	case 79:
	    unicode_string = "\u0907";
	    break;
	case 80:
	    unicode_string = "\u090f";
	    break;
	case 81:
	    unicode_string = "\u0924\u094d\u0924";
	    break;
	case 82:
	    unicode_string = "\u091a\u094d";
	    break;
	case 83:
	    unicode_string = "\u0915\u094d";
	    break;
	case 84:
	    unicode_string = "\u0924\u094d";
	    break;
	case 85:
	    unicode_string = "\u0917\u094d";
	    break;
	case 86:
	    unicode_string = "\u0916\u094d";
	    break;
	case 87:
	    unicode_string = "\u0927\u094d";
	    break;
	case 88:
	    unicode_string = "\u0939\u094d";
	    break;
	case 89:
	    unicode_string = "\u0925\u094d";
	    break;
	case 90:
	    unicode_string = "\u0936\u094d";
	    break;
	case 91:
	    unicode_string = "\u0943";
	    break;
	case 92:
	    unicode_string = "\u094d";
	    break;
	case 93:
	    unicode_string = "\u0947";
	    break;
	case 94:
	    unicode_string = "\u096c";
	    break;
	case 95:
	    unicode_string = "\u0029";
	    break;
	case 96:
	    unicode_string = "\u091e";
	    break;
	case 97:
	    unicode_string = "\u092c";
	    break;
	case 98:
	    unicode_string = "\u0926";
	    break;
	case 99:
	    unicode_string = "\u0905";
	    break;
	case 100:
	    unicode_string = "\u092e";
	    break;
	case 101:
	    unicode_string = "\u092d";
	    break;
	case 102:
	    unicode_string = "\u093e";
	    break;
	case 103:
	    unicode_string = "\u0928";
	    break;
	case 104:
	    unicode_string = "\u091c";
	    break;
	case 105:
	    unicode_string = "\u0937\u094d";
	    break;
	case 106:
	    unicode_string = "\u0935";
	    break;
	case 107:
	    unicode_string = "\u092a";
	    break;
	case 108:
	    unicode_string = "\u093f";
	    break;
	// has to be modified 109
	case 109:
	    unicode_string = "\u094d\u0915";
	    break;
	case 110:
	    unicode_string = "\u0932";
	    break;
	case 111:
	    unicode_string = "\u092f";
	    break;
	case 112:
	    unicode_string = "\u0909";
	    break;
	case 113:
	    unicode_string = "\u0924\u094d\u0930";
	    break;
	case 114:
	    unicode_string = "\u091a";
	    break;
	case 115:
	    unicode_string = "\u0915";
	    break;
	case 116:
	    unicode_string = "\u0924";
	    break;
	case 117:
	    unicode_string = "\u0917";
	    break;
	case 118:
	    unicode_string = "\u0916";
	    break;
	case 119:
	    unicode_string = "\u0927";
	    break;
	case 120:
	    unicode_string = "\u0939";
	    break;
	case 121:
	    unicode_string = "\u0925";
	    break;
	case 122:
	    unicode_string = "\u0936";
	    break;
	case 123:
	    unicode_string = "\u0930\u094d";
	    break;
	case 124:
	    unicode_string = "\u094d\u0930";
	    break;
	case 125:
	    unicode_string = "\u0948";
	    break;
	case 126:
	    unicode_string = "\u091e\u094d";
	    break;
	case 134:
	    unicode_string = "\u0924\u094d\u0930";
	    break;
	case 137:
	    unicode_string = "\u091d\u094d";
	    break;
	case 140:
	    unicode_string = "\u0924\u094d\u0924\u094d";
	    break;
	case 145:
	    unicode_string = "\u0027";
	    break;
	case 146:
	    unicode_string = "\u0027";
	    break;
	case 147:
	    unicode_string = "\u0901";
	    break;
	case 150:
	    unicode_string = "\u2013";
	    break;
	case 151:
	    unicode_string = "\u2015";
	    break;
	case 156:
	    unicode_string = "\u0924\u094d\u0930\u094d";
	    break;
	case 160:
	    unicode_string = "";
	    break;
	case 161:
	    unicode_string = "\u091c\u094d\u091e\u094d";
	    break;
	case 162:
	    unicode_string = "\u0926\u094d\u0927";
	    break;
	case 163:
	    unicode_string = "\u0918\u094d";
	    break;
	case 164:
	    unicode_string = "\u0021";
	    break;
	case 165:
	    unicode_string = "\u0930\u094d\u200d";
	    break;
	case 167:
	    unicode_string = "\u091f\u094d\u091f";
	    break;
	case 170:
	    unicode_string = "\u0919";
	    break;
	case 176:
	    unicode_string = "\u0919\u094d\u0915";
	    break;
	case 171:
	    unicode_string = "\u094d\u0930";
	    break;
	case 180:
	    unicode_string = "\u091d";
	    break;
	case 182:
	    unicode_string = "\u0920\u094d\u0920";
	    break;
	case 183:
	    unicode_string = "\u0919\u094d\u0917";
	    break;
	case 191:
	    unicode_string = "\u0930\u0942";
	    break;
	case 198:
	    unicode_string = "\u201d";
	    break;
	case 199:
	    unicode_string = "\u092b\u094d";
	    break;
	case 200:
	    unicode_string = "\u0937";
	    break;
	case 202:
	    unicode_string = "\u0924\u094d\u0930\u094d";
	    break;
	case 205:
	    unicode_string = "\u0919\u094d\u0915";
	    break;
	// case 212:
	// unicode_string="\u0915";
	// break;
	case 216:
	    unicode_string = "\u094d\u092f";
	    break;
	case 221:
	    unicode_string = "\u091f\u094d\u0920";
	    break;
	case 223:
	    unicode_string = "\u0926\u094d\u092e";
	    break;
	case 229:
	    unicode_string = "\u0926\u094d\u0935";
	    break;
	case 230:
	    unicode_string = "\u201c";
	    break;
	case 231:
	    unicode_string = "\u0950";
	    break;
	case 247:
	    unicode_string = "\u002f";
	    break;
	case 248:
	    unicode_string = "\u092f\u094d";
	    break;
	default:
	    unicode_string = Integer.toString(ascii_value);
	    break;
	}

	return unicode_string;

    }
}
