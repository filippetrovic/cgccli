package org.cancergenomicscloud.cli.http;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Filip.
 */

public class CgcRequest {

	private final String path;
	private final Map<String, String> headers;

	public static CgcRequest of(String path, Map<String, String> headers) {
		return new CgcRequest(path, headers);
	}

	private CgcRequest(String path, Map<String, String> headers) {
		this.path = path;
		this.headers = headers;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CgcRequest that = (CgcRequest) o;
		return Objects.equals(path, that.path) &&
				Objects.equals(headers, that.headers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, headers);
	}

	@Override
	public String toString() {
		return "CgcRequest{" +
				"path='" + path + '\'' +
				", headers=" + headers +
				'}';
	}
}
