package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
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

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Filip.
 */

@RunWith(MockitoJUnitRunner.class)
public class CommandHandlerImplTest {

	@Mock
	private HttpClient httpClient;

	@Mock
	private ResponseFormatter responseFormatter;

	@Mock
	private StringOutput stringOutput;

	@Mock
	private RequestBuilder requestBuilder;

	private CommandHandlerImpl handler;

	@Before
	public void setUp() throws Exception {
		handler = new CommandHandlerImpl(
				requestBuilder,
				httpClient::get,
				responseFormatter,
				stringOutput);
	}

	@Test
	public void collaborationTest() throws Exception {
		// given
		final Command command = Command.of("command code", "token123", emptyMap(), emptyMap());

		final CgcRequest request = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/test")
				.setHeaders(singletonMap("X-SBG-Auth-Token", "token123"))
				.createCgcRequest();

		final String outputString = "result body";

		final CgcResponse response = CgcResponse.of(outputString, 200, "OK");

		when(requestBuilder.buildRequest(command))
				.thenReturn(request);

		when(httpClient.get(request))
				.thenReturn(response);

		when(responseFormatter.format(response))
				.thenReturn(outputString);

		// when
		handler.handleCommand(command);

		// then
		verify(requestBuilder)
				.buildRequest(command);

		verify(httpClient)
				.get(request);

		verify(responseFormatter)
				.format(response);

		verify(stringOutput)
				.print(outputString);
	}


}
