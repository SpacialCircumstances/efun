package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.Token

class BinaryExpression(private val left: AbstractExpression, private val operator: Token, private val right: AbstractExpression) : AbstractExpression() {

}