package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.handler.request.RequestBuilder;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.output.StringOutput;

import java.util.function.Function;

/**
 * Created by Filip.
 */

public class CommandHandlerImpl implements CliCommandHandler {

	private final RequestBuilder requestBuilder;
	private final Function<CgcRequest, CgcResponse> sendHttpRequestFunction;
	private final ResponseFormatter responseFormatter;
	private final StringOutput stringOutput;


	public CommandHandlerImpl(RequestBuilder requestBuilder,
							  Function<CgcRequest, CgcResponse> sendHttpRequestFunction,
							  ResponseFormatter responseFormatter,
							  StringOutput stringOutput) {

		this.requestBuilder = requestBuilder;
		this.sendHttpRequestFunction = sendHttpRequestFunction;
		this.responseFormatter = responseFormatter;
		this.stringOutput = stringOutput;
	}

	@Override
	public void handleCommand(Command command) {

		final CgcRequest cgcRequest = requestBuilder.buildRequest(command);

		final CgcResponse response = sendHttpRequestFunction.apply(cgcRequest);

		final String formattedOutput = responseFormatter.format(response);

		stringOutput.print(formattedOutput);

	}

}
