package org.cancergenomicscloud.cli.handler;

import java.util.List; /**
 * Created by Filip.
 */

public interface CliCommandHandler {
	void handleCommand(List<String> strings);
}
