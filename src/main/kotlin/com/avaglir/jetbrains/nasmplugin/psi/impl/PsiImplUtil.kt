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
    @JvmStatic
    fun getIncludeString(element: Include): String? {
        val includeString = element.node.findChildByType(Types.STRING)
        return includeString?.text?.replace("\"", "")?.replace("'", "")
    }

    @JvmStatic
    fun getDefineIdentifierString(element: Define): String? = element.node.findChildByType(Types.IDENTIFIER)?.text

    @JvmStatic
    fun getDefineIdentifier(element: Define): Identifier? = element.node.findChildByType(Types.IDENTIFIER)?.psi as Identifier?

    @JvmStatic
    fun getMacroIdentifier(element: Macro): String? = element.node.findChildByType(Types.IDENTIFIER)?.text

    @JvmStatic
    fun getLabelIdentifierString(element: Label): String? = element.lblDef?.text?.let {
        it.substring(0, it.indexOf(':')).trim { it <= ' ' }
    }

    @JvmStatic
    fun getConstantIdentifierString(element: Constant): String? = element.identifier.id.text

    @JvmStatic
    fun getName(element: Identifier): String = element.id.text

    @JvmStatic
    fun setName(element: Identifier, newName: String): PsiElement {
        element.id.node?.let {
            val property = ElementFactory.createIdentifier(element.project, newName)
            element.node.replaceChild(it, property.firstChild.node)
        }

        return element
    }

    @JvmStatic
    fun getNameIdentifier(element: Identifier): PsiElement {
        return element.id
    }
}
