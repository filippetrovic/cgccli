package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.HttpClient;

import java.util.Collections;

/**
 * Created by Filip.
 */

public class ListProjectsCommand implements CliCommandHandler {

	private HttpClient httpClient;

	public ListProjectsCommand(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public void handleCommand(Command command) {

		final CgcRequest cgcRequest = CgcRequest.of(
				"https://cgc-api.sbgenomics.com/v2/projects",
				Collections.singletonMap("X-SBG-Auth-Token", command.getAuthToken()));

		httpClient.post(cgcRequest);
	}

}
