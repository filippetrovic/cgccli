package org.cancergenomicscloud.cli.http;

import java.util.Objects;

/**
 * Created by Filip.
 */

public class CgcResponse {

	private final String result;
	private final int statusCode;
	private final String statusCodeText;

	public static CgcResponse of(String result, int status, String statusText) {
		return new CgcResponse(result, status, statusText);
	}

	private CgcResponse(String result, int statusCode, String statusCodeText) {
		this.result = result;
		this.statusCode = statusCode;
		this.statusCodeText = statusCodeText;
	}

	public String getResult() {
		return result;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusCodeText() {
		return statusCodeText;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CgcResponse that = (CgcResponse) o;
		return statusCode == that.statusCode &&
				Objects.equals(result, that.result);
	}

	@Override
	public int hashCode() {
		return Objects.hash(result, statusCode);
	}

	@Override
	public String toString() {
		return "CgcResponse{" +
				"result='" + result + '\'' +
				", statusCode=" + statusCode +
				", statusCodeText='" + statusCodeText + '\'' +
				'}';
	}
}
