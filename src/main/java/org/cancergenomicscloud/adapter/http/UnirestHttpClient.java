package org.cancergenomicscloud.adapter.http;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import org.apache.commons.io.FileUtils;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;

import java.io.File;
import java.net.URL;

/**
 * Created by Filip.
 */

public class UnirestHttpClient implements HttpClient {

	public CgcResponse get(CgcRequest cgcRequest) {

		try {


			final HttpRequest httpRequest = Unirest.get(cgcRequest.getPath())
					.headers(cgcRequest.getHeaders())
					.queryString(cgcRequest.getQueryParams());

			cgcRequest.getPathVariables().forEach(httpRequest::routeParam);

			final HttpResponse<String> httpResponse = httpRequest.asString();

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

			final HttpRequestWithBody httpRequest = Unirest.patch(cgcRequest.getPath())
					.headers(cgcRequest.getHeaders())
					.queryString(cgcRequest.getQueryParams());

			cgcRequest.getPathVariables().forEach(httpRequest::routeParam);

			final HttpResponse<String> httpResponse = httpRequest
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
	public CgcResponse downloadFile(CgcRequest cgcRequest, String fileDestination) {
		try {


			FileUtils.copyURLToFile(new URL(cgcRequest.getPath()), new File(fileDestination));

			return CgcResponse.of("",
					200,
					"OK");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdown() throws Exception {
		Unirest.shutdown();
	}

}
