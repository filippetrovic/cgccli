package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.cancergenomicscloud.cli.handler.Command;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;

import java.util.HashMap;

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

	public void execute(String[] cliInputArgs) {

		final Command command = cliArgumentsParser.parseCommand(cliInputArgs);

		if (!handlers.containsKey(command.getCommandCode())) {
			throw new IllegalArgumentException(String.format("\"%s\" is not a valid command code", command.getCommandCode()));
		}

		handlers.get(command.getCommandCode())
				.handleCommand(command);
	}

	public void registerHandler(String commandCode, CliCommandHandler handler) {
		if (handlers.containsKey(commandCode)) {
			throw new IllegalArgumentException("Command handler for \"%s\" has been already registered");
		}
		handlers.put(commandCode, handler);
	}
}
