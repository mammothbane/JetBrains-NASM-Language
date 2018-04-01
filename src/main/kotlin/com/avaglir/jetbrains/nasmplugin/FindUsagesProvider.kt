/*
 * Modified 3/31/18 - converted to Kotlin (Nathan Perry).
 * Modifications copyright (c) 2018 Nathan Perry.
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

package com.avaglir.jetbrains.nasmplugin

import com.avaglir.jetbrains.nasmplugin.psi.NASMTypes
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet

class FindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner? {
        return DefaultWordsScanner(Lexer.compatLexer(),
                TokenSet.create(NASMTypes.IDENTIFIER),
                TokenSet.create(NASMTypes.COMMENT),
                TokenSet.EMPTY)
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }

    override fun getHelpId(psiElement: PsiElement): String? {
        return null
    }

    override fun getType(element: PsiElement): String {
        return "Identifier Usage"
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return element.toString()
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return element.toString()
    }

}
