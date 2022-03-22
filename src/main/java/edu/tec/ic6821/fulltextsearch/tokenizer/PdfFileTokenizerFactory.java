package edu.tec.ic6821.fulltextsearch.tokenizer;

import java.io.File;
import java.util.Optional;

public class PdfFileTokenizerFactory implements FileTokenizerFactory {

    @Override
    public final Optional<FileTokenizer> fileTokenizer(File file) {
        return Optional.of(new PdfFileTokenizer(file));
    }
}
