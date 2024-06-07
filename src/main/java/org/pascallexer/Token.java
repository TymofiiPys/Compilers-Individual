package org.pascallexer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Token {
    private final TokenType type;
    private final Object value;
    private final int line;

    public String toString() {
        StringBuilder ret = new StringBuilder("(" + type);
        if(value != null) {
            ret.append(", ");
            if (value instanceof String)
                ret.append("\"");
            ret.append(value);
            if (value instanceof String)
                ret.append("\"");
        }
        ret.append(")");
        return ret.toString();
    }
}
