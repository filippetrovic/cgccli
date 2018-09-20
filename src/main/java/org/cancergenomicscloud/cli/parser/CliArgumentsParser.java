package org.cancergenomicscloud.cli.parser;

import org.cancergenomicscloud.cli.handler.Command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class CliArgumentsParser {

	private static final int COMMAND_CODE_LEN = 2;
	public static final String COMMAND_CODE_PARTS_DELIMITER = " ";

	public Command parseCommand(String[] args) {
		return Command.of(
				extractCommandCode(args),
				extractCommandArguments(args)
		);
	}

	private String extractCommandCode(String[] args) {
		return Stream.of(args)
				.limit(COMMAND_CODE_LEN)
				.collect(Collectors.joining(COMMAND_CODE_PARTS_DELIMITER));
	}

	private List<String> extractCommandArguments(String[] args) {
		return Stream.of(args)
				.skip(COMMAND_CODE_LEN)
				.collect(Collectors.toList());
	}

}
