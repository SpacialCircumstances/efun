package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.Token

class UnaryExpression(private val expression: AbstractExpression, private val operator: Token): AbstractExpression() {
}