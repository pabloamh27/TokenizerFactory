package edu.tec.ic6821.fulltextsearch.tokenizer;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public final class DefaultFileTokenizerFactory implements FileTokenizerFactory {

    private final Map<String, FileTokenizerFactory> tokenizer;

    public DefaultFileTokenizerFactory(final Map<String, FileTokenizerFactory> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Optional<FileTokenizer> fileTokenizer(File file) {
        final String extension = FilenameUtils.getExtension(file.getName());
        final FileTokenizerFactory factory = tokenizer.getOrDefault(extension, defaultValue -> Optional.empty());
        return factory.fileTokenizer(file);
    }
}
