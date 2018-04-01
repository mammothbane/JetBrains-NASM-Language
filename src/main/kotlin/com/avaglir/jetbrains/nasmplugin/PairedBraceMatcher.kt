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
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class PairedBraceMatcher : PairedBraceMatcher {

    override fun getPairs(): Array<BracePair> {
        return BRACE_PAIRS
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, type: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }

    companion object {
        private val BRACE_PAIRS = arrayOf(
            BracePair(Types.SQUARE_L, Types.SQUARE_R, true),
            BracePair(Types.ROUND_L, Types.ROUND_R, false),
            BracePair(Types.MACRO_TAG, Types.MACRO_END_TAG, true),
            BracePair(Types.STRUC_TAG, Types.ENDSTRUC_TAG, true),
            BracePair(Types.ISTRUC_TAG, Types.IEND_TAG, true),
            BracePair(Types.IF_TAG, Types.ENDIF_TAG, true),
            BracePair(Types.IFMACRO_TAG, Types.ENDIF_TAG, true)
        )//new BracePair(Types.ELIF_TAG, Types.ENDIF_TAG, false),
    }
}
