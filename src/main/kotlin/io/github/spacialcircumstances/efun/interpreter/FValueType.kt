package io.github.spacialcircumstances.efun.interpreter

enum class FValueType {
    Int,
    Float,
    String,
    Function,
    Void,
    Bool
}

fun checkNumeric(type: FValueType): Boolean {
    return when(type) {
        FValueType.Int -> true
        FValueType.Float -> true
        FValueType.String -> false
        FValueType.Function -> false
        FValueType.Void -> false
        FValueType.Bool -> false
    }
}