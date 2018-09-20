package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.formatter.ResponseFormatter;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.cancergenomicscloud.cli.output.StringOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Filip.
 */

@RunWith(MockitoJUnitRunner.class)
public class ListFilesInProjectCommandHandlerTest {

	@Mock
	private HttpClient httpClient;

	@Mock
	private ResponseFormatter responseFormatter;

	@Mock
	private StringOutput stringOutput;

	@InjectMocks
	private ListFilesInProjectCommandHandler handler;

	@Test
	public void shouldInvokeHttpClientWithValidRequest() throws Exception {
		// given
		final Command command = Command.of("files list", "token123", Arrays.asList("--project", "test/test"));

		when(httpClient.get(any()))
				.thenReturn(CgcResponse.of("result body", 200, "OK"));

		when(responseFormatter.format(any()))
				.thenReturn("result body");

		// when
		handler.handleCommand(command);

		// then
		verify(httpClient)
				.get(CgcRequest.of("https://cgc-api.sbgenomics.com/v2/files",
						Collections.singletonMap("X-SBG-Auth-Token", "token123"),
						Collections.singletonMap("project", "test/test")));

		verify(responseFormatter)
				.format(CgcResponse.of("result body", 200, "OK"));

		verify(stringOutput)
				.print("result body");
	}
}