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

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import javax.swing.Icon
import com.intellij.openapi.options.colors.ColorSettingsPage as JBColorSettingsPage

class ColorSettingsPage : JBColorSettingsPage {
    override fun getIcon(): Icon? {
        return Icons.ASM_FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return NASMSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return ColorSettingsPage.demoText
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return NASM_DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "NASM"
    }

    companion object {
        val demoText = """
            ; Sample NASM file
            
            global func
            
            section .data
            
            floatval    dd 3.14159
            byteval1    db 0xFF
            byteval2    db 0A1h

            binaryval1    db 0b0101

            section .text
            example_constant equ 45

            %macro multi_line_macro 1
                mov    %1, ebx
            %endmacro

            %macro silly 2
                %2: db %1
            %endmacro

            %macro retz 0
                    jnz %%skip
                    ret
                %%skip:
            %endmacro

            %define    single_line_macro(x)    (x+5)

            func:
                multi_line_macro(eax)
                mov    eax, single_line_macro(5)
                xor    ax, ax
                mov    ss, ax ; Set segments
                mov    ds, ax
                mov    es, ax

                fadd    S(0) ; FPU instruction
                pxor    xmm0, xmm0 ; MMX instruction
                cvtsi2ss    xmm0, rax
                mov    eax, dword [ebp + 4*eax - 12h]
                repz ret
        """.trimIndent()

        private val NASM_DESCRIPTORS = arrayOf(
                AttributesDescriptor("Number", HighlightType.NUMBER.attrKey), 
                AttributesDescriptor("Constant", HighlightType.CONSTANT.attrKey), 
                AttributesDescriptor("Separator", HighlightType.SEPARATOR.attrKey), 
                AttributesDescriptor("Register", HighlightType.REGISTER.attrKey), 
                AttributesDescriptor("Segment Register", HighlightType.SEGMENT_REGISTER.attrKey), 
                AttributesDescriptor("Operation", HighlightType.OPERATION.attrKey), 
                AttributesDescriptor("String", HighlightType.STRING.attrKey), 
                AttributesDescriptor("Macro Identifier", HighlightType.MACRO.attrKey), 
                AttributesDescriptor("Macro Parameter Reference", HighlightType.MACRO_PARAM_REF.attrKey), 
                AttributesDescriptor("Macro Variable Reference", HighlightType.MACRO_VAR_REF.attrKey), 
                AttributesDescriptor("Macro Label", HighlightType.MACRO_LABEL.attrKey), 
                AttributesDescriptor("Label", HighlightType.LABEL.attrKey), 
                AttributesDescriptor("Structure", HighlightType.STRUCTURE.attrKey), 
                AttributesDescriptor("Size Type", HighlightType.SIZE_TYPE.attrKey), 
                AttributesDescriptor("Instruction Prefix", HighlightType.OP_PREFIX.attrKey)
        )
    }
}
