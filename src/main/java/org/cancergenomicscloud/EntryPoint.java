package org.cancergenomicscloud;

import org.cancergenomicscloud.adapter.http.UnirestHttpClient;
import org.cancergenomicscloud.adapter.output.SystemOutStringOutput;
import org.cancergenomicscloud.cli.CgcCli;
import org.cancergenomicscloud.cli.CgcCliBuilder;
import org.cancergenomicscloud.cli.formatter.CliResponseFormatter;
import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.handler.CommandHandlerImpl;
import org.cancergenomicscloud.cli.handler.RequestBuilder;
import org.cancergenomicscloud.cli.handler.parser.KeyValueToJsonParser;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;
import org.cancergenomicscloud.cli.parser.CommandArgumentsParser;
import org.cancergenomicscloud.cli.parser.CommandKeyValuesParser;

import java.util.Collections;

/**
 * Created by Filip.
 */

public class EntryPoint {

	public static void main(String [] args) throws Exception {
		final HttpClient httpClient = new UnirestHttpClient();
		final ResponseFormatter formatter = new CliResponseFormatter();
		final StringOutput output = new SystemOutStringOutput();
		final CommandArgumentsParser argumentsParser = new CommandArgumentsParser();
		final CommandKeyValuesParser keyValuesParser = new CommandKeyValuesParser();
		final KeyValueToJsonParser keyValueParser = new KeyValueToJsonParser();

		try {

			final CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser(argumentsParser, keyValuesParser))
					.withHandler("projects list",
							new CommandHandlerImpl(
									new RequestBuilder(
											"https://cgc-api.sbgenomics.com/v2/projects",
											Collections.emptySet(),
											keyValueParser),
									httpClient::get,
									formatter,
									output))
					.withHandler("files list",
							new CommandHandlerImpl(
									new RequestBuilder(
											"https://cgc-api.sbgenomics.com/v2/files",
											Collections.emptySet(),
											keyValueParser),
									httpClient::get,
									formatter,
									output))
					.withHandler("files stat",
							new CommandHandlerImpl(
									new RequestBuilder(
											"https://cgc-api.sbgenomics.com/v2/files/{file}",
											Collections.singleton("file"),
											keyValueParser),
									httpClient::get,
									formatter,
									output))
					.withHandler("files update",
							new CommandHandlerImpl(
									new RequestBuilder(
											"https://cgc-api.sbgenomics.com/v2/files/{file}",
											Collections.singleton("file"),
											keyValueParser),
									httpClient::patch,
									formatter,
									output))
					.create();

			cgcCli.execute(args);

		} catch (Exception e) {
			output.print(e.getMessage());
		} finally {
			httpClient.shutdown();
		}

	}

}
