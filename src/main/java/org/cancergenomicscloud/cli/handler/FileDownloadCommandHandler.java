package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;
import org.json.JSONObject;

/**
 * Created by Filip.
 */

public class FileDownloadCommandHandler implements CliCommandHandler {

	private static final String JSON_URL_KEY = "url";
	private static final int HTTP_OK = 200;

	private final RequestBuilder requestBuilder;
	private final HttpClient httpClient;
	private final StringOutput stringOutput;

	public FileDownloadCommandHandler(RequestBuilder requestBuilder, HttpClient httpClient, StringOutput stringOutput) {
		this.requestBuilder = requestBuilder;
		this.httpClient = httpClient;
		this.stringOutput = stringOutput;
	}

	@Override
	public void handleCommand(Command command) {

		final CgcRequest fileInfoRequest = requestBuilder.buildRequest(command);

		final CgcResponse fileInfoResponse = httpClient.get(fileInfoRequest);

		if (failed(fileInfoResponse)) {
			stringOutput.print(fileInfoResponse.getStatusCodeText());
			return;
		}

		final CgcRequest fileDownloadRequest = buildFileDownloadRequest(fileInfoResponse);

		final CgcResponse downloadFileResponse = httpClient
				.downloadFile(fileDownloadRequest, command.getCommandArguments().get("dest"));

		stringOutput.print(downloadFileResponse.getStatusCodeText());

	}

	private boolean failed(CgcResponse fileInfoResponse) {
		return fileInfoResponse.getStatusCode() != HTTP_OK;
	}

	// TODO: Candidate for extraction: single responsibility principle
	private CgcRequest buildFileDownloadRequest(CgcResponse fileInfoResponse) {
		return new CgcRequestBuilder(extractFileDownloadUrl(fileInfoResponse.getResult()))
				.createCgcRequest();
	}

	private String extractFileDownloadUrl(String result) {
		return new JSONObject(result).getString(JSON_URL_KEY);
	}
}
