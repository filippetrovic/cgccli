package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Filip.
 */

public class CommandHandlerImpl implements CliCommandHandler {

	private static final String X_SBG_AUTH_TOKEN = "X-SBG-Auth-Token";

	private final String path;
	private final Set<String> pathVariables;

	private final HttpClient httpClient;
	private final ResponseFormatter responseFormatter;
	private final StringOutput stringOutput;


	public CommandHandlerImpl(String path,
							  Set<String> pathVariables,
							  HttpClient httpClient,
							  ResponseFormatter responseFormatter,
							  StringOutput stringOutput) {
		this.path = path;
		this.pathVariables = pathVariables;
		this.httpClient = httpClient;
		this.responseFormatter = responseFormatter;
		this.stringOutput = stringOutput;
	}

	@Override
	public void handleCommand(Command command) {

		final CgcRequest cgcRequest = CgcRequest.of(
				path,
				Collections.singletonMap(X_SBG_AUTH_TOKEN, command.getAuthToken()),
				getQueryParams(command),
				getPathVariables(command),
				null);

		final CgcResponse response = httpClient.get(cgcRequest);

		stringOutput.print(responseFormatter.format(response));

	}

	private Map<String, String> getQueryParams(Command command) {
		final HashMap<String, String> queryParams = new HashMap<>(command.getCommandArguments());

		queryParams.keySet().removeAll(pathVariables);

		return queryParams;
	}

	private Map<String, String> getPathVariables(Command command) {
		return pathVariables.stream()
				.filter(command.getCommandArguments()::containsKey)
				.collect(Collectors.toMap(Function.identity(), command.getCommandArguments()::get));
	}

}
