package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler; /**
 * Created by Filip.
 */

public class CgcCliBuilder {

	private CgcCli underConstruction = new CgcCli();

	public CgcCliBuilder withHandler(String commandCode, CliCommandHandler handler) {
		underConstruction.registerHandler(commandCode, handler);
		return this;
	}

	public CgcCli get() {
		return underConstruction;
	}
}
