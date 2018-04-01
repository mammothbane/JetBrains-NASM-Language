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

import com.avaglir.jetbrains.nasmplugin.psi.*
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class Annotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        fun TextRange.highlight(type: HighlightType) {
            val annotation = holder.createInfoAnnotation(this, null)
            annotation.textAttributes = type.attrKey
        }

        fun PsiElement.highlight(type: HighlightType) {
            this.textRange.highlight(type)
        }

        when (element) {
            is Identifier -> {
                element.parent ?: return
                val file = element.parent.containingFile

                when (element.parent) {
                    is IStruct, is Struct -> {
                        element.highlight(HighlightType.STRUCTURE)
                        val children = PsiTreeUtil.getChildrenOfType(element.parent, LabelIdentifier::class.java) ?: return
                        children
                                .filter { it.text.contains(element.text) }
                                .forEach { labelIdent ->
                                    labelIdent.highlight(HighlightType.STRUCTURE)

                                    val offset = labelIdent.textRange.startOffset
                                    val annotation = holder.createInfoAnnotation(TextRange(offset, offset + 1), null)
                                    annotation.textAttributes = HighlightType.SEPARATOR.attrKey
                                }

                        file.findAll<Identifier>()
                                .textMatching(element)
                                .forEach { it.highlight(HighlightType.STRUCTURE) }
                    }

                    is Macro, is MacroCall, is Assign, is Strlen ->
                        element.parent.node.findChildByType(Types.IDENTIFIER)?.textRange?.highlight(HighlightType.MACRO)

                    is Constant -> {
                        element.highlight(HighlightType.CONSTANT)

                        file.findAll<Identifier>()
                            .textMatching(element)
                            .forEach { it.highlight(HighlightType.CONSTANT) }
                    }

                    is Instruction -> {
                        file.findAll<Label>().forEach {
                            if (it.labelIdentifierString == element.text) it.highlight(HighlightType.LABEL)
                        }
                    }
                }

            }

            is Define -> {
                element.defineIdentifier?.highlight(HighlightType.MACRO)

                element.containingFile.findAll<Identifier>().textMatching(element)
                        .filter { it.parent?.let {
                            it !is Define && it !is MacroCall
                        } ?: false }
                        .forEach {it.highlight(HighlightType.MACRO)}
            }

            is Label -> {
                // FIXME
                (element.labelDefMacro?.expr as MacroCall).numericExprList.takeIf { it.size == 1 }?.first()?.highlight(HighlightType.LABEL)
            }

            is LabelIdentifier -> {
                if (element.parent is Struct || element.parent is IStruct) return
                element.id?.highlight(HighlightType.LABEL)
            }

            is StructureField -> {
                val text = element.structField.text

                text.indexOf('.').takeIf { it != -1 }?.let { idx ->
                    val offset = element.structField.textOffset
                    TextRange(offset + idx, offset + idx + 1).highlight(HighlightType.SEPARATOR)
                    TextRange(offset + idx + 1, offset + text.length).highlight(HighlightType.LABEL)
                }

            }

            is SegmentAddress -> {

                fun segLeft() {
                    element.segmentAddrL?.let {
                        val offset = it.textRange.startOffset + it.text.indexOf(':')
                        TextRange(offset, offset + 1).highlight(HighlightType.SEPARATOR)

                        return@segLeft
                    }

                    if (element.lblDef == null) {
                        element.labelDefMacro?.let {
                            val offset = it.textOffset + it.textRange.length - 1
                            TextRange(offset, offset + 1).highlight(HighlightType.SEPARATOR)
                        }

                        return@segLeft
                    }

                    val segElt = element.lblDef!!

                    val sepIdx = segElt.text.indexOf(':')
                    val sepOffset = segElt.textOffset + sepIdx
                    TextRange(sepOffset, sepOffset + 1).highlight(HighlightType.SEPARATOR)

                    val idText = segElt.text.substring(0, sepIdx).trim { it <= ' ' } // this is weird

                    val bundle = HighlightBundle(holder, segElt.textOffset, segElt.textOffset + idText.length, segElt.containingFile)

                    val highlit = bundle.matchAndHighlight<Constant>(HighlightType.CONSTANT) { it.constantIdentifierString == idText } ||
                            bundle.matchAndHighlight<Define>(HighlightType.CONSTANT) { it.defineIdentifier?.textMatches(idText) ?: false } ||
                            bundle.matchAndHighlight<Label>(HighlightType.LABEL) { it.labelIdentifierString?.let { it == idText } ?: false }


                    if (!highlit) TextRange(segElt.textOffset, segElt.textOffset + idText.length).highlight(HighlightType.IDENTIFIER)

                }

                fun segRight() {
                    val addrId = element.id
                    addrId ?: return

                    val range = addrId.textRange
                    val bundle = HighlightBundle(holder, range.startOffset, range.endOffset, addrId.containingFile)
                    val idText = addrId.text

                    val highlit = bundle.matchAndHighlight<Constant>(HighlightType.CONSTANT) { it.constantIdentifierString == idText } ||
                            bundle.matchAndHighlight<Define>(HighlightType.CONSTANT) { it.defineIdentifier?.textMatches(idText) ?: false } ||
                            bundle.matchAndHighlight<Label>(HighlightType.LABEL) { it.labelIdentifierString?.let { it == idText } ?: false }

                    if (!highlit) range.highlight(HighlightType.IDENTIFIER)
                }

                segLeft()
                segRight()
            }
        }
    }

    private data class HighlightBundle(val holder: AnnotationHolder, val start: Int, val end: Int, val file: PsiFile) {
        inline fun <reified T: PsiElement> matchAndHighlight(type: HighlightType, block: (T) -> Boolean): Boolean {
            return file.findAll<T>()
                    .find(block)?.let {
                        val annotation = holder.createInfoAnnotation(TextRange(start, end), null)
                        annotation.textAttributes = type.attrKey
                        true
                    } ?: false
        }
    }
}
