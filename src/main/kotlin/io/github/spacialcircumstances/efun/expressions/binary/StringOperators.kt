package io.github.spacialcircumstances.efun.expressions.binary

import io.github.spacialcircumstances.efun.interpreter.TString

class StringConcatOperator: SimpleBinaryOperator<String, String, String>(TString, TString, TString) {
    override fun computeBinary(left: String, right: String): String =left + right
}