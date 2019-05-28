package io.github.oliviercailloux.extractor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.common.collect.ImmutableList;

import io.github.oliviercailloux.extractor.SimpleExtractor;

public class MySimpleExtractor implements SimpleExtractor {
	private PDFTextStripper pdfTextStripper;

	public MySimpleExtractor() {
		pdfTextStripper = null;
	}

	private void initStripper() throws IOException {
		if (pdfTextStripper == null) {
			pdfTextStripper = new PDFTextStripper();
		}
	}

	@Override
	public void writeTextFromDocument(PDDocument document, Writer output) throws IOException {
		if (document == null) {
			return;
		}
		checkNotNull(output);

		initStripper();
		pdfTextStripper.writeText(document, output);
	}

	@Override
	public void writeText(InputStream input, Writer output) throws IOException {
		if (input == null) {
			return;
		}
		checkNotNull(output);

		try (PDDocument doc = PDDocument.load(input)) {
			writeTextFromDocument(doc, output);
		}
	}

	private void writeText(Path inputPath, Writer output) throws IOException {
		try (InputStream inputStream = Files.newInputStream(inputPath)) {
			writeText(inputStream, output);
		}
	}

	@Override
	public void writeAllText(Collection<Path> inputPaths, Writer output) throws IOException {
		checkNotNull(inputPaths);
		for (Path inputPath : inputPaths) {
			writeText(inputPath, output);
		}
	}

	@Override
	public List<String> getAllText(Collection<Path> inputPaths) throws IOException {
		checkNotNull(inputPaths);
		final ImmutableList.Builder<String> builder = ImmutableList.builder();
		for (Path inputPath : inputPaths) {
			final StringWriter writer = new StringWriter();
			writeText(inputPath, writer);
			final String written = writer.toString();
			builder.add(written);
		}
		return builder.build();
	}

}
