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
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

enum class HighlightType(val externalName: String, val fallbackAttrKey: TextAttributesKey, vararg val types: IElementType) {
    REGISTER("REGISTER", DefaultLanguageHighlighterColors.LOCAL_VARIABLE, NASMTypes.REGISTER),
    SEGMENT_REGISTER("SEGMENT_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE, NASMTypes.SEGMENT_REGISTER),

    OPERATION("OPERATION", DefaultLanguageHighlighterColors.KEYWORD,
            NASMTypes.GENERAL_OP,
            NASMTypes.SYSTEM_OP,
            NASMTypes.VIRTUALIZATION_OP,
            NASMTypes.X64_OP,
            NASMTypes.FPU_OP,
            NASMTypes.MMX_OP,
            NASMTypes.SSE_OP,
            NASMTypes.SSE2_OP,
            NASMTypes.SSE3_OP,
            NASMTypes.SSE4_OP,
            NASMTypes.AVX_OP,
            NASMTypes.AVX2_OP,
            NASMTypes.AVX512_OP,
            NASMTypes.DATA_OP
        ),

    NUMBER("NUMBER", DefaultLanguageHighlighterColors.NUMBER,
            NASMTypes.NUMERIC_LITERAL, NASMTypes.BINARY, NASMTypes.HEXADECIMAL,
            NASMTypes.ZEROES, NASMTypes.DECIMAL, NASMTypes.SEGMENT_ADDR_L
    ),

    LABEL("LABEL", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION,
            NASMTypes.LBL, NASMTypes.LBL_DEF
            //, NASMTypes.LBL_INS, NASMTypes.LBL_DATA
    ),

    SEPARATOR("SEPARATOR", DefaultLanguageHighlighterColors.COMMA, NASMTypes.SEPARATOR),
    SIZE_TYPE("SIZE_TYPE", DefaultLanguageHighlighterColors.METADATA, NASMTypes.SIZE_TYPE),
    OP_PREFIX("OP_PREFIX", DefaultLanguageHighlighterColors.METADATA, NASMTypes.OP_PREFIX),
    COMMENT("COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT, NASMTypes.COMMENT),
    IDENTIFIER("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER, NASMTypes.IDENTIFIER),

    MACRO("MACRO", DefaultLanguageHighlighterColors.CONSTANT, NASMTypes.MACRO, NASMTypes.MACRO_CALL),
    MACRO_PARAM_REF("MACRO_PARAM_REF", DefaultLanguageHighlighterColors.PARAMETER, NASMTypes.MACRO_PARAM_REF),
    MACRO_VAR_REF("MACRO_VAR_REF", HighlightType.MACRO_PARAM_REF.fallbackAttrKey, NASMTypes.MACRO_VAR_REF),
    MACRO_LABEL("MACRO_LABEL", HighlightType.LABEL.fallbackAttrKey, NASMTypes.MACRO_LABEL),

    DIRECTIVE("DIRECTIVE", DefaultLanguageHighlighterColors.KEYWORD,
            NASMTypes.DIRECTIVE_OP, NASMTypes.SECTION, NASMTypes.SEGMENT, NASMTypes.EQU),

    STRING("STRING", DefaultLanguageHighlighterColors.STRING, NASMTypes.STRING),
    CONSTANT("CONSTANT", DefaultLanguageHighlighterColors.CONSTANT, NASMTypes.CONSTANT),
    STRUCTURE("STRUCTURE", DefaultLanguageHighlighterColors.CLASS_NAME, NASMTypes.STRUCTURE),
    STRUCTURE_FIELD("STRUCTURE_FIELD", HighlightType.STRUCTURE.fallbackAttrKey, NASMTypes.STRUCT_FIELD),
    BAD_CHARACTER("BAD_CHARACTER", HighlighterColors.BAD_CHARACTER, TokenType.BAD_CHARACTER);

    val attrKey by lazy {
        types.firstOrNull()?.let { type -> HighlightType.index[type] } ?: TextAttributesKey.createTextAttributesKey(externalName, fallbackAttrKey)
    }

    companion object {
        val index by lazy {
            HighlightType.values().flatMap { v ->
                v.types.map { type -> type to TextAttributesKey.createTextAttributesKey(v.externalName, v.fallbackAttrKey) }
            }.toMap()
        }
    }
}