package io.github.oliviercailloux.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.jupiter.api.Test;

public class ExtractorTests {
	
	//public void writeText(InputStream input, Writer output) throws IOException;
	public StringWriter strWrite = new StringWriter();
	
	/**
	 * a test methode de test the pdf methode output
	 * @throws IOException
	 */
	
	public SimpleExtractor extract;
	
	@Test
	public void  testNormalPdf() throws IOException {
		extract = new SimpleExtractor();
		assertEquals("hello world\n" , extract.)
	}

	
}
