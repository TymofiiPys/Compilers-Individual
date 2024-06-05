package org.pascallexer;

public enum TokenType {
    // Operators
    PLUS, MINUS, ASTERISK, SLASH,
    EQUAL, LESS, GREATER,
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACKET, RIGHT_BRACKET,
    COMMA, PERIOD, SEMICOLON, COLON,
    NOT_EQUAL, LESSEQ, GREATEQ, BECOMES, DOUBLEPERIOD,

    // Literals
    IDENTIFIER, STRING, CHAR, INTEGER, REAL,

    // Keywords
    AND, ARRAY, BEGIN, CASE, CONST, DIV, DO,
    DOWNTO, ELSE, END, FILE, FOR, FUNCTION, GOTO,
    IF, IN, LABEL, MOD, NIL, NOT, OF, OR,
    PACKED, PROCEDURE, PROGRAM, RECORD,
    REPEAT, SET, THEN, TO, TYPE,
    UNTIL, VAR, WHILE, WITH,

    EOF
}
