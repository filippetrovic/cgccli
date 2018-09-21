package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;

import java.util.Collections;

/**
 * Created by Filip.
 */

public class CommandHandlerImpl implements CliCommandHandler {

	private static final String X_SBG_AUTH_TOKEN = "X-SBG-Auth-Token";

	private final String path;
	private final HttpClient httpClient;
	private final ResponseFormatter responseFormatter;
	private final StringOutput stringOutput;

	public CommandHandlerImpl(String path,
							  HttpClient httpClient,
							  ResponseFormatter responseFormatter,
							  StringOutput stringOutput) {
		this.path = path;
		this.httpClient = httpClient;
		this.responseFormatter = responseFormatter;
		this.stringOutput = stringOutput;
	}

	@Override
	public void handleCommand(Command command) {

		final CgcRequest cgcRequest = CgcRequest.of(
				path,
				Collections.singletonMap(X_SBG_AUTH_TOKEN, command.getAuthToken()),
				command.getCommandArguments());

		final CgcResponse response = httpClient.get(cgcRequest);

		stringOutput.print(responseFormatter.format(response));

	}

}
