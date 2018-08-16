@file:Suppress("UNCHECKED_CAST")

package io.github.spacialcircumstances.efun.interpreter

abstract class ExternalFunction {
    abstract val functionType: FunctionType
    abstract val argumentCount: Int
    abstract fun run(args: List<FValue>): FValue
}

class ExternalFunction0<R>(val external: () -> R, val outType: FType<*>): ExternalFunction() {
    override val argumentCount: Int = 0
    override val functionType: FunctionType = FunctionType(TVoid, outType)
    override fun run(args: List<FValue>): FValue {
        val result = external()
        return FValue(outType, result)
    }
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

class ExternalFunction3<P1, P2, P3, R>(val external: (P1, P2, P3) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 3

    override fun run(args: List<FValue>): FValue {
        val nativeArg1 = args.first().value as P1
        val nativeArg2 = args[1].value as P2
        val nativeArg3 = args[2].value as P3
        val result = external(nativeArg1, nativeArg2, nativeArg3)
        val out = (functionType.outType as FunctionType).outType
        return FValue(out, result)
    }
}

class ExternalFunction4<P1, P2, P3, P4, R>(val external: (P1, P2, P3, P4) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 4

    override fun run(args: List<FValue>): FValue {
        val nativeArg1 = args.first().value as P1
        val nativeArg2 = args[1].value as P2
        val nativeArg3 = args[2].value as P3
        val nativeArg4 = args[3].value as P4
        val result = external(nativeArg1, nativeArg2, nativeArg3, nativeArg4)
        val out = (functionType.outType as FunctionType).outType
        return FValue(out, result)
    }
}

class ExternalFunction5<P1, P2, P3, P4, P5, R>(val external: (P1, P2, P3, P4, P5) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 5

    override fun run(args: List<FValue>): FValue {
        val nativeArg1 = args.first().value as P1
        val nativeArg2 = args[1].value as P2
        val nativeArg3 = args[2].value as P3
        val nativeArg4 = args[3].value as P4
        val nativeArg5 = args[5].value as P5
        val result = external(nativeArg1, nativeArg2, nativeArg3, nativeArg4, nativeArg5)
        val out = (functionType.outType as FunctionType).outType
        return FValue(out, result)
    }
}

class ExternalFunction6<P1, P2, P3, P4, P5, P6, R>(val external: (P1, P2, P3, P4, P5, P6) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 6

    override fun run(args: List<FValue>): FValue {
        val nativeArg1 = args.first().value as P1
        val nativeArg2 = args[1].value as P2
        val nativeArg3 = args[2].value as P3
        val nativeArg4 = args[3].value as P4
        val nativeArg5 = args[5].value as P5
        val nativeArg6 = args[6].value as P6
        val result = external(nativeArg1, nativeArg2, nativeArg3, nativeArg4, nativeArg5, nativeArg6)
        val out = (functionType.outType as FunctionType).outType
        return FValue(out, result)
    }
}

class ExternalFunction7<P1, P2, P3, P4, P5, P6, P7, R>(val external: (P1, P2, P3, P4, P5, P6, P7) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 7

    override fun run(args: List<FValue>): FValue {
        val nativeArg1 = args.first().value as P1
        val nativeArg2 = args[1].value as P2
        val nativeArg3 = args[2].value as P3
        val nativeArg4 = args[3].value as P4
        val nativeArg5 = args[5].value as P5
        val nativeArg6 = args[6].value as P6
        val nativeArg7 = args[7].value as P7
        val result = external(nativeArg1, nativeArg2, nativeArg3, nativeArg4, nativeArg5, nativeArg6, nativeArg7)
        val out = (functionType.outType as FunctionType).outType
        return FValue(out, result)
    }
}

class ExternalFunction8<P1, P2, P3, P4, P5, P6, P7, P8, R>(val external: (P1, P2, P3, P4, P5, P6, P7, P8) -> R, override val functionType: FunctionType): ExternalFunction() {
    override val argumentCount: Int = 8

    override fun run(args: List<FValue>): FValue {
        val nativeArg1 = args.first().value as P1
        val nativeArg2 = args[1].value as P2
        val nativeArg3 = args[2].value as P3
        val nativeArg4 = args[3].value as P4
        val nativeArg5 = args[5].value as P5
        val nativeArg6 = args[6].value as P6
        val nativeArg7 = args[7].value as P7
        val nativeArg8 = args[8].value as P8
        val result = external(nativeArg1, nativeArg2, nativeArg3, nativeArg4, nativeArg5, nativeArg6, nativeArg7, nativeArg8)
        val out = (functionType.outType as FunctionType).outType
        return FValue(out, result)
    }
}