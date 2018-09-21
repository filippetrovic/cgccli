package org.cancergenomicscloud;

import org.cancergenomicscloud.adapter.http.UnirestHttpClient;
import org.cancergenomicscloud.adapter.output.SystemOutStringOutput;
import org.cancergenomicscloud.cli.CgcCli;
import org.cancergenomicscloud.cli.CgcCliBuilder;
import org.cancergenomicscloud.cli.formatter.JsonResponseFormatter;
import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.handler.CommandHandlerImpl;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;
import org.cancergenomicscloud.cli.parser.CommandArgumentsParser;

import java.util.Collections;

/**
 * Created by Filip.
 */

public class EntryPoint {

	public static void main(String [] args) throws Exception {
		final HttpClient httpClient = new UnirestHttpClient();
		final ResponseFormatter formatter = new JsonResponseFormatter();
		final StringOutput output = new SystemOutStringOutput();
		final CommandArgumentsParser parser = new CommandArgumentsParser();

		try {

			final CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser(parser))
					.withHandler("projects list",
							new CommandHandlerImpl(
									"https://cgc-api.sbgenomics.com/v2/projects",
									Collections.emptySet(),
									httpClient,
									formatter,
									output))
					.withHandler("files list",
							new CommandHandlerImpl(
									"https://cgc-api.sbgenomics.com/v2/files",
									Collections.emptySet(),
									httpClient,
									formatter,
									output))
					.get();

			cgcCli.execute(args);

		} catch (Exception e) {
			output.print(e.getMessage());
		} finally {
			httpClient.shutdown();
		}

	}

}
