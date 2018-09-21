package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.handler.request.RequestBuilder;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;

import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.verify;

/**
 * Created by Filip.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateFileCommandHandlerTest {

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
				httpClient::patch,
				responseFormatter,
				stringOutput);
	}

	@Test
	public void shouldInvokeHttpClientWithValidRequest() throws Exception {

		// given
		final Command command = Command.of(
				"files update",
				"token123",
				singletonMap("file", "file#id#123"),
				singletonMap("name", "newName"));

		// when
		handler.handleCommand(command);

		// then
		final HashMap<String, String> expectedHeaders = new HashMap<>();
		expectedHeaders.put("X-SBG-Auth-Token", "token123");
		expectedHeaders.put("Content-Type", "application/json");

		final CgcRequest expected = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/{file}")
				.setHeaders(expectedHeaders)
				.setPathVariables(singletonMap("file", "file#id#123"))
				.setBody("{\"name\":\"newName\"}")
				.createCgcRequest();

		verify(httpClient)
				.patch(expected);

	}
}
