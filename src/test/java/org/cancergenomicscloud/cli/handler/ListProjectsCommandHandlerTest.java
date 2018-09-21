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
public class ListProjectsCommandHandlerTest {

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
						"https://cgc-api.sbgenomics.com/v2/projects",
						Collections.emptySet()),
				httpClient::get,
				responseFormatter,
				stringOutput);
	}

	@Test
	public void shouldInvokeHttpClientWithValidRequest() throws Exception {
		// given
		final Command command = Command.of("projects list", "token123", emptyMap(), emptyMap());

		when(httpClient.get(any()))
				.thenReturn(CgcResponse.of("result body", 200, "OK"));

		when(responseFormatter.format(any()))
				.thenReturn("result body");

		// when
		handler.handleCommand(command);

		// then
		final CgcRequest expected = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/projects")
				.setHeaders(singletonMap("X-SBG-Auth-Token", "token123"))
				.createCgcRequest();

		verify(httpClient)
				.get(expected);

		verify(responseFormatter)
				.format(CgcResponse.of("result body", 200, "OK"));

		verify(stringOutput)
				.print("result body");
	}
}
