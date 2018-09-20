package org.cancergenomicscloud.cli.parser;

import org.cancergenomicscloud.cli.handler.Command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class CliArgumentsParser {

	public Command parseCommand(String[] args) {
		return Command.of(
				extractCommandCode(args),
				extractCommandArguments(args)
		);
	}

	private String extractCommandCode(String[] args) {
		return args[0];
	}

	private List<String> extractCommandArguments(String[] args) {
		return Stream.of(args)
				.skip(1)
				.collect(Collectors.toList());
	}

}
