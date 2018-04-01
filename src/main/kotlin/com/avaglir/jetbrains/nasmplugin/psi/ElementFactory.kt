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

package com.avaglir.jetbrains.nasmplugin.psi

import com.avaglir.jetbrains.nasmplugin.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory

object ElementFactory {

    fun createIdentifier(project: Project, name: String): Identifier {
        val file = createFile(project, name)
        return file.firstChild as Identifier
    }

    private fun createFile(project: Project, text: String): File {
        val name = "dummy.create.asm"
        return PsiFileFactory.getInstance(project).createFileFromText(name, FileType, text) as File
    }
}
