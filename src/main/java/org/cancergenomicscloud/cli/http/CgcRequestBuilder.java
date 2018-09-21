package org.cancergenomicscloud.cli.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CgcRequestBuilder {

	private String path;
	private Map<String, String> headers;
	private Map<String, String> queryParams;
	private Map<String, String> pathVariables;
	private String body;

	public CgcRequestBuilder(String path) {
		this.path = path;
		headers = Collections.emptyMap();
		queryParams = Collections.emptyMap();
		pathVariables = Collections.emptyMap();
		body = null;
	}

	public CgcRequestBuilder setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public CgcRequestBuilder setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
		return this;
	}

	public CgcRequestBuilder setPathVariables(Map<String, String> pathVariables) {
		this.pathVariables = pathVariables;
		return this;
	}

	public CgcRequestBuilder setBody(String body) {
		this.body = body;
		return this;
	}

	public CgcRequest createCgcRequest() {
		return new CgcRequest(path, headers, new HashMap<>(queryParams), pathVariables, body);
	}
}