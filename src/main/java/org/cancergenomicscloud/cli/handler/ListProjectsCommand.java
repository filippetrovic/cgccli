package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;

import java.util.Collections;

/**
 * Created by Filip.
 */

public class ListProjectsCommand implements CliCommandHandler {

	private HttpClient httpClient;
	private ResponseFormatter responseFormatter;
	private StringOutput stringOutput;

	public ListProjectsCommand(HttpClient httpClient, ResponseFormatter responseFormatter, StringOutput stringOutput) {
		this.httpClient = httpClient;
		this.responseFormatter = responseFormatter;
		this.stringOutput = stringOutput;
	}

	@Override
	public void handleCommand(Command command) {

		final CgcRequest cgcRequest = CgcRequest.of(
				"https://cgc-api.sbgenomics.com/v2/projects",
				Collections.singletonMap("X-SBG-Auth-Token", command.getAuthToken()));

		final CgcResponse response = httpClient.get(cgcRequest);

		stringOutput.print(responseFormatter.format(response));

	}

}
