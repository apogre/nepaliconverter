/*
 * Copyright (C) 2008 Dana Esperanta Junulara Organizo http://dejo.dk/
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

package np.org.mpp.conv4.utils.odfdom;

import org.openoffice.odf.doc.element.style.OdfTextProperties;
import org.openoffice.odf.dom.element.OdfStylableElement;
import org.openoffice.odf.dom.style.props.OdfStyleProperty;
import org.w3c.dom.Node;

/**
 *
 * @author j
 */
public class StyleUtils {
/*
  public static OdfStylableElement findOdfStylableParentNode(Node node) {
    if (node==null) return null;
    if (node instanceof OdfStylableElement) return (OdfStylableElement) node;
    return findOdfStylableParentNode(node.getParentNode());
  }
*/
  
  
  public static String findStylePropertyForNode(Node node, OdfStyleProperty propertyName) {
    
    while (node!=null && !(node instanceof OdfStylableElement)) 
      node.getParentNode();

    if (node==null) return null;
    
    OdfStylableElement parentNode= (OdfStylableElement) node;

    String propertyValue = parentNode.getProperty(propertyName);
    if (propertyValue != null) return propertyValue;

    return findStylePropertyForNode(parentNode.getParentNode(), propertyName);
  }
  

}
