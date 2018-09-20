package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class CgcCli {

	private HashMap<String, CliCommandHandler> handlers = new HashMap<>();

	public void execute(String[] args) {

		final String commandCode = args[0];

		if (!handlers.containsKey(commandCode)) {
			throw new IllegalArgumentException(String.format("\"%s\" is not a valid command code", commandCode));
		}

		final List<String> params = Stream.of(args)
				.skip(1)
				.collect(Collectors.toList());

		handlers.get(commandCode).handleCommand(params);
	}

	public void registerHandler(String commandCode, CliCommandHandler handler) {
		if (handlers.containsKey(commandCode)) {
			throw new IllegalArgumentException("Command handler for \"%s\" has been already registered");
		}
		handlers.put(commandCode, handler);
	}
}
