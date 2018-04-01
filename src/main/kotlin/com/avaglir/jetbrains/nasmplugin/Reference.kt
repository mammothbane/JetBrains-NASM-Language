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

package com.avaglir.jetbrains.nasmplugin

import com.avaglir.jetbrains.nasmplugin.psi.Identifier
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*

class Reference(element: PsiElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {
    private val id: String = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> = myElement.project.findAll<Identifier>()
            .filter { it.id.text == id }.map { PsiElementResolveResult(it) }.toTypedArray()

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> = myElement.project.findAll<Identifier>()
                .filter { it.text.isNotEmpty() }
                .map {
                    LookupElementBuilder.create(it).withIcon(Icons.ASM_FILE).withTypeText(it.containingFile.name)
                }.toTypedArray()
}
