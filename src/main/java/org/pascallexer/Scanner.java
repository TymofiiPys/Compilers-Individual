package org.pascallexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.pascallexer.TokenType.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static final Map<String, TokenType> keywords;

    private final Map<Object, Object> table;

    static {
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("array", ARRAY);
        keywords.put("begin", BEGIN);
        keywords.put("case", CASE);
        keywords.put("const", CONST);
        keywords.put("div", DIV);
        keywords.put("do", DO);
        keywords.put("downto", DOWNTO);
        keywords.put("else", ELSE);
        keywords.put("end", END);
        keywords.put("file", FILE);
        keywords.put("for", FOR);
        keywords.put("function", FUNCTION);
        keywords.put("goto", GOTO);
        keywords.put("if", IF);
        keywords.put("in", IN);
        keywords.put("label", LABEL);
        keywords.put("mod", MOD);
        keywords.put("nil", NIL);
        keywords.put("not", NOT);
        keywords.put("of", OF);
        keywords.put("or", OR);
        keywords.put("packed", PACKED);
        keywords.put("procedure", PROCEDURE);
        keywords.put("program", PROGRAM);
        keywords.put("record", RECORD);
        keywords.put("repeat", REPEAT);
        keywords.put("set", SET);
        keywords.put("then", THEN);
        keywords.put("to", TO);
        keywords.put("type", TYPE);
        keywords.put("until", UNTIL);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
        keywords.put("with", WITH);
    }

    Scanner(String source) {
        this.source = source;
        this.table = new HashMap<>();
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '+':
                addToken(PLUS);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '*':
                addToken(ASTERISK);
                break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                }
                break;
            case '=':
                addToken(EQUAL);
                break;
            case '<':
                if (match('=')) {
                    addToken(LESSEQ);
                } else if (match('>')) {
                    addToken(NOT_EQUAL);
                } else {
                    addToken(LESS);
                }
                break;
            case '>':
                addToken(match('=') ? GREATEQ : GREATER);
                break;
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '[':
                addToken(LEFT_BRACKET);
                break;
            case ']':
                addToken(RIGHT_BRACKET);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(match('.') ? DOUBLEPERIOD : PERIOD);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case ':':
                addToken(match('=') ? BECOMES : SEMICOLON);
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                line++;
                break;

            // Literal handling:
            case '"':
                handleString();
                break;
            default:
                if (Character.isDigit(c)) {
                    handleNumber();
                } else if (Character.isLetter(c)) {
                    handleIdentifier();
                } else {
                    ErrorHandler.error(line, "Unknown character");
                }
        }
    }

    private void handleIdentifier() {
        while (Character.isLetterOrDigit(peek())) advance();

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);

        addToken(IDENTIFIER);
    }

    private void handleNumber() {
        while (Character.isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && Character.isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (Character.isDigit(peek())) advance();
        }

        addToken(REAL,
                Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void handleString() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            ErrorHandler.error(line, "Unterminated string");
            return;
        }

        // Advance after the closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
        table.putIfAbsent(literal, literal);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (peek() != expected) return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
}
