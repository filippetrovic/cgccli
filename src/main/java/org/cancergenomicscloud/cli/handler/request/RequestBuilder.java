package org.cancergenomicscloud.cli.handler.request;

import org.cancergenomicscloud.cli.handler.Command;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
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

public class RequestBuilder {

	private static final String X_SBG_AUTH_TOKEN = "X-SBG-Auth-Token";
	private static final String CONTENT_TYPE = "Content-Type";
	public static final String APPLICATION_JSON_TYPE = "application/json";

	private final String path;
	private final Set<String> pathVariables;

	public RequestBuilder(String path, Set<String> pathVariables) {
		this.path = path;
		this.pathVariables = pathVariables;
	}

	public CgcRequest buildRequest(Command command) {
		final CgcRequestBuilder cgcRequestBuilder = new CgcRequestBuilder(path)
				.setHeaders(Collections.singletonMap(X_SBG_AUTH_TOKEN, command.getAuthToken()))
				.setQueryParams(getQueryParams(command))
				.setPathVariables(getPathVariables(command));

		if (bodyExists(command)) {

			cgcRequestBuilder.setBody(getBody(command));

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
