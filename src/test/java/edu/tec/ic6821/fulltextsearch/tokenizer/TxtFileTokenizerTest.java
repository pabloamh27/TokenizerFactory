package edu.tec.ic6821.fulltextsearch.tokenizer;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TxtFileTokenizerTest {

    @Test
    public void testConstructor() throws URISyntaxException {
        // given
        final File txtFile = new File(getClass().getResource("/LoremIpsum.txt").toURI());

        // when
        final TxtFileTokenizer tokenizer = new TxtFileTokenizer(txtFile);

        // then
        assertNotNull(tokenizer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNonExistingFile() {
        // given
        final File nonExistingTxtFile = new File(UUID.randomUUID().toString());

        // when
        new TxtFileTokenizer(nonExistingTxtFile);
    }

    @Test
    public void hasNext() throws URISyntaxException {
        // given
        final File txtFile = new File(getClass().getResource("/LoremIpsum.txt").toURI());
        final TxtFileTokenizer tokenizer = new TxtFileTokenizer(txtFile);

        // when
        final boolean result1 = tokenizer.hasNext();
        tokenizer.next();
        final boolean result2 = tokenizer.hasNext();
        tokenizer.next();
        final boolean result3 = tokenizer.hasNext();
        tokenizer.next();
        final boolean result4 = tokenizer.hasNext();

        // then
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
    }

    @Test
    public void next() throws URISyntaxException {
        // given
        final File txtFile = new File(getClass().getResource("/LoremIpsum.txt").toURI());
        final TxtFileTokenizer tokenizer = new TxtFileTokenizer(txtFile);

        // when
        final String result = tokenizer.next();

        // then
        assertNotNull(result);
        assertFalse(result.isBlank());
    }
}
