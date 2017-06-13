// This is a generated file. Not intended for manual editing.
package com.nasmlanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.nasmlanguage.psi.impl.*;

public interface NASMTypes {

  IElementType ADDRESS = new NASMElementType("ADDRESS");
  IElementType CODE_SECTION = new NASMElementType("CODE_SECTION");
  IElementType DATA = new NASMElementType("DATA");
  IElementType DATA_SECTION = new NASMElementType("DATA_SECTION");
  IElementType DATA_VALUE = new NASMElementType("DATA_VALUE");
  IElementType DEFINE = new NASMElementType("DEFINE");
  IElementType DIRECTIVE = new NASMElementType("DIRECTIVE");
  IElementType DIRECTIVE_ARG = new NASMElementType("DIRECTIVE_ARG");
  IElementType INCLUDE = new NASMElementType("INCLUDE");
  IElementType INSTRUCTION = new NASMElementType("INSTRUCTION");
  IElementType MACRO = new NASMElementType("MACRO");
  IElementType PREPROCESSOR = new NASMElementType("PREPROCESSOR");

  IElementType BSS_SECTION_NAME = new NASMTokenType("BSS_SECTION_NAME");
  IElementType CODE_SECTION_NAME = new NASMTokenType("CODE_SECTION_NAME");
  IElementType COLON = new NASMTokenType(":");
  IElementType COMMENT = new NASMTokenType("COMMENT");
  IElementType CRLF = new NASMTokenType("CRLF");
  IElementType DATA_OP = new NASMTokenType("DATA_OP");
  IElementType DATA_SECTION_NAME = new NASMTokenType("DATA_SECTION_NAME");
  IElementType DEFINE_TAG = new NASMTokenType("DEFINE_TAG");
  IElementType DIRECTIVE_OP = new NASMTokenType("DIRECTIVE_OP");
  IElementType DIVIDE = new NASMTokenType("\\");
  IElementType DOLLARSIGN = new NASMTokenType("$");
  IElementType DOLLARSIGN2 = new NASMTokenType("$$");
  IElementType EQU = new NASMTokenType("EQU");
  IElementType IDENTIFIER = new NASMTokenType("IDENTIFIER");
  IElementType INCLUDE_TAG = new NASMTokenType("INCLUDE_TAG");
  IElementType INS_64_BIT = new NASMTokenType("INS_64_BIT");
  IElementType INS_BINARY_ARITH = new NASMTokenType("INS_BINARY_ARITH");
  IElementType INS_BINARY_LOGICAL = new NASMTokenType("INS_BINARY_LOGICAL");
  IElementType INS_BINARY_OTHER = new NASMTokenType("INS_BINARY_OTHER");
  IElementType INS_BINARY_ROTATE = new NASMTokenType("INS_BINARY_ROTATE");
  IElementType INS_BINARY_SET = new NASMTokenType("INS_BINARY_SET");
  IElementType INS_BIT_MANIPULATION = new NASMTokenType("INS_BIT_MANIPULATION");
  IElementType INS_CONTROL_TRANS = new NASMTokenType("INS_CONTROL_TRANS");
  IElementType INS_DATA_TRANS_MOV = new NASMTokenType("INS_DATA_TRANS_MOV");
  IElementType INS_DATA_TRANS_OTHER = new NASMTokenType("INS_DATA_TRANS_OTHER");
  IElementType INS_DATA_TRANS_XCHG = new NASMTokenType("INS_DATA_TRANS_XCHG");
  IElementType INS_DECIMAL_ARITH = new NASMTokenType("INS_DECIMAL_ARITH");
  IElementType INS_FLAG_CONTROL = new NASMTokenType("INS_FLAG_CONTROL");
  IElementType INS_INPUT_OUTPUT = new NASMTokenType("INS_INPUT_OUTPUT");
  IElementType INS_MISC_OTHER = new NASMTokenType("INS_MISC_OTHER");
  IElementType INS_PREFIX = new NASMTokenType("INS_PREFIX");
  IElementType INS_RNG_RAND = new NASMTokenType("INS_RNG_RAND");
  IElementType INS_SEG_REGS = new NASMTokenType("INS_SEG_REGS");
  IElementType INS_STRING_DATA = new NASMTokenType("INS_STRING_DATA");
  IElementType LABEL = new NASMTokenType("LABEL");
  IElementType LABEL_DEF = new NASMTokenType("LABEL_DEF");
  IElementType MACRO_END_TAG = new NASMTokenType("MACRO_END_TAG");
  IElementType MACRO_TAG = new NASMTokenType("MACRO_TAG");
  IElementType MINUS = new NASMTokenType("-");
  IElementType MNEMONIC_OP = new NASMTokenType("MNEMONIC_OP");
  IElementType NUMBER = new NASMTokenType("NUMBER");
  IElementType PERCENT = new NASMTokenType("%");
  IElementType PLUS = new NASMTokenType("+");
  IElementType PREPROCESSOR_OP = new NASMTokenType("PREPROCESSOR_OP");
  IElementType REGISTER = new NASMTokenType("REGISTER");
  IElementType ROUND_L = new NASMTokenType("(");
  IElementType ROUND_R = new NASMTokenType(")");
  IElementType SECTION_TAG = new NASMTokenType("SECTION_TAG");
  IElementType SEPARATOR = new NASMTokenType(",");
  IElementType SIZE_TYPE = new NASMTokenType("SIZE_TYPE");
  IElementType SQUARE_L = new NASMTokenType("[");
  IElementType SQUARE_R = new NASMTokenType("]");
  IElementType STRING = new NASMTokenType("STRING");
  IElementType TIMES = new NASMTokenType("*");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ADDRESS) {
        return new NASMAddressImpl(node);
      }
      else if (type == CODE_SECTION) {
        return new NASMCodeSectionImpl(node);
      }
      else if (type == DATA) {
        return new NASMDataImpl(node);
      }
      else if (type == DATA_SECTION) {
        return new NASMDataSectionImpl(node);
      }
      else if (type == DATA_VALUE) {
        return new NASMDataValueImpl(node);
      }
      else if (type == DEFINE) {
        return new NASMDefineImpl(node);
      }
      else if (type == DIRECTIVE) {
        return new NASMDirectiveImpl(node);
      }
      else if (type == DIRECTIVE_ARG) {
        return new NASMDirectiveArgImpl(node);
      }
      else if (type == INCLUDE) {
        return new NASMIncludeImpl(node);
      }
      else if (type == INSTRUCTION) {
        return new NASMInstructionImpl(node);
      }
      else if (type == MACRO) {
        return new NASMMacroImpl(node);
      }
      else if (type == PREPROCESSOR) {
        return new NASMPreprocessorImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
