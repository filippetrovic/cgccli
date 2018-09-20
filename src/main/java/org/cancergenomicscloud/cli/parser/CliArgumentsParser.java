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

	public Command parseCommand(String[] cliInputArgs) {
		return Command.of(
				extractCommandCode(cliInputArgs),
				extractCommandArguments(cliInputArgs)
		);
	}

	private String extractCommandCode(String[] cliInputArgs) {
		return Stream.of(cliInputArgs)
				.limit(COMMAND_CODE_LEN)
				.collect(Collectors.joining(COMMAND_CODE_PARTS_DELIMITER));
	}

	private List<String> extractCommandArguments(String[] cliInputArgs) {
		return Stream.of(cliInputArgs)
				.skip(COMMAND_CODE_LEN)
				.collect(Collectors.toList());
	}


}
