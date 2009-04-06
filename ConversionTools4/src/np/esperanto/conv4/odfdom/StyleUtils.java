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

package np.esperanto.conv4.odfdom;

import org.openoffice.odf.doc.element.style.OdfTextProperties;
import org.openoffice.odf.dom.element.OdfStylableElement;
import org.openoffice.odf.dom.style.props.OdfStyleProperty;
import org.w3c.dom.Node;

/**
 *
 * @author j
 */
public class StyleUtils {

  
    /**
     * Returns a property for a (text) node, as it would appear for a user in OpenOffice:
     * First examining the style (and the style's parent styles) and if the property is not
     * found the search continues in the enclosing node, and so on, until an enclosing node
     * is found, whitc has a style (or an inherited style) where the property is defined.
     * Example: findActualStylePropertyValueForNode(textNode, OdfTextProperties.FontName) will give the font name
     * @param node (text) node to be examined
     * @param propertyName for example OdfTextProperties.FontName
     * @return proterty the value of the property, for example "Thorndale"
     */
    public static String findActualStylePropertyValueForNode(Node node, OdfStyleProperty propertyName) {
      Node nodeWithStyle = node;

      while (nodeWithStyle!=null && !(nodeWithStyle instanceof OdfStylableElement)) 
        nodeWithStyle = nodeWithStyle.getParentNode();

      if (nodeWithStyle==null) {
        // Property value not found in any nodes' styles!
        return null;
      }


      String propertyValue = ((OdfStylableElement) nodeWithStyle).getProperty(propertyName);

      if (propertyValue != null) return propertyValue;

      // Continue the search in enclosing nodes
      return findActualStylePropertyValueForNode(nodeWithStyle.getParentNode(), propertyName);

    }



}
