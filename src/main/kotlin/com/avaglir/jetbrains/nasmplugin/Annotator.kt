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
                    is IStruc, is Struc -> {
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
                        element.parent.node.findChildByType(NASMTypes.IDENTIFIER)?.textRange.highlight(HighlightType.MACRO)

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
                element.defineIdentifier.highlight(HighlightType.MACRO)

                element.containingFile.findAll<Identifier>().textMatching(element)
                        .filter { it.parent?.let {
                            it !is Define && it !is MacroCall
                        } ?: false }
                        .forEach {it.highlight(HighlightType.MACRO)}
            }

            is Label -> {
                // TODO
                element.labelDefMacro?.getMacroCall().getNumericExprList().takeIf { it.size == 1 }?.first().highlight(HighlightType.LABEL)
            }

            is LabelIdentifier -> {

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
                element.segmentAddrL?.let {
                    val offset = it.textRange.startOffset + it.text.indexOf(':')
                    TextRange(offset, offset + 1).highlight(HighlightType.SEPARATOR)

                    return@annotate
                }

                if (element.lblDef == null) {

                    segmentElement = element.labelDefMacro
                    if (segmentElement != null) { // Its an macro on the left
                        val tr = segmentElement.textRange
                        highlightTextRange(tr.startOffset + tr.length - 1, 1,
                                NASMSyntaxHighlighter.NASM_SEPARATOR, holder)
                    }

                    return@annotate
                }

                element.lblDef?.let { segElt ->
                    val sepIdx = segElt.text.indexOf(':')
                    val sepOffset = segElt.textOffset + sepIdx
                    TextRange(sepOffset, sepOffset + 1).highlight(HighlightType.SEPARATOR)

                    val idText = segElt.text.substring(0, sepIdx).trim { it <= ' ' } // this is weird
                    element.containingFile.findAll<Constant>()
                            .find { const ->
                                const.constantIdentifierString == idText
                            }?.let {
                                TextRange(segElt.textOffset, segElt.textOffset + idText.length).highlight(HighlightType.CONSTANT)
                            }

                    return@annotate
                }


            }
        }

    if (element is NASMSegmentAddress) {
            // Handle segment (left side) value
            var segmentElement = element.segmentAddrL
            if (segmentElement != null) { // Its a number on the left
                val segAddrText = segmentElement.text
                val separatorIdx = segAddrText.indexOf(':')
                val tr = segmentElement.textRange
                highlightTextRange(tr.startOffset + separatorIdx, 1, NASMSyntaxHighlighter.NASM_SEPARATOR, holder)
            } else {
                segmentElement = element.lblDef
                if (segmentElement != null) { // Its an identifer on the left
                    val lblDefText = segmentElement.text
                    val separatorIdx = lblDefText.indexOf(':')
                    val tr = segmentElement.textRange
                    highlightTextRange(tr.startOffset + separatorIdx, 1, NASMSyntaxHighlighter.NASM_SEPARATOR, holder)
                    val identifierText = lblDefText.substring(0, separatorIdx).trim { it <= ' ' }
                    var found = false
                    // Search for a constant
                    val constants = NASMUtil.findConstants(element.getContainingFile())
                    for (constant in constants) {
                        val constantIdentifier = constant.constantIdentifierString
                        if (constantIdentifier != null && constantIdentifier == identifierText) {
                            found = true
                            highlightTextRange(tr.startOffset, identifierText.length,
                                    NASMSyntaxHighlighter.NASM_CONSTANT, holder)
                            break
                        }
                    }
                    // Search for a preprocessor define
                    if (!found) {
                        val defines = NASMUtil.findPreprocessorDefines(element.getContainingFile())
                        for (define in defines) {
                            val defineIdentifier = define.defineIdentifierString
                            if (defineIdentifier != null && defineIdentifier == identifierText) {
                                found = true
                                highlightTextRange(tr.startOffset, identifierText.length, NASMSyntaxHighlighter.NASM_CONSTANT, holder)
                                break
                            }
                        }
                    }
                    // Search for a label
                    if (!found) {
                        val labels = NASMUtil.findLabels(element.getContainingFile())
                        for (label in labels) {
                            val labelIdentifier = label.labelIdentifierString
                            if (labelIdentifier != null && labelIdentifier == identifierText) {
                                found = true
                                highlightTextRange(tr.startOffset, identifierText.length,
                                        NASMSyntaxHighlighter.NASM_LABEL, holder)
                                break
                            }
                        }
                    }
                    // If a match wasnt found, color it a generic identifier color
                    if (!found) {
                        highlightTextRange(tr.startOffset, identifierText.length,
                                NASMSyntaxHighlighter.NASM_IDENTIFIER, holder)
                    }
                } else {
                    segmentElement = element.labelDefMacro
                    if (segmentElement != null) { // Its an macro on the left
                        val tr = segmentElement.textRange
                        highlightTextRange(tr.startOffset + tr.length - 1, 1,
                                NASMSyntaxHighlighter.NASM_SEPARATOR, holder)
                    }
                }// Label def macro
            }// Label def
            // Handle address (right side) value
            val addrIdentifier = element.id
            if (addrIdentifier != null) { // if it is not null that means the address value is an identifier
                val addrIdentifierText = addrIdentifier.text
                val tr = addrIdentifier.textRange
                var found = false
                // Search for a constant
                val constants = NASMUtil.findConstants(element.getContainingFile())
                for (constant in constants) {
                    val constantIdentifier = constant.constantIdentifierString
                    if (constantIdentifier == addrIdentifierText) {
                        found = true
                        highlightTextRange(tr, NASMSyntaxHighlighter.NASM_CONSTANT, holder)
                        break
                    }
                }
                // Search for a preprocessor define
                if (!found) {
                    val defines = NASMUtil.findPreprocessorDefines(element.getContainingFile())
                    for (define in defines) {
                        val defineIdentifier = define.defineIdentifierString
                        if (defineIdentifier != null && defineIdentifier == addrIdentifierText) {
                            found = true
                            highlightTextRange(tr, NASMSyntaxHighlighter.NASM_CONSTANT, holder)
                            break
                        }
                    }
                }
                // Search for a label
                if (!found) {
                    val labels = NASMUtil.findLabels(element.getContainingFile())
                    for (label in labels) {
                        val labelIdentifier = label.labelIdentifierString
                        if (labelIdentifier != null && labelIdentifier == addrIdentifierText) {
                            found = true
                            highlightTextRange(tr, NASMSyntaxHighlighter.NASM_LABEL, holder)
                            break
                        }
                    }
                }
                // If a match wasnt found, color it a generic identifier color
                if (!found) {
                    highlightTextRange(tr, NASMSyntaxHighlighter.NASM_IDENTIFIER, holder)
                }
            } // Otherwise hexadecimal values are already highlighted properly
        }
    }

}
