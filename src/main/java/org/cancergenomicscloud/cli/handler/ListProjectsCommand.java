package org.cancergenomicscloud.cli.handler;

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
		httpClient.post("https://cgc-api.sbgenomics.com/v2/projects", Collections.singletonMap("X-SBG-Auth-Token", "token123"));
	}

}
