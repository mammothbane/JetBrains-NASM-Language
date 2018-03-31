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

package com.avaglir.jetbrains.nasmplugin;

import com.avaglir.jetbrains.nasmplugin.psi.NASMIdentifier;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NASMReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String id;

    public NASMReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        id = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<NASMIdentifier> identifiers = NASMUtil.findIdentifierReferencesByStringInProject(project, id);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (NASMIdentifier identifier : identifiers) {
            results.add(new PsiElementResolveResult(identifier));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<NASMIdentifier> identifiers = NASMUtil.findIdentifierReferencesInProject(project);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (final NASMIdentifier identifier : identifiers) {
            String identifierText = identifier.getId().getText();
            if (identifierText != null && identifierText.length() > 0) {
                variants.add(LookupElementBuilder.create(identifier).
                        withIcon(NASMIcons.ASM_FILE).
                        withTypeText(identifier.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }

}
