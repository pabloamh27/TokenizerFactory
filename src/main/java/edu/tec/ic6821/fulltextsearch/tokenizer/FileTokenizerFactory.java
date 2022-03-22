package edu.tec.ic6821.fulltextsearch.tokenizer;

import java.io.File;
import java.util.Optional;

public interface FileTokenizerFactory {
    Optional<FileTokenizer> fileTokenizer(File file);
}
