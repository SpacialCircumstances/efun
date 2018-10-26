# efun
A simple interpreter for a functional programming language.
This is a learning project. The interpreter is incomplete, inflexible and bug-ridden.

Efun supports functions as first-class values, currying, closures and data structures (called objects). 
Efun does not have a module system, but this can be replaced by the usage of objects and signatures. 
Signatures are similar to interfaces, but are automatically implemented, because efun uses structural equality for all datatypes (See `examples/objects/equality.ef`).
Calling Java/Kotlin functions is supported, but extremely impractical at the moment.

The purpose of this project is (1) learning how to write interpreters and compilers and (2) getting more familiar with some concepts of functional programming by implementing them.

At the moment, support for mutable datastructures and arrays is planned.

## Examples

### Hello World

```
debug "Hello World!"
```

### Closures

```
let add = { a: Int ->
  { b: Int ->
    a + b
  }
}

let x = add(5)
debug x(2)
``` 

### Currying

```
let multiplication = { a: Int -> b: Int ->
  a * b
}

let xf = multiplication(2)
debug xf(2)
```

### Objects

```
type Object1 = object {
    let succ = { x: Int ->
        x + 1
    }
}

type Object2 = object(obj1: Object1, val num: Int) {
    let numSucc = obj1.succ(num)
}

type DataObject = object(val t1: String, val t2: Int)

let obj1 = newObject1()
let obj = newObject2(obj1, 3)
let do = newDataObject("Test", 0)
debug obj.numSucc
debug obj.num
debug do
```

## Reference

### Debug printing

The `debug` statement prints the specified value.
Example: `debug "Hello World!"` will print `Hello World!`.

### Assert statements

The `assert` statement will cause a runtime error if the argument does not evaluate to true.

## Building

### Windows

`gradlew.bat shadowJar`

### Unix

`./gradlew shadowJar`

## Running

Command `repl` runs the REPL.

Command `run` runs the specified script file.
Use the flag `-p` to show some performance numbers.
