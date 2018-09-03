# efun
A simple interpreter for a functional programming language.
This is a learning project. The interpreter is incomplete, inflexible and bug-ridden.

The programming language uses only immutable variables and supports first-class functions, records, enums and currying. Types are checked after parsing and before executing the program.
The purpose of this project is (1) learning how to write interpreters and compilers and (2) getting more familiar with some concepts of functional programming by implementing them.

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

### Records

```
type Vehicle = record {
    name: String,
    wheelCount: Int
}

let car = Vehicle {
    name: "Test Car",
    wheelCount: 4
}

assert (car.wheelCount == 4)
debug car.name
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
