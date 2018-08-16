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

class ExternalFunction2<P1, P2, R>(val external: (P1, P2) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 2

    override fun run(args: List<FValue>): FValue {
        val nativeArg1 = args.first().value as P1
        val nativeArg2 = args[1].value as P2
        val result = external(nativeArg1, nativeArg2)
        val out = (functionType.outType as FunctionType).outType
        return FValue(out, result)
    }
}