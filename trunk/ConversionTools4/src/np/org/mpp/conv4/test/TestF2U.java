package np.org.mpp.conv4.test;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import np.org.mpp.conv4.old_f2u.Old_F2UConversionHandler;
import np.org.mpp.conv4.f2u.F2UConversionHandler;
import np.org.mpp.conv4.utils.odfdom.SpreadsheetWriter;

public class TestF2U {
  public TestF2U() {
  }

  public static void main(String[] args) throws Exception {
    TestF2U testf2u = new TestF2U();
    //testf2u.test("test/words_Kantipur.txt", "Kantipur", "tmp/test_words_Kantipur_result.ods");
    testf2u.test("test/words_Preeti.txt", "Preeti", "tmp/test_words_Preeti_result3.ods");
  }

    private void test(String textFile, String font, String resultFile) throws Exception {
        ArrayList<String> words = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(textFile));
        int maxLines = 7000;
        String l;
        while ( (l=br.readLine())!=null && maxLines-->0) words.add(l.trim());
        br.close();

        System.out.println(font+" words read: "+words.size());

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

        for (String w : words) {
            ArrayList<String> row = new ArrayList<String>();
            String cNew = newf2u.convertText(font, w).trim();
            String cOld = oldf2u.convertText(font, w).trim();
            row.add(w);
            row.add(w);
            row.add(cNew);
            row.add(cOld);
            String status = "";
            if (cNew.equals(cOld)) status="OK";
            else {
              status = "diff: " + diffStringDetail(cNew, cOld);
            }
            row.add(status);
            table.add(row);
        }

        System.out.println(font+" writing "+resultFile+ " ");
        SpreadsheetWriter ssw = new SpreadsheetWriter();
        ssw.write(resultFile, table);
    }

    public static String diffStringDetail(String cNew, String cOld) {
        String status = "";
        int imax = Math.min(cNew.length(), cOld.length());
        for (int i=0; i<imax && status.length()<20; i++) {
            if (cNew.charAt(i) != cOld.charAt(i)) {
                status = status + " pos "+i+": "+cNew.charAt(i)+" "+cOld.charAt(i);
            }
        }
        return status;
    }
}
