package io.github.spacialcircumstances.efun.interpreter

abstract class ExternalFunction {
    abstract val functionType: FunctionType
    abstract val argumentCount: Int
    abstract fun run(args: List<FValue>): FValue
}

class ExternalFunction1<P1, R>(val external: (P1) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 1

    override fun run(args: List<FValue>): FValue {
        val nativeArg = args.first().value as P1
        val result = external(nativeArg)
        return FValue(functionType.outType, result)
    }
}