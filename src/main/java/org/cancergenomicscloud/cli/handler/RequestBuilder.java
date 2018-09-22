package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.handler.parser.KeyValueToJsonParser;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Filip.
 */

public class RequestBuilder {

	private static final String X_SBG_AUTH_TOKEN = "X-SBG-Auth-Token";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String APPLICATION_JSON_TYPE = "application/json";

	private final String path;
	private final Set<String> pathVariables;
	private final KeyValueToJsonParser keyValueParser;

	public RequestBuilder(String path, Set<String> pathVariables, KeyValueToJsonParser keyValueParser) {
		this.path = path;
		this.pathVariables = pathVariables;
		this.keyValueParser = keyValueParser;
	}

	public CgcRequest buildRequest(Command command) {
		final CgcRequestBuilder cgcRequestBuilder = new CgcRequestBuilder(path)
				.setHeaders(Collections.singletonMap(X_SBG_AUTH_TOKEN, command.getAuthToken()))
				.setQueryParams(getQueryParams(command))
				.setPathVariables(getPathVariables(command));

		if (bodyExists(command)) {

			cgcRequestBuilder.setBody(keyValueParser.getBody(command.getCommandKeyValues()));

			Map<String, String> headers = new HashMap<>();
			headers.put(X_SBG_AUTH_TOKEN, command.getAuthToken());
			headers.put(CONTENT_TYPE, APPLICATION_JSON_TYPE);
			cgcRequestBuilder.setHeaders(headers);

		}

		return cgcRequestBuilder.createCgcRequest();
	}

	private boolean bodyExists(Command command) {
		return !command.getCommandKeyValues().isEmpty();
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
