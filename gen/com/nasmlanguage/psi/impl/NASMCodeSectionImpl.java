// This is a generated file. Not intended for manual editing.
package com.nasmlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.nasmlanguage.psi.NASMTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.nasmlanguage.psi.*;

public class NASMCodeSectionImpl extends ASTWrapperPsiElement implements NASMCodeSection {

  public NASMCodeSectionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NASMVisitor visitor) {
    visitor.visitCodeSection(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NASMVisitor) accept((NASMVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<NASMDirective> getDirectiveList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NASMDirective.class);
  }

  @Override
  @NotNull
  public List<NASMInstruction> getInstructionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NASMInstruction.class);
  }

  @Override
  @NotNull
  public PsiElement getCodeSectionName() {
    return findNotNullChildByType(CODE_SECTION_NAME);
  }

  @Override
  @NotNull
  public PsiElement getSectionTag() {
    return findNotNullChildByType(SECTION_TAG);
  }

}