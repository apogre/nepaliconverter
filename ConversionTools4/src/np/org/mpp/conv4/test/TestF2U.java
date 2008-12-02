package np.org.mpp.conv4.test;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import np.org.mpp.conv4.old_f2u.Old_F2UConversionHandler;
import np.org.mpp.conv4.f2u.F2UConversionHandler;
import np.org.mpp.conv4.utils.odfdom.SpreadsheetWriter;
import np.org.mpp.conv4.utils.odfdom.SpreadsheetReader;
import java.util.HashSet;
import java.util.HashMap;
import java.io.*;
import java.util.*;

public class TestF2U {
  public TestF2U() {
  }

  public static void main(String[] args) throws Exception {
    TestF2U testf2u = new TestF2U();
    //testf2u.test("test/words_Kantipur.txt", "Kantipur", "tmp/test_words_Kantipur_result.ods");
    //testf2u.test("Preeti", "tmp/test_words_Preeti_result.ods");
    testf2u.test("Kantipur", "tmp/test_words_Kantipur_result.ods");
  }

    private void test(String font, String resultFile) throws Exception {
	  ArrayList<String> words = new ArrayList<String>();
	  readTextFile(words, "test/words_"+font+".txt");

	  HashMap<String,String> correctWords = new HashMap<String,String>();
	  try {
		SpreadsheetReader ssr = new SpreadsheetReader();
		ArrayList<ArrayList<String>> correctList = ssr.read("test/correct_words_"+font+".ods",1,2);

		for (ArrayList<String> row : correctList) {
		    correctWords.put(row.get(0), row.get(1));
		}
		System.out.println("correctWords = " + correctWords);
	  } catch (Exception ex) {
		ex.printStackTrace();
	  }



	  ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

	  F2UConversionHandler newf2u = new F2UConversionHandler();
	  Old_F2UConversionHandler oldf2u = new Old_F2UConversionHandler();


	  ArrayList<String> heading = new ArrayList<String>();
	  heading.add("orig");
	  heading.add(font);
	  heading.add("new f2u");
	  heading.add("old f2u");
	  heading.add("status");
	  table.add(heading);
	  int err = 0;

	  String ignoreInDiff = "[-­\u2013\u2014\u2015'‘’:ः ]"; //"[Ù;धघÜ%-­\u2013\u2014\u2015'‘’:ः]";
	  System.out.println("These chars be ignored in compariosons: "+ignoreInDiff);


	  for (String w : words) {
		ArrayList<String> row = new ArrayList<String>();

		String cNew = newf2u.convertText(font, w).trim();
		String cOld = oldf2u.convertText(font, w).trim();
		row.add(w);
		row.add(w);
		row.add(cNew);
		row.add(cOld);


		String cOldi = cOld.replaceAll(ignoreInDiff," ");
		String cNewi = cNew.replaceAll(ignoreInDiff," ");

		String status = "";
		String correct = correctWords.get(w);
		if (cNewi.equals(cOldi) || correct!=null && cNew.equals(correct)) status="OK";
		else {
		  status = "diff: " + diffStringDetail(cNewi, cOldi);
		}


		/*
		for (int i=0; i<w.length(); i++) {
		    if (w.charAt(i)>255) System.out.println(status = ">255 i input "+w+" : "
									  +w.charAt(i) + "(\\u" + Integer.toHexString(w.charAt(i)) + ") ");
		}*/


		row.add(status);
		if (status!="OK") {
		  err++;
		  table.add(row);
		  if (err < 10) System.out.println(row);
		}
	  }

	  System.out.println("");

	  newf2u.findMapping(font).printUsage();

	  //System.out.println("Total of "+err+" errors on "+words.size()+" words ("+100*err/words.size()+" %)");
	  System.out.println("Total of "+err+" deviations on "+words.size()+" words");

	  System.out.println(font+" writing "+resultFile+ " ");
	  SpreadsheetWriter ssw = new SpreadsheetWriter();
	  ssw.write(resultFile, table, font);
	  System.out.println();
	  System.out.println("finish");
    }

    public static void readTextFile(Collection<String> words, String fn) throws FileNotFoundException, IOException {
	  BufferedReader br = new BufferedReader(new FileReader(fn));
	  int maxLines = 3000000;
	  String l;
	  while ( (l=br.readLine())!=null && maxLines-->0) words.add(l.trim());
	  br.close();

	  System.out.println(fn+" read, size is now: "+words.size());
    }

    public static String diffStringDetail(String cNew, String cOld) {
	  String status = "";
	  int imax = Math.min(cNew.length(), cOld.length());
	  for (int i=0; i<imax; i++) {
		if (cNew.charAt(i) != cOld.charAt(i)) {
		    if (status.length()>40) {
			status = status + "  ...";
			break;
		    }
		    status = status + " pos "+i+": "
				 +hex(cNew.charAt(i))+ " "
				 +hex(cOld.charAt(i));
		}
	  }
	  return status;
    }

    public static String hex(char c) {
	  return (int) c + "(\\u" + Integer.toHexString(c) + ")";
    }

}
