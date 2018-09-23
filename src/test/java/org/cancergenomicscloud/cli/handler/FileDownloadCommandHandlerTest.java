package org.cancergenomicscloud.cli.handler;

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

import java.util.HashMap;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Filip.
 */

@RunWith(MockitoJUnitRunner.class)
public class FileDownloadCommandHandlerTest {

	@Mock
	private HttpClient httpClient;

	@Mock
	private StringOutput stringOutput;

	@Mock
	private RequestBuilder requestBuilder;

	private CliCommandHandler handler;

	@Before
	public void setUp() throws Exception {
		handler = new FileDownloadCommandHandler(
				requestBuilder,
				httpClient,
				stringOutput);
	}

	@Test
	public void collaborationTest() throws Exception {
		// given
		final HashMap<String, String> arguments = new HashMap<>();
		arguments.put("file", "file_id_123");
		arguments.put("dest", "/tmp/foo.bar");

		final Command command = Command.of("files download", "token123", arguments, emptyMap());

		final CgcRequest request = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/file_id_123/download_info")
				.setHeaders(singletonMap("X-SBG-Auth-Token", "token123"))
				.createCgcRequest();

		final String outputString = "{\"url\": \"https://sb-datasets-us-east-1.s3.amazonaws.com/blah/blah\"}";

		final CgcResponse response = CgcResponse.of(outputString, 200, "OK");

		final CgcRequest downloadFileRequest = new CgcRequestBuilder("https://sb-datasets-us-east-1.s3.amazonaws.com/blah/blah")
//				.setHeaders(singletonMap("X-SBG-Auth-Token", "token123"))
				.createCgcRequest();

		final CgcResponse downloadFileResponse = CgcResponse.of("", 200, "OK");

		when(requestBuilder.buildRequest(command))
				.thenReturn(request);

		when(httpClient.get(request))
				.thenReturn(response);

		when(httpClient.downloadFile(downloadFileRequest, "/tmp/foo.bar"))
				.thenReturn(downloadFileResponse);

		// when
		handler.handleCommand(command);

		// then
		verify(requestBuilder)
				.buildRequest(command);

		verify(httpClient)
				.get(request);

		verify(httpClient)
				.downloadFile(downloadFileRequest, "/tmp/foo.bar");

		verify(stringOutput)
				.print("OK");
	}


	@Test
	public void collaborationTestForHttpGetFail() throws Exception {
		// given
		final HashMap<String, String> arguments = new HashMap<>();
		arguments.put("file", "file_id_123");
		arguments.put("dest", "/tmp/foo.bar");

		final Command command = Command.of("files download", "token123", arguments, emptyMap());

		final CgcRequest request = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/file_id_123/download_info")
				.setHeaders(singletonMap("X-SBG-Auth-Token", "token123"))
				.createCgcRequest();


		final CgcResponse response = CgcResponse.of("", 401, "Unauthorized");

		when(requestBuilder.buildRequest(command))
				.thenReturn(request);

		when(httpClient.get(request))
				.thenReturn(response);

		// when
		handler.handleCommand(command);

		// then
		verify(requestBuilder)
				.buildRequest(command);

		verify(httpClient)
				.get(request);

		verify(stringOutput)
				.print("Unauthorized");
	}

}
