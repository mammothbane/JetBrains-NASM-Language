/*
 * Modified 3/31/18 - converted to Kotlin (Nathan Perry).
 * Modifications copyright (c) 2018 Nathan Perry. All rights reserved to the greatest extent permissible by law.
 * Copyright (c) 2017-2018 Aidan Khoury. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.avaglir.jetbrains.nasmplugin.psi.impl

import com.avaglir.jetbrains.nasmplugin.psi.*
import com.intellij.psi.PsiElement

object PsiImplUtil {

    fun getIncludeString(element: Include): String? {
        val includeString = element.node.findChildByType(NASMTypes.STRING)
        return includeString?.text?.replace("\"", "")?.replace("'", "")
    }

    fun getDefineIdentifierString(element: Define): String? {
        val defineIdentifier = element.node.findChildByType(NASMTypes.IDENTIFIER)
        return defineIdentifier?.text
    }

    fun getDefineIdentifier(element: Define): Identifier? {
        val defineIdentifier = element.node.findChildByType(NASMTypes.IDENTIFIER)
        return if (defineIdentifier != null) defineIdentifier.psi as Identifier else null
    }

    fun getMacroIdentifier(element: Macro): String? {
        val macroIdentifier = element.node.findChildByType(NASMTypes.IDENTIFIER)
        return macroIdentifier?.text
    }

    fun getLabelIdentifierString(element: Label): String? {
        val labelDef = element.lblDef
        if (labelDef != null) {
            val labelDefString = labelDef.text
            return labelDefString.substring(0, labelDefString.indexOf(':')).trim { it <= ' ' }
        }
        return null
    }

    fun getConstantIdentifierString(element: Constant): String? {
        val identifier = element.identifier.id
        return identifier?.text
    }

    fun getName(element: Identifier): String {
        return element.id.text
    }

    fun setName(element: Identifier, newName: String): PsiElement {
        val keyNode = element.id.node
        if (keyNode != null) {
            val property = ElementFactory.createIdentifier(element.project, newName)
            val newKeyNode = property.firstChild.node
            element.node.replaceChild(keyNode, newKeyNode)
        }
        return element
    }

    fun getNameIdentifier(element: Identifier): PsiElement {
        return element.id
    }

    //public static String getLabelIdentifierString(LabelInstruction element) {
    //    PsiElement labelIns = element.getLblIns();
    //    if (labelIns != null) {
    //        String labelInsString = labelIns.getText();
    //        return labelInsString.substring(0, labelInsString.indexOf(':')).trim();
    //    }
    //    return null;
    //}
    //public static String getLabelIdentifierString(LabelData element) {
    //    PsiElement lblData = element.getLblData();
    //    if (lblData != null) {
    //        String lblDataString = lblData.getText();
    //        return lblDataString.substring(0, lblDataString.indexOf(':')).trim();
    //    }
    //    return null;
    //}


}
