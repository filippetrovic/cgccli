package org.cancergenomicscloud.cli.http;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Filip.
 */

public class CgcRequest {
	private final String path;

	private final Map<String, String> headers;
	private final Map<String, Object> queryParams;
	private final Map<String, String> pathVariables;
	private final String body;

	CgcRequest(String path, Map<String, String> headers, Map<String, Object> queryParams, Map<String, String> pathVariables, String body) {
		this.path = path;
		this.headers = headers;
		this.queryParams = queryParams;
		this.pathVariables = pathVariables;
		this.body = body;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public String getBody() {
		return body;
	}

	public Map<String, String> getPathVariables() {
		return pathVariables;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CgcRequest that = (CgcRequest) o;
		return Objects.equals(path, that.path) &&
				Objects.equals(headers, that.headers) &&
				Objects.equals(queryParams, that.queryParams) &&
				Objects.equals(pathVariables, that.pathVariables) &&
				Objects.equals(body, that.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, headers, queryParams, pathVariables, body);
	}

	@Override
	public String toString() {
		return "CgcRequest{" +
				"path='" + path + '\'' +
				", headers=" + headers +
				", queryParams=" + queryParams +
				", pathVariables=" + pathVariables +
				", body='" + body + '\'' +
				'}';
	}
}
