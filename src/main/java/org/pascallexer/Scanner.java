package org.pascallexer;

import java.util.ArrayList;
import java.util.List;

import static org.pascallexer.TokenType.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
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
            default:
                // error
        }
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
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
}
