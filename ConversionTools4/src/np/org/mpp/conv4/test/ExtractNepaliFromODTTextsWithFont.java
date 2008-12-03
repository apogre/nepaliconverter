package np.org.mpp.conv4.test;

import java.io.*;
import java.util.*;

import np.org.mpp.conv4.utils.ConversionHandler;
import np.org.mpp.conv4.utils.GeneralReaderWriter;
import org.openoffice.odf.doc.OdfDocument;
import org.openoffice.odf.doc.OdfFileDom;



public class ExtractNepaliFromODTTextsWithFont {

  String subdir = "";
  GeneralReaderWriter odtrwj = new np.org.mpp.conv4.utils.OpenOfficeJacobsOldReaderWriter(); //String subdir = "ja";
  GeneralReaderWriter odtrwo = new np.org.mpp.conv4.utils.odfdom.OpenOfficeReaderWriter(); //String subdir = "odfdom";


  //XPath  xpath = XPathFactory.newInstance().newXPath();


  LoggingConversionHandler conversionHandlerj = new LoggingConversionHandler();
  LoggingConversionHandler conversionHandlero = new LoggingConversionHandler();

  public ExtractNepaliFromODTTextsWithFont() {
  }

  private static int documentNumber = 0;

  private void traverse(File file) {
    if (file.isDirectory()) {
	if (file.isHidden()) return;
	for (File f : file.listFiles()) {
	  traverse(f);
	}
    } else
	try {
	  System.out.println(++documentNumber +" Reading " + file);

	  OdfFileDom content = OdfDocument.loadDocument(file ).getContentDom();
	  if (content.toString().indexOf("text:change-start text:change-id")>0) {
		System.out.println("Changesets found, skipping " + file);
	  } else {

	    //conversionHandler.actualFile = file;
	    //odtrw.convert(file.getPath(), "tmp/delete.odt", conversionHandler);
	    odtrwj.convert(file.getPath(), null, conversionHandlerj);
	    odtrwo.convert(file.getPath(), null, conversionHandlero);

      System.out.println("conversionHandlero.fontwords = " + conversionHandlero.fontwords);

	    if (!conversionHandlerj.fontwords.equals(conversionHandlero.fontwords)) {
		  System.out.println("FORSKEL for "+file);
		  System.out.println("conversionHandlerj.fontwords = " + conversionHandlerj.fontwords);
		  System.out.println("conversionHandlero.fontwords = " + conversionHandlero.fontwords);
		  conversionHandlerj.fontwords.clear(); conversionHandlero.fontwords.clear();
	    }
	    //System.out.println("fontwords = " + fontwords.keySet());
	  }
	} catch (Exception e) {
	  e.printStackTrace();
	}
  }



  private void save() throws FileNotFoundException {

    for (String font : conversionHandlero.fontwords.keySet()) {
	if (font.indexOf("Arial") != -1) continue;
	if (font.indexOf("Times") != -1) continue;
	if (font.indexOf("Sans") != -1) continue;
	TreeSet<String> words = new TreeSet<String>(conversionHandlero.fontwords.get(font));
	new File("tmp/"+subdir).mkdirs();
	PrintWriter pw = new PrintWriter("tmp/"+subdir+"/words_"+font+".txt");
	for (String word : words) {
	  pw.println(word);
	}
	pw.close();
	System.out.println("wrote words for "+font);

    }
  }


  public static void main(String[] args) throws FileNotFoundException {
    ExtractNepaliFromODTTextsWithFont e = new ExtractNepaliFromODTTextsWithFont();


    //e.traverse(new File("../nepalitext_odt/esperanto-vortaro/vorto02.odt"));
    //e.traverse(new File("../nepalitext_odt/esperanto-vortaro/vorto03.odt"));

    //e.traverse(new File("../nepalitext_odt/nep_eng.odt"));
    //e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Follow up course 1/Materials/LES1.odt"));
    //e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Follow up course 1/Materials/Follow up 1.odt"));
    //e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Follow up course 3/Materials/MD3GUID.odt"));
    //e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Reviews, Monetoring and Evauluations/Materials/Old stuff/CASTE.odt"));
    e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Correspondence/2004/GKSiwakoti's honorerium.odt"));
    e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Materials/ICOC intro, background & aims.odt"));
    e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Materials/ICOCINF.S3.odt"));
    e.traverse(new File("../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 2/Materials/BLT 2  april 2007.odt"));
    //e.traverse(new File("../nepalitext_odt/MS/ICOC/new Lang meterial/lise language 200407.odt"));
    e.traverse(new File("../nepalitext_odt/MS/translated/MSN income generation strategy.odt"));
    e.traverse(new File("../nepalitext_odt/MS/translated/Baseline/baseline/ALG Questionnaire DDC.odt"));
    e.traverse(new File("../nepalitext_odt/MS/translated/Baseline/baseline/ALG QuestionnaireVDC.odt"));
    e.traverse(new File("../nepalitext_odt/MS/translated/Baseline/baseline/CSO Questionnaire.odt"));
    e.traverse(new File("../nepalitext_odt/MS/translated/Baseline/baseline/PR Questionnaire.doc_nepali.odt"));


    //e.traverse(new File("../nepalitext_odt"));
    //e.traverse(new File("../nepalitext_odt/MS/ICOC"));
    //e.traverse(new File("test"));
    //e.traverse(new File("test/testtextKantipur.odt"));
    e.save();
  }


}

