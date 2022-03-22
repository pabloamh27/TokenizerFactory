package edu.tec.ic6821.fulltextsearch.tokenizer;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class DefaultFileTokenizerFactoryTest {

    @Test
    public void testFileTokenizerWithTxtFile() throws URISyntaxException {
        // given
        final File file = new File(getClass().getResource("/LoremIpsum.txt").toURI());
        final Map<String, FileTokenizerFactory> factories = new HashMap<>();
        factories.put("txt", new TxtFileTokenizerFactory());
        factories.put("pdf", new PdfFileTokenizerFactory());
        final FileTokenizerFactory factory = new DefaultFileTokenizerFactory(factories);

        // when
        final Optional<FileTokenizer> actual = factory.fileTokenizer(file);

        // then
        assertTrue(actual.isPresent());
        final FileTokenizer tokenizer = actual.get();
        assertTrue(tokenizer instanceof TxtFileTokenizer);
    }

    @Test
    public void testFileTokenizerWithPdfFile() throws URISyntaxException {
        // given
        final File file = new File(getClass().getResource("/LoremIpsum.pdf").toURI());
        final Map<String, FileTokenizerFactory> factories = new HashMap<>();
        factories.put("txt", new TxtFileTokenizerFactory());
        factories.put("pdf", new PdfFileTokenizerFactory());
        final FileTokenizerFactory factory = new DefaultFileTokenizerFactory(factories);

        // when
        final Optional<FileTokenizer> actual = factory.fileTokenizer(file);

        // then
        assertTrue(actual.isPresent());
        final FileTokenizer tokenizer = actual.get();
        assertTrue(tokenizer instanceof PdfFileTokenizer);
    }

    @Test
    public void testFileTokenizerNotFound() {
        // given
        final File file = new File("somefile.someext");
        final Map<String, FileTokenizerFactory> factories = new HashMap<>();
        factories.put("txt", new TxtFileTokenizerFactory());
        factories.put("pdf", new PdfFileTokenizerFactory());
        final FileTokenizerFactory factory = new DefaultFileTokenizerFactory(factories);

        // when
        final Optional<FileTokenizer> actual = factory.fileTokenizer(file);

        // then
        assertTrue(actual.isEmpty());
    }


}
