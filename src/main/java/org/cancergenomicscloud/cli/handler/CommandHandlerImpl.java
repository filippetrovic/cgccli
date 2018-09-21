package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Filip.
 */

public class CommandHandlerImpl implements CliCommandHandler {

	private final String path;
	private final HttpClient httpClient;
	private final ResponseFormatter responseFormatter;
	private final StringOutput stringOutput;
	private final QueryParameterParser queryParameterParser;

	public CommandHandlerImpl(String path,
							  HttpClient httpClient,
							  ResponseFormatter responseFormatter,
							  StringOutput stringOutput,
							  QueryParameterParser queryParameterParser) {
		this.httpClient = httpClient;
		this.responseFormatter = responseFormatter;
		this.stringOutput = stringOutput;
		this.queryParameterParser = queryParameterParser;
		this.path = path;
	}

	@Override
	public void handleCommand(Command command) {

		final Map<String, Object> queryParams = queryParameterParser.generateQueryParams(command.getArgs());

		final CgcRequest cgcRequest = CgcRequest.of(
				path,
				Collections.singletonMap("X-SBG-Auth-Token", command.getAuthToken()),
				queryParams);

		final CgcResponse response = httpClient.get(cgcRequest);

		stringOutput.print(responseFormatter.format(response));

	}

}
