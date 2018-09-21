package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.handler.request.RequestBuilder;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Filip.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetFileStatsCommandHandlerTest {

	@Mock
	private HttpClient httpClient;

	@Mock
	private ResponseFormatter responseFormatter;

	@Mock
	private StringOutput stringOutput;

	private CommandHandlerImpl handler;

	@Before
	public void setUp() throws Exception {
		handler = new CommandHandlerImpl(
				new RequestBuilder(
						"https://cgc-api.sbgenomics.com/v2/files/{file}",
						Collections.singleton("file")),
				httpClient::get,
				responseFormatter,
				stringOutput);
	}

	@Test
	public void shouldInvokeHttpClientWithValidRequest() throws Exception {

		// given
		final Command command = Command.of("files stat", "token123", singletonMap("file", "file#id#123"), emptyMap());

		when(httpClient.get(any()))
				.thenReturn(CgcResponse.of("This is file stats response", 200, "OK"));

		when(responseFormatter.format(any()))
				.thenReturn("This is file stats response");

		// when
		handler.handleCommand(command);

		// then
		final CgcRequest expected = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/{file}")
				.setHeaders(singletonMap("X-SBG-Auth-Token", "token123"))
				.setPathVariables(singletonMap("file", "file#id#123"))
				.createCgcRequest();

		verify(httpClient)
				.get(expected);

		verify(responseFormatter)
				.format(CgcResponse.of("This is file stats response", 200, "OK"));

		verify(stringOutput)
				.print("This is file stats response");

	}
}
