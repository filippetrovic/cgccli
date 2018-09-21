package org.cancergenomicscloud.cli.parser;

import org.cancergenomicscloud.cli.handler.Command;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class CliArgumentsParser {

	private static final int TOKEN_PART_LEN = 2;
	private static final int COMMAND_CODE_LEN = 2;
	private static final String COMMAND_CODE_PARTS_DELIMITER = " ";
	private static final int TOKEN_INDEX = 1;

	public static final String QUERY_PREFIX = "--";
	public static final String KEY_VALUE_SEPARATOR = "=";

	private final CommandArgumentsParser commandArgumentsParser;
	private final CommandKeyValuesParser commandKeyValuesParser;

	public CliArgumentsParser(CommandArgumentsParser commandArgumentsParser,
							  CommandKeyValuesParser commandKeyValuesParser) {
		this.commandArgumentsParser = commandArgumentsParser;
		this.commandKeyValuesParser = commandKeyValuesParser;
	}

	public Command parseCommand(String[] cliInputArgs) {
		try {
			return Command.of(
					extractCommandCode(cliInputArgs),
					extractToken(cliInputArgs),
					extractCommandArguments(getArgumentsAfterCommandCode(cliInputArgs)),
					extractCommandKeyValues(getArgumentsAfterCommandCode(cliInputArgs)));
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to parse arguments.");
		}
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

	private List<String> getArgumentsAfterCommandCode(String[] cliInputArgs) {
		return Stream.of(cliInputArgs)
					.skip(COMMAND_CODE_LEN + TOKEN_PART_LEN)
					.collect(Collectors.toList());
	}

	private Map<String, String> extractCommandArguments(List<String> argumentsPart) {
		return commandArgumentsParser.parse(argumentsPart);
	}

	private Map<String, String> extractCommandKeyValues(List<String> argumentsPart) {
		return commandKeyValuesParser.parse(argumentsPart);
	}

}
