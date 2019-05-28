package io.github.oliviercailloux.extractor;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class ExtractorUser {

	public static void writeTextToFile(InputStream input, Path outputFile)
			throws InvalidPasswordException, IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
			new MySimpleExtractor().writeText(input, writer);
		}
	}

	public static String getTextFromEncrypted(InputStream input, String password) throws InvalidPasswordException, IOException {
		final StringWriter output = new StringWriter();
		try (PDDocument document = PDDocument.load(input, password)) {
			assert document != null;
			new MySimpleExtractor().writeTextFromDocument(document, output);
		}
		return output.toString();
	}

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		checkArgument(args.length >= 1 && args.length <= 2);
		final String path = args[0];
		final String password;
		if (args.length == 2) {
			password = args[1];
		} else {
			password = "";
		}
		try (InputStream input = Files.newInputStream(Paths.get(path))) {
			final String text = getTextFromEncrypted(input, password);
			System.out.println(text);
		}
	}

}
