package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.output.StringOutput;
import org.json.JSONObject;

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

	private final ResponseFormatter responseFormatter;
	private final StringOutput stringOutput;
	private final Function<CgcRequest, CgcResponse> sendHttpRequestFunction;


	public CommandHandlerImpl(String path,
							  Set<String> pathVariables,
							  Function<CgcRequest, CgcResponse> sendHttpRequestFunction,
							  ResponseFormatter responseFormatter,
							  StringOutput stringOutput) {
		this.path = path;
		this.pathVariables = pathVariables;
		this.responseFormatter = responseFormatter;
		this.stringOutput = stringOutput;
		this.sendHttpRequestFunction = sendHttpRequestFunction;
	}

	@Override
	public void handleCommand(Command command) {

		final CgcRequestBuilder cgcRequestBuilder = new CgcRequestBuilder(path)
				.setHeaders(Collections.singletonMap(X_SBG_AUTH_TOKEN, command.getAuthToken()))
				.setQueryParams(getQueryParams(command))
				.setPathVariables(getPathVariables(command));

		if (bodyExists(command)) {

			cgcRequestBuilder.setBody(getBody(command));

			Map<String, String> headers = new HashMap<>();
			headers.put(X_SBG_AUTH_TOKEN, command.getAuthToken());
			headers.put("Content-Type", "application/json");
			cgcRequestBuilder.setHeaders(headers);

		}

		final CgcRequest cgcRequest = cgcRequestBuilder.createCgcRequest();

		final CgcResponse response = sendHttpRequestFunction.apply(cgcRequest);

		stringOutput.print(responseFormatter.format(response));

	}

	private boolean bodyExists(Command command) {
		return !command.getCommandKeyValues().isEmpty();
	}

	private String getBody(Command command) {
		return new JSONObject(command.getCommandKeyValues()).toString();
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
