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

import com.avaglir.jetbrains.nasmplugin.psi.Types
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

enum class HighlightType(val externalName: String, val fallbackAttrKey: TextAttributesKey, vararg val elementTypes: IElementType) {
    REGISTER("REGISTER", DefaultLanguageHighlighterColors.LOCAL_VARIABLE, Types.REGISTER),
    SEGMENT_REGISTER("SEGMENT_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE, Types.SEGMENT_REGISTER),

    OPERATION("OPERATION", DefaultLanguageHighlighterColors.KEYWORD,
            Types.GENERAL_OP,
            Types.SYSTEM_OP,
            Types.VIRTUALIZATION_OP,
            Types.X64_OP,
            Types.FPU_OP,
            Types.MMX_OP,
            Types.SSE_OP,
            Types.SSE2_OP,
            Types.SSE3_OP,
            Types.SSE4_OP,
            Types.AVX_OP,
            Types.AVX2_OP,
            Types.AVX512_OP,
            Types.DATA_OP
        ),

    NUMBER("NUMBER", DefaultLanguageHighlighterColors.NUMBER,
            Types.NUMERIC_LITERAL, Types.BINARY, Types.HEXADECIMAL,
            Types.ZEROES, Types.DECIMAL, Types.SEGMENT_ADDR_L
    ),

    LABEL("LABEL", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION,
            Types.LBL, Types.LBL_DEF
            //, Types.LBL_INS, Types.LBL_DATA
    ),

    SEPARATOR("SEPARATOR", DefaultLanguageHighlighterColors.COMMA, Types.SEPARATOR),
    SIZE_TYPE("SIZE_TYPE", DefaultLanguageHighlighterColors.METADATA, Types.SIZE_TYPE),
    OP_PREFIX("OP_PREFIX", DefaultLanguageHighlighterColors.METADATA, Types.OP_PREFIX),
    COMMENT("COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT, Types.COMMENT),
    IDENTIFIER("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER, Types.IDENTIFIER),

    MACRO("MACRO", DefaultLanguageHighlighterColors.CONSTANT, Types.MACRO, Types.MACRO_CALL),
    MACRO_PARAM_REF("MACRO_PARAM_REF", DefaultLanguageHighlighterColors.PARAMETER, Types.MACRO_PARAM_REF),
    MACRO_VAR_REF("MACRO_VAR_REF", HighlightType.MACRO_PARAM_REF.fallbackAttrKey, Types.MACRO_VAR_REF),
    MACRO_LABEL("MACRO_LABEL", HighlightType.LABEL.fallbackAttrKey, Types.MACRO_LABEL),

    DIRECTIVE("DIRECTIVE", DefaultLanguageHighlighterColors.KEYWORD,
            Types.DIRECTIVE_OP, Types.SECTION, Types.SEGMENT, Types.EQU),

    STRING("STRING", DefaultLanguageHighlighterColors.STRING, Types.STRING),
    CONSTANT("CONSTANT", DefaultLanguageHighlighterColors.CONSTANT, Types.CONSTANT),
    STRUCTURE("STRUCTURE", DefaultLanguageHighlighterColors.CLASS_NAME, Types.STRUCTURE),
    STRUCTURE_FIELD("STRUCTURE_FIELD", HighlightType.STRUCTURE.fallbackAttrKey, Types.STRUCT_FIELD),
    BAD_CHARACTER("BAD_CHARACTER", HighlighterColors.BAD_CHARACTER, TokenType.BAD_CHARACTER);

    val attrKey by lazy {
        elementTypes.firstOrNull()?.let { type -> HighlightType.index[type] } ?: TextAttributesKey.createTextAttributesKey(externalName, fallbackAttrKey)
    }

    companion object {
        val index by lazy {
            HighlightType.values().flatMap { v ->
                v.elementTypes.map { type -> type to TextAttributesKey.createTextAttributesKey(v.externalName, v.fallbackAttrKey) }
            }.toMap()
        }
    }
}