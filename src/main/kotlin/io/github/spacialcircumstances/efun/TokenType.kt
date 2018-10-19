package io.github.spacialcircumstances.efun

enum class TokenType {
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    COMMA,
    DOT,
    SLASH,
    STAR,
    PLUS,
    MINUS,
    COLON,
    SEMICOLON,
    BANG,
    BANG_EQUAL,
    EQUAL,
    EQUAL_EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    ARROW,
    IDENTIFIER,
    STRING,
    INTEGER,
    FLOAT,
    LET, //let
    IF, //if
    ELSE, //else
    FOREACH, //for
    IN, //in
    TRUE, //true
    FALSE, //false
    DEBUG, //debug
    ASSERT,
    AND,
    OR,
    XOR,
    EOF,
    TYPE,
    REC,
    MODULE, //module
    USES, //uses
    OBJECT //object
}