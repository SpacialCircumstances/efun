package io.github.spacialcircumstances.efun.interpreter

abstract class PlaceholderType {
    abstract fun resolveType(context: TypeContext): FType<*>
}

class SimplePlaceholderType(val name: String): PlaceholderType() {
    override fun resolveType(context: TypeContext): FType<*> {
        return context.resolveSimpleType(this)
    }
}

class FunctionPlaceholderType(val args: List<PlaceholderType>, val returnType: PlaceholderType): PlaceholderType() {
    override fun resolveType(context: TypeContext): FType<*> {
        return context.resolveFunctionType(this)
    }
}