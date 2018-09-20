package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class CgcCli {

	private CliCommandHandler handler;

	public void execute(String[] args) {

		final String commandCode = args[0];

		if ("cmd".equals(commandCode)) {
			final List<String> params = Stream.of(args)
					.skip(1)
					.collect(Collectors.toList());

			handler.handleCommand(params);
		} else {
			throw new IllegalArgumentException(String.format("%s is not a valid command code", commandCode));
		}
	}

	public void registerHandler(String cmd, CliCommandHandler handler) {
		this.handler = handler;
	}
}
