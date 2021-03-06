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

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object FileType : LanguageFileType(Language) {
    override fun getName(): String {
        return "Assembly File"
    }

    override fun getDescription(): String {
        return "Assembly Language"
    }

    override fun getDefaultExtension(): String {
        return EXTENSION
    }

    override fun getIcon(): Icon? {
        return Icons.ASM_FILE
    }

    const val EXTENSION = "asm"

    class Factory : FileTypeFactory() {
        override fun createFileTypes(fileTypeConsumer: FileTypeConsumer) {
            fileTypeConsumer.consume(FileType, FileType.EXTENSION)
        }
    }
}
