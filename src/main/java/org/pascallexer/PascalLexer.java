package org.pascallexer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PascalLexer {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            run(args[0]);
        } else {
            System.out.println("Supply a pascal code filename");
            System.exit(1);
        }
    }

    private static void run(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));

        Scanner scanner = new Scanner(new String(bytes, Charset.defaultCharset()));
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }

        if (ErrorHandler.hadError) System.exit(2);
    }
}
