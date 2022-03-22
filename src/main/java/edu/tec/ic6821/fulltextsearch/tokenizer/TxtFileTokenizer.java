package edu.tec.ic6821.fulltextsearch.tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public final class TxtFileTokenizer implements FileTokenizer, Iterator<String> {

    private static final String DELIMITER_WHITESPACE = "\\r?\\n| ";

    private final Scanner scanner;

    public TxtFileTokenizer(final File file) {
        try {
            this.scanner = new Scanner(file).useDelimiter(DELIMITER_WHITESPACE);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(
                String.format("[%s: %s] Couldn't open file for scanning %s",
                    e.getClass().getName(),
                    e.getMessage(),
                    file.getAbsolutePath()));
        }
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public String next() {
        return scanner.next();
    }
}
