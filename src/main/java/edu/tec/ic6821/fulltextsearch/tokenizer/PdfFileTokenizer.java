package edu.tec.ic6821.fulltextsearch.tokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public final class PdfFileTokenizer implements FileTokenizer {

    private static final String DELIMITER_WHITESPACE = "\\r?\\n| ";

    private final Scanner scanner;

    public PdfFileTokenizer(final File file) {
        try {
            final PDDocument document = PDDocument.load(file);
            if (!document.isEncrypted()) {
                final PDFTextStripperByArea stripperByArea = new PDFTextStripperByArea();
                stripperByArea.setSortByPosition(true);
                final PDFTextStripper stripper = new PDFTextStripper();

                final String text = stripper.getText(document);
                this.scanner = new Scanner(text).useDelimiter(DELIMITER_WHITESPACE);
            } else {
                this.scanner = new Scanner("");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(
                String.format("[%s: %s] Couldn't open file for scanning %s",
                    e.getClass().getName(),
                    e.getMessage(),
                    file.getAbsolutePath())
            );
        }
    }

    @Override
    public boolean hasNext() {
        return this.scanner.hasNext();
    }

    @Override
    public String next() {
        return this.scanner.next();
    }
}
