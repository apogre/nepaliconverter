package vortaro;

import org.openoffice.odf.text.*;
import org.openoffice.odf.*;
import org.openoffice.odf.common.documenttype.*;
import java.io.IOException;

public class LeguPerOdf4j {
  public static void main(String[] args) throws IOException {
    TextDocument td = (TextDocument) OpenDocumentFactory.load("/home/j/esperanto/nepala vortaro/provo.odt");
    System.out.println("top level body structure:");
    Body body = td.getBody();
    body.getParent();
// iterate over the body contents
    for (INode element : body) {
        // handle sections
        if (element instanceof Section) {
            Section section = (Section) element;
            for (INode se : section) {
                // handle paragraphs
                if (se instanceof Paragraph) {
              Paragraph paragraph = (Paragraph)se;
                    for (INode pe : paragraph) {
                        // handle portions
                        if (pe instanceof Portion) {
                            Portion portion = (Portion)pe;
                            System.out.println("    portion: {" + portion.toString() +"}");
                        }
                    }
                }
            }
        }
    }
  }
}
