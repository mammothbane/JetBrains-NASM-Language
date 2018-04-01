/*
 * Modified 3/31/18 - converted to Kotlin and rewrote completely (Nathan Perry).
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

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.indexing.FileBasedIndex

inline fun <reified T: PsiElement> Project.findAll(): List<T> {
    @Suppress("UNCHECKED_CAST")
    val virtFiles = FileBasedIndex.getInstance()
            .getContainingFiles<FileType, Void>(
                    FileTypeIndex.NAME,
                    FileType,
                    GlobalSearchScope.allScope(this)
    )

    return virtFiles.flatMap { (it as PsiFile).findAll<T>() }
}

inline fun <reified T: PsiElement> PsiFile.findAll(): Collection<T> {
    return PsiTreeUtil.collectElementsOfType(this, T::class.java)
}

inline fun <reified T: PsiElement> Collection<T>.textMatching(elt: PsiElement): Collection<T> {
    return this.filter { it != elt && it.textMatches(elt) }
}
