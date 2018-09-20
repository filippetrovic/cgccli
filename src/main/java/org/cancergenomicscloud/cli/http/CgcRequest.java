package org.cancergenomicscloud.cli.http;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Filip.
 */

public class CgcRequest {

	private final String path;
	private final Map<String, String> headers;
	private final Map<String, Object> queryParams;

	public static CgcRequest of(String path, Map<String, String> headers) {
		return of(path, headers, Collections.emptyMap());
	}

	public static CgcRequest of(String path, Map<String, String> headers, Map<String, Object> queryParams) {
		return new CgcRequest(path, headers, queryParams);
	}

	private CgcRequest(String path, Map<String, String> headers, Map<String, Object> queryParams) {
		this.path = path;
		this.headers = headers;
		this.queryParams = queryParams;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CgcRequest that = (CgcRequest) o;
		return Objects.equals(path, that.path) &&
				Objects.equals(headers, that.headers) &&
				Objects.equals(queryParams, that.queryParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, headers, queryParams);
	}

	@Override
	public String toString() {
		return "CgcRequest{" +
				"path='" + path + '\'' +
				", headers=" + headers +
				", queryParams=" + queryParams +
				'}';
	}
}
