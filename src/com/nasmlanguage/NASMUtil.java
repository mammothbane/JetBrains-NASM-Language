/*
 * Copyright (c) 2018 Nathan Perry, Aidan Khoury. All rights reserved.
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

package com.nasmlanguage;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import com.nasmlanguage.psi.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class NASMUtil {

    static List<PsiElement> findPreprocessorMacrosAndDefines(Project project) {
        List<PsiElement> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
                FileTypeIndex.NAME, NASMFileType.INSTANCE, GlobalSearchScope.allScope(project)
        );
        for (VirtualFile virtualFile : virtualFiles) {
            NASMFile assemblyFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
            if (assemblyFile != null) {
                Collection<NASMPreprocessor> nasmPreprocessors = PsiTreeUtil.collectElementsOfType(assemblyFile, NASMPreprocessor.class);
                if (!nasmPreprocessors.isEmpty()) {
                    for (NASMPreprocessor nasmPreprocessor : nasmPreprocessors) {
                        NASMMacro macro = nasmPreprocessor.getMacro();
                        if (macro != null) {
                            result.add(macro);
                            continue;
                        }
                        NASMDefine define = nasmPreprocessor.getDefine();
                        if (define != null) {
                            result.add(define);
                        }
                    }
                }
            }
        }
        return result;
    }

    static List<NASMMacro> findPreprocessorMacros(Project project) {
        List<NASMMacro> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
            FileTypeIndex.NAME, NASMFileType.INSTANCE, GlobalSearchScope.allScope(project)
        );
        for (VirtualFile virtualFile : virtualFiles) {
            NASMFile assemblyFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
            if (assemblyFile != null) {
                Collection<NASMPreprocessor> nasmPreprocessors = PsiTreeUtil.collectElementsOfType(assemblyFile, NASMPreprocessor.class);
                if (!nasmPreprocessors.isEmpty()) {
                    for (NASMPreprocessor nasmPreprocessor : nasmPreprocessors) {
                        NASMMacro macro = nasmPreprocessor.getMacro();
                        if (macro != null)
                            result.add(macro);
                    }
                }
            }
        }
        return result;
    }

    static List<NASMDefine> findPreprocessorDefines(PsiFile containingFile) {
        List<NASMDefine> result = new ArrayList<>();

        // Check the containing file's preprocessor defines
        Collection<NASMDefine> nasmDefines = PsiTreeUtil.collectElementsOfType(containingFile, NASMDefine.class);
        if (!nasmDefines.isEmpty())
            result.addAll(nasmDefines);

        // Makes this plugin perform like shit
        //Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
        //        FileTypeIndex.NAME, NASMFileType.INSTANCE, GlobalSearchScope.allScope(project)
        //);
        //for (VirtualFile virtualFile : virtualFiles) {
        //    NASMFile assemblyFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
        //    if (assemblyFile != null) {
        //        Collection<NASMPreprocessor> nasmPreprocessors = PsiTreeUtil.collectElementsOfType(assemblyFile, NASMPreprocessor.class);
        //        if (!nasmPreprocessors.isEmpty()) {
        //            for (NASMPreprocessor nasmPreprocessor : nasmPreprocessors) {
        //                NASMDefine define = nasmPreprocessor.getDefine();
        //                if (define != null)
        //                    result.add(define);
        //            }
        //        }
        //    }
        //}

        return result;
    }

    static List<NASMLabel> findLabels(PsiFile containingFile) {
        List<NASMLabel> result = new ArrayList<>();

        // Check the containing file's labels
        Collection<NASMLabel> nasmLabels = PsiTreeUtil.collectElementsOfType(containingFile, NASMLabel.class);
        if (!nasmLabels.isEmpty())
            result.addAll(nasmLabels);

        // Makes this plugin perform like shit
        //Project project = containingFile.getProject();
        //// Then check each include file for labels
        //Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
        //        FileTypeIndex.NAME, NASMFileType.INSTANCE, GlobalSearchScope.allScope(project)
        //);
        //Collection<NASMInclude> includes = PsiTreeUtil.collectElementsOfType(containingFile, NASMInclude.class);
        //for (NASMInclude include : includes) {
        //    String includeFileName = include.getIncludeString();
        //    for (VirtualFile virtualFile : virtualFiles) {
        //        String virtFileName = virtualFile.getName();
        //        if (virtFileName.equals(includeFileName)) {
        //            NASMFile assemblyFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
        //            if (assemblyFile != null) {
        //                nasmLabels = PsiTreeUtil.collectElementsOfType(assemblyFile, NASMLabel.class);
        //                if (!nasmLabels.isEmpty()) {
        //                    result.addAll(nasmLabels);
        //                }
        //            }
        //        }
        //    }
        //}

        return result;
    }

    static List<NASMConstant> findConstants(PsiFile containingFile) {
        List<NASMConstant> result = new ArrayList<>();

        // First check the containing file's constants
        Collection<NASMConstant> nasmConstants = PsiTreeUtil.collectElementsOfType(containingFile, NASMConstant.class);
        if (!nasmConstants.isEmpty())
            result.addAll(nasmConstants);

        // Makes this plugin perform like shit
        //Project project = containingFile.getProject();
        //// Then check each include file for constants
        //Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
        //        FileTypeIndex.NAME, NASMFileType.INSTANCE, GlobalSearchScope.allScope(project)
        //);
        //Collection<NASMInclude> includes = PsiTreeUtil.collectElementsOfType(containingFile, NASMInclude.class);
        //for (NASMInclude include : includes) {
        //    String includeFileName = include.getIncludeString();
        //    for (VirtualFile virtualFile : virtualFiles) {
        //        String virtFileName = virtualFile.getName();
        //        if (virtFileName.equals(includeFileName)) {
        //            NASMFile assemblyFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
        //            if (assemblyFile != null) {
        //                nasmConstants = PsiTreeUtil.collectElementsOfType(assemblyFile, NASMConstant.class);
        //                if (!nasmConstants.isEmpty()) {
        //                    result.addAll(nasmConstants);
        //                }
        //            }
        //        }
        //    }
        //}

        return result;
    }

    static List<NASMStructure> findStructures(Project project) {
        List<NASMStructure> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
                FileTypeIndex.NAME, NASMFileType.INSTANCE, GlobalSearchScope.allScope(project)
        );
        for (VirtualFile virtualFile : virtualFiles) {
            NASMFile assemblyFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
            if (assemblyFile != null) {
                Collection<NASMStructure> nasmStructs = PsiTreeUtil.collectElementsOfType(assemblyFile, NASMStructure.class);
                if (!nasmStructs.isEmpty()) {
                    result.addAll(nasmStructs);
                }
            }
        }
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    static List<NASMIdentifier> findIdentifierReferences(PsiFile containingFile, NASMIdentifier identifier) {

        List<NASMIdentifier> result = new ArrayList<>();
        PsiElement targetIdentifierId = identifier.getId();
        if (targetIdentifierId != null) {
            // First check the containing file's identifiers
            Collection<NASMIdentifier> nasmIdentifiers = PsiTreeUtil.collectElementsOfType(containingFile, NASMIdentifier.class);
            for (NASMIdentifier nasmIdentifier : nasmIdentifiers) {
                if (nasmIdentifier != identifier) {
                    PsiElement nasmIdentifierId = nasmIdentifier.getId();
                    if (nasmIdentifierId != null) {
                        if (nasmIdentifierId.getText().equals(targetIdentifierId.getText())) {
                            result.add(nasmIdentifier);
                        }
                    }
                }
            }

            //Project project = containingFile.getProject();
            //// Then check each include file for identifiers
            //Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
            //        FileTypeIndex.NAME, NASMFileType.INSTANCE, GlobalSearchScope.allScope(project)
            //);
            //Collection<NASMInclude> includes = PsiTreeUtil.collectElementsOfType(containingFile, NASMInclude.class);
            //for (NASMInclude include : includes) {
            //    String includeFileName = include.getIncludeString();
            //    for (VirtualFile virtualFile : virtualFiles) {
            //        String virtFileName = virtualFile.getName();
            //        if (virtFileName.equals(includeFileName)) {
            //            NASMFile assemblyFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
            //            if (assemblyFile != null) {
            //                nasmIdentifiers = PsiTreeUtil.collectElementsOfType(assemblyFile, NASMIdentifier.class);
            //                if (!nasmIdentifiers.isEmpty()) {
            //                    for (NASMIdentifier nasmIdentifier : nasmIdentifiers) {
            //                        if (nasmIdentifier == identifier) continue;
            //                        PsiElement nasmIdentifierId = nasmIdentifier.getId();
            //                        if (nasmIdentifierId != null) {
            //                            if (nasmIdentifierId.getText().equals(targetIdentifierId.getText())) {
            //                                result.add(nasmIdentifier);
            //                            }
            //                        }
            //                    }
            //                }
            //            }
            //        }
            //    }
            //}
        }

        return result;
    }

    @SuppressWarnings("ConstantConditions")
    static List<NASMIdentifier> findIdentifierReferencesByString(PsiFile containingFile, String targetIdentifierId) {
        List<NASMIdentifier> result = null;
        // First check the containing file's identifiers
        Collection<NASMIdentifier> nasmIdentifiers = PsiTreeUtil.collectElementsOfType(containingFile, NASMIdentifier.class);
        for (NASMIdentifier nasmIdentifier : nasmIdentifiers) {
            if (targetIdentifierId.equals(nasmIdentifier.getId().getText())) {
                if (result == null)
                    result = new ArrayList<>();
                result.add(nasmIdentifier);
            }
        }

        //List<NASMIdentifier> result = null;
        //Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
        //        NASMFileType.INSTANCE, GlobalSearchScope.allScope(project));
        //for (VirtualFile virtualFile : virtualFiles) {
        //    NASMFile nasmFile = (NASMFile) PsiManager.getInstance(project).findFile(virtualFile);
        //    if (nasmFile != null) {
        //        NASMIdentifier[] identifiers = PsiTreeUtil.getChildrenOfType(nasmFile, NASMIdentifier.class);
        //        if (identifiers != null) {
        //            for (NASMIdentifier identifier : identifiers) {
        //                if (targetIdentifierId.equals(identifier.getId().getText())) {
        //                    if (result == null) {
        //                        result = new ArrayList<NASMIdentifier>();
        //                    }
        //                    result.add(identifier);
        //                }
        //            }
        //        }
        //    }
        //}
        //return result != null ? result : Collections.<NASMIdentifier>emptyList();

        return result != null ? result : Collections.emptyList();
    }

    @SuppressWarnings("ConstantConditions")
    static List<NASMIdentifier> findIdentifierReferencesByStringInProject(Project project, String targetIdentifierId) {
        List<NASMIdentifier> result = null;
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
                NASMFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            NASMFile nasmFile = (NASMFile)PsiManager.getInstance(project).findFile(virtualFile);
            if (nasmFile != null) {
                NASMIdentifier[] identifiers = PsiTreeUtil.getChildrenOfType(nasmFile, NASMIdentifier.class);
                if (identifiers != null) {
                    for (NASMIdentifier identifier : identifiers) {
                        if (targetIdentifierId.equals(identifier.getId().getText())) {
                            if (result == null) {
                                result = new ArrayList<>();
                            }
                            result.add(identifier);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.emptyList();
    }

    @SuppressWarnings("ConstantConditions")
    static List<NASMIdentifier> findIdentifierReferencesInProject(Project project) {
        List<NASMIdentifier> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
                NASMFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            NASMFile nasmFile = (NASMFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (nasmFile != null) {
                NASMIdentifier[] identifiers = PsiTreeUtil.getChildrenOfType(nasmFile, NASMIdentifier.class);
                if (identifiers != null) {
                    Collections.addAll(result, identifiers);
                }
            }
        }
        return result != null ? result : Collections.emptyList();
    }

}
