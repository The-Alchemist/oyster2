package util;

import java.util.StringTokenizer;
import java.util.Vector;

public class Tokenizer {

	/**
	 * Create a new <code>Tokenizer</code>.
	 */
	protected Tokenizer() {
		// do nothing
	}

	/**
	 * Splits the given string by whitespaces in an array of strings.
	 *
	 * @param input the string to split.
	 * @return the delivered string splitted in an array of strings.
	 */
	public static String[] tokenize(String input) {
		return tokenize(input, " ");
	}

	/**
	 * Splits the given string by a given separator in an array of strings.
	 *
	 * @param input     the string to split.
	 * @param separator the splitting string.
	 * @return the delivered string splitted in an array of strings.
	 */
	public static String[] tokenize(String input, String separator) {
		Vector vector = new Vector();
		StringTokenizer strTokens = new StringTokenizer(input, separator);
		String[] strings;

		while (strTokens.hasMoreTokens())
			vector.addElement(strTokens.nextToken());
		strings = new String[vector.size()];
		for (int i = 0; i < strings.length; i++)
			strings[i] = (String)vector.get(i);
		return strings;
	}

}