package org.cancergenomicscloud.adapter.http;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;

/**
 * Created by Filip.
 */

public class UnirestHttpClient implements HttpClient {

	public CgcResponse get(CgcRequest cgcRequest) {

		try {


			final HttpResponse<String> httpResponse = Unirest.get(cgcRequest.getPath())
					.headers(cgcRequest.getHeaders())
					.queryString(cgcRequest.getQueryParams())
					.asString();

			return CgcResponse.of(httpResponse.getBody(),
					httpResponse.getStatus(),
					httpResponse.getStatusText());

		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public CgcResponse patch(CgcRequest cgcRequest) {
		try {


			final HttpResponse<String> httpResponse = Unirest.patch(cgcRequest.getPath())
					.headers(cgcRequest.getHeaders())
					.queryString(cgcRequest.getQueryParams())
					.body(cgcRequest.getBody())
					.asString();

			return CgcResponse.of(httpResponse.getBody(),
					httpResponse.getStatus(),
					httpResponse.getStatusText());

		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdown() throws Exception {
		Unirest.shutdown();
	}

}
