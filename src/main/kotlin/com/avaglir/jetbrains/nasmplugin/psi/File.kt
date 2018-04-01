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

package com.avaglir.jetbrains.nasmplugin.psi

import com.avaglir.jetbrains.nasmplugin.FileType
import com.avaglir.jetbrains.nasmplugin.Language
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class File(private val fileViewProvider: FileViewProvider) : PsiFileBase(fileViewProvider, Language) {

    override fun getFileType(): FileType {
        return FileType
    }

    override fun toString(): String {
        val virtualFile = if (fileViewProvider.isEventSystemEnabled)
            fileViewProvider.virtualFile
        else
            null
        return "Assembly File: " + (virtualFile?.name ?: "<unknown>")
    }
}