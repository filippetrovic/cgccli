package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Filip.
 */

public class CgcCli {

	private CliArgumentsParser cliArgumentsParser;

	private HashMap<String, CliCommandHandler> handlers;

	CgcCli(CliArgumentsParser cliArgumentsParser) {
		this.cliArgumentsParser = cliArgumentsParser;
		this.handlers = new HashMap<>();
	}

	public void execute(String[] args) {

		final String commandCode = args[0];

		if (!handlers.containsKey(commandCode)) {
			throw new IllegalArgumentException(String.format("\"%s\" is not a valid command code", commandCode));
		}

		final List<String> params = cliArgumentsParser.parseCommand(args);

		handlers.get(commandCode).handleCommand(params);
	}

	public void registerHandler(String commandCode, CliCommandHandler handler) {
		if (handlers.containsKey(commandCode)) {
			throw new IllegalArgumentException("Command handler for \"%s\" has been already registered");
		}
		handlers.put(commandCode, handler);
	}
}
