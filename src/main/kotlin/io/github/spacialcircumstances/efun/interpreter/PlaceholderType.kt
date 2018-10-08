package io.github.spacialcircumstances.efun.interpreter

abstract class PlaceholderType {
    abstract fun resolveType(context: TypesContext): FType<*>
}

class SimplePlaceholderType(val name: String): PlaceholderType() {
    override fun resolveType(context: TypesContext): FType<*> {
        return context.resolveType(this)
    }
}

class FunctionPlaceholderType(val args: List<PlaceholderType>, val returnType: PlaceholderType): PlaceholderType() {
    override fun resolveType(context: TypesContext): FType<*> {
        return context.resolveType(this)
    }
}