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
package np.esperanto.conv4.test;

import junit.framework.*;
import java.io.File;
import np.esperanto.conv4.GeneralReaderWriter;
import java.util.*;

public class TestReaderWriters extends TestCase {
  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }


  public void testtextKantipurOdfDom() throws Exception {
	GeneralReaderWriter odtrw = new np.esperanto.conv4.odfdom.OpenOfficeReaderWriter();
	checkTesttextKantipur(odtrw);
  }

  public void testtextKantipurJacob() throws Exception {
	GeneralReaderWriter odtrw = new np.esperanto.vortaro.OpenOfficeJacobsOldReaderWriter();
	checkTesttextKantipur(odtrw);
  }


  private void checkTesttextKantipur(GeneralReaderWriter odtrw) throws Exception {
	LoggingConversionHandler loggingconversionhandler = new LoggingConversionHandler();
	odtrw.convert("test/testtextKantipur.odt", null, loggingconversionhandler);
	assertTrue(loggingconversionhandler.fonts.contains("Kantipur"));
	assertEquals(1, loggingconversionhandler.fonts.size());

	Set words = loggingconversionhandler.fontwords.get("Kantipur");

	assertEquals(3, words.size());
	System.out.println("words = " + words);
	assertTrue(words.contains(">Ldfg\\n]"));
	assertTrue(words.contains("cfºgf]"));
	assertTrue(words.contains("pgsf"));
  }

}
/*
 FORSKEL for ../nepalitext_odt/esperanto-vortaro/vorto02.odt
 FORSKEL for ../nepalitext_odt/esperanto-vortaro/vorto03.odt
 FORSKEL for ../nepalitext_odt/nep_eng.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Follow up course 1/Materials/LES1.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Follow up course 1/Materials/Follow up 1.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Follow up course 3/Materials/MD3GUID.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Reviews, Monetoring and Evauluations/Materials/Old stuff/CASTE.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Correspondence/2004/GKSiwakoti's honorerium.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Materials/ICOC intro, background & aims.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 1/Materials/ICOCINF.S3.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/Training Modules/Basic Language 2/Materials/BLT 2  april 2007.odt
 FORSKEL for ../nepalitext_odt/MS/ICOC/new Lang meterial/lise language 200407.odt
 FORSKEL for ../nepalitext_odt/MS/translated/MSN income generation strategy.odt
 FORSKEL for ../nepalitext_odt/MS/translated/Baseline/baseline/ALG Questionnaire DDC.odt
 FORSKEL for ../nepalitext_odt/MS/translated/Baseline/baseline/ALG QuestionnaireVDC.odt
 FORSKEL for ../nepalitext_odt/MS/translated/Baseline/baseline/CSO Questionnaire.odt
 FORSKEL for ../nepalitext_odt/MS/translated/Baseline/baseline/PR Questionnaire.doc_nepali.odt
 */
