package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.cancergenomicscloud.cli.handler.Command;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;

import java.util.HashMap;

/**
 * Created by Filip.
 */

public class CgcCli {

	private final CliArgumentsParser cliArgumentsParser;

	private final HashMap<String, CliCommandHandler> handlers;

	CgcCli(CliArgumentsParser cliArgumentsParser, HashMap<String, CliCommandHandler> handlers) {
		this.cliArgumentsParser = cliArgumentsParser;
		this.handlers = handlers;
	}

	public void execute(String[] cliInputArgs) {

		final Command command = cliArgumentsParser.parseCommand(cliInputArgs);

		if (!handlers.containsKey(command.getCommandCode())) {
			throw new IllegalArgumentException(String.format("\"%s\" is not a valid command code", command.getCommandCode()));
		}

		handlers.get(command.getCommandCode())
				.handleCommand(command);
	}

}
