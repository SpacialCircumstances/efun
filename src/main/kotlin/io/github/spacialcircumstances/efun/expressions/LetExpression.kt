package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.Token

class LetExpression(private val expression: AbstractExpression, private val name: Token): AbstractExpression()