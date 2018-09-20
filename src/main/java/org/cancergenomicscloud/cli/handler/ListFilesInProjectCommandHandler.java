package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Filip.
 */

public class ListFilesInProjectCommandHandler implements CliCommandHandler {

	private HttpClient httpClient;
	private ResponseFormatter responseFormatter;
	private StringOutput stringOutput;

	public ListFilesInProjectCommandHandler(HttpClient httpClient, ResponseFormatter responseFormatter, StringOutput stringOutput) {
		this.httpClient = httpClient;
		this.responseFormatter = responseFormatter;
		this.stringOutput = stringOutput;
	}

	@Override
	public void handleCommand(Command command) {

		final Map<String, Object> queryParams = generateQueryParams(command.getArgs());


		final CgcRequest cgcRequest = CgcRequest.of(
				"https://cgc-api.sbgenomics.com/v2/files",
				Collections.singletonMap("X-SBG-Auth-Token", command.getAuthToken()),
				queryParams);

		final CgcResponse response = httpClient.get(cgcRequest);

		stringOutput.print(responseFormatter.format(response));

	}

	private Map<String, Object> generateQueryParams(List<String> args) {
		final HashMap<String, Object> toRet = new HashMap<>();

		final Iterator<String> iterator = args.iterator();

		while (iterator.hasNext()) {

			final String argument = iterator.next();

			if (argument.startsWith("--")) {
				final String parameterName = argument.substring("--".length());
				final String parameterValue = iterator.next();
				toRet.put(parameterName, parameterValue);
			}
		}

		return toRet;
	}

}
