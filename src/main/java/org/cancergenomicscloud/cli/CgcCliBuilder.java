package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;

import java.util.HashMap;

/**
 * Created by Filip.
 */

public class CgcCliBuilder {

	private final CliArgumentsParser cliArgumentsParser;
	private final HashMap<String, CliCommandHandler> handlers;

	public CgcCliBuilder(CliArgumentsParser cliArgumentsParser) {
		this.cliArgumentsParser = cliArgumentsParser;
		handlers = new HashMap<>();
	}

	public CgcCliBuilder withHandler(String commandCode, CliCommandHandler handler) {
		if (handlers.containsKey(commandCode)) {
			throw new IllegalArgumentException("Command handler for \"%s\" has been already registered");
		}
		handlers.put(commandCode, handler);

		return this;
	}

	public CgcCli create() {
		return new CgcCli(cliArgumentsParser, handlers);
	}
}
