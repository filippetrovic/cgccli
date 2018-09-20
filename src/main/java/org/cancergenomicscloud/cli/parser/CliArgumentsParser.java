package org.cancergenomicscloud.cli.parser;

import org.cancergenomicscloud.cli.handler.Command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class CliArgumentsParser {

	private static final int TOKEN_PART_LEN = 2;
	private static final int COMMAND_CODE_LEN = 2;
	public static final String COMMAND_CODE_PARTS_DELIMITER = " ";
	public static final int TOKEN_INDEX = 1;

	public Command parseCommand(String[] cliInputArgs) {
		return Command.of(
				extractCommandCode(cliInputArgs),
				extractToken(cliInputArgs),
				extractCommandArguments(cliInputArgs));
	}

	private String extractToken(String[] cliInputArgs) {
		return cliInputArgs[TOKEN_INDEX];
	}

	private String extractCommandCode(String[] cliInputArgs) {
		return Stream.of(cliInputArgs)
				.skip(TOKEN_PART_LEN)
				.limit(COMMAND_CODE_LEN)
				.collect(Collectors.joining(COMMAND_CODE_PARTS_DELIMITER));
	}

	private List<String> extractCommandArguments(String[] cliInputArgs) {
		return Stream.of(cliInputArgs)
				.skip(COMMAND_CODE_LEN + TOKEN_PART_LEN)
				.collect(Collectors.toList());
	}


}
