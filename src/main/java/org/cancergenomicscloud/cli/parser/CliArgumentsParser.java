package org.cancergenomicscloud.cli.parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class CliArgumentsParser {

	public List<String> parseCommand(String[] args) {
		return Stream.of(args)
				.skip(1)
				.collect(Collectors.toList());
	}

}
