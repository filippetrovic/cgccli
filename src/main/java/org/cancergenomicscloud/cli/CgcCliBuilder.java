package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;

/**
 * Created by Filip.
 */

public class CgcCliBuilder {

	private CgcCli underConstruction;

	public CgcCliBuilder(CliArgumentsParser cliArgumentsParser) {
		underConstruction = new CgcCli(cliArgumentsParser);
	}

	public CgcCliBuilder withHandler(String commandCode, CliCommandHandler handler) {
		underConstruction.registerHandler(commandCode, handler);
		return this;
	}

	public CgcCli get() {
		return underConstruction;
	}
}
