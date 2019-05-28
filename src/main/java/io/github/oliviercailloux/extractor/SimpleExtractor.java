package io.github.oliviercailloux.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

/**
 * <p>
 * Extracts text from PDF.
 * </p>
 * <p>
 * Following the usual practice, this object does not close the resources it is
 * given, but closes the resources it creates.
 * </p>
 *
 * @author Olivier Cailloux
 *
 */
public interface SimpleExtractor {
	/**
	 * <p>
	 * Extracts text found in the given document, and writes it to the given
	 * <code>Writer</code>.
	 * </p>
	 *
	 * @param document the document to extract text from, if <code>null</code>,
	 *                 nothing is written
	 * @param output   the writer where content should be written, not
	 *                 <code>null</code> if <code>input</code> is not null,
	 *                 otherwise, may be <code>null</code>
	 * @throws IOException in case of a reading, parsing or writing error
	 */
	public void writeTextFromDocument(PDDocument document, Writer output) throws IOException;

	/**
	 * <p>
	 * Extracts text found in the given input stream representing a non-encrypted
	 * PDF, and writes it to the given <code>Writer</code>.
	 * </p>
	 * <p>
	 * Uses the given stripper or a default one if none is provided.
	 * </p>
	 *
	 * @param input  the PDF byte stream, if <code>null</code>, nothing is written
	 * @param output the writer where content should be written, not
	 *               <code>null</code> if <code>input</code> is not null, otherwise,
	 *               may be <code>null</code>
	 * @throws InvalidPasswordException if the PDF required a non-empty password
	 * @throws IOException              in case of a reading, parsing or writing
	 *                                  error
	 */
	public void writeText(InputStream input, Writer output) throws IOException;

	/**
	 * <p>
	 * For each path given as input, extracts the text from the corresponding file,
	 * assuming it is a PDF, and writes it to the given <code>Writer</code>. The
	 * text is written to the output with no separation indicating the boundaries of
	 * the given PDFs.
	 * </p>
	 *
	 * @param inputPaths not <code>null</code>, may be empty
	 * @param output     the writer where content should be written, not
	 *                   <code>null</code> if <code>input</code> is not empty,
	 *                   otherwise, may be <code>null</code>
	 * @throws InvalidPasswordException if a PDF required a non-empty password
	 * @throws IOException              in case of a reading, parsing or writing
	 *                                  error
	 */
	public void writeAllText(Collection<Path> inputPaths, Writer output) throws IOException;

	/**
	 * <p>
	 * For each path given as input, extracts the text from the corresponding file,
	 * assuming it is a PDF.
	 * </p>
	 * <p>
	 * Uses the given stripper or a default one if none is provided.
	 * </p>
	 *
	 * @param inputPaths not <code>null</code>, may be empty
	 * @return a list containing as many entries as in the given collection, not
	 *         <code>null</code>, but may be empty
	 *
	 * @throws InvalidPasswordException if a PDF required a non-empty password
	 * @throws IOException              in case of a reading or parsing error
	 */
	public List<String> getAllText(Collection<Path> inputPaths) throws IOException;
}
