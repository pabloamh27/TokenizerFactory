package edu.tec.ic6821.fulltextsearch.tokenizer;

import java.io.File;
import java.util.Optional;

public class TxtFileTokenizerFactory implements FileTokenizerFactory {

    @Override
    public final Optional<FileTokenizer> fileTokenizer(File file) {
        return Optional.of(new TxtFileTokenizer(file));
    }
}
