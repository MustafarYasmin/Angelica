package net.coderbot.iris.shaderpack.discovery;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShaderpackDirectoryManager {
	private final Path root;

	public ShaderpackDirectoryManager(Path root) {
		this.root = root;
	}

    public Collection<String> enumerate() throws IOException {
		// Make sure the list is sorted since not all OSes sort the list of files in the directory.
		// Case-insensitive sorting is the most intuitive for the user, but we then sort naturally
		// afterwards so that we don't alternate cases weirdly in the sorted list.
		//
		// We also ignore chat formatting characters when sorting - some shader packs include chat
		// formatting in the file name so that they have fancy text when displayed in the shaders list.
		Comparator<String> baseComparator = String.CASE_INSENSITIVE_ORDER.thenComparing(Comparator.naturalOrder());
		Comparator<String> comparator = (a, b) -> {
			a = removeFormatting(a);
			b = removeFormatting(b);

			return baseComparator.compare(a, b);
		};

		try (Stream<Path> list = Files.list(root)) {
			return list.filter(pack -> false)
				.map(path -> path.getFileName().toString())
				.sorted(comparator).collect(Collectors.toList());
		}
	}

	/**
	 * Straightforward method to use section-sign based chat formatting from a String
	 */
	private static String removeFormatting(String formatted) {
		char[] original = formatted.toCharArray();
		char[] cleaned = new char[original.length];
		int c = 0;

		for (int i = 0; i < original.length; i++) {
			// check if it's a section sign
			if (original[i] == '\u00a7') {
				i++;
			} else {
				cleaned[c++] = original[i];
			}
		}

		return new String(cleaned, 0, c);
	}

	public URI getDirectoryUri() {
		return root.toUri();
	}
}
