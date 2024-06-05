package org.pascallexer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PascalLexer {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            runFile(args[0]);
        } else {
            System.out.println("Supply a pascal code filename");
            System.exit(1);
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    private static void run(String source) {
        if (ErrorHandler.hadError) System.exit(2);
    }
}
