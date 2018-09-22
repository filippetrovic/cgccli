package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Filip.
 */
public class UpdateFileRequestBuilderTest {

	private RequestBuilder requestBuilder;

	@Before
	public void setUp() throws Exception {
		requestBuilder = new RequestBuilder(
				"https://cgc-api.sbgenomics.com/v2/files/{file}",
				Collections.singleton("file"));
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
		final CgcRequest result = requestBuilder.buildRequest(command);

		// then
		final HashMap<String, String> expectedHeaders = new HashMap<>();
		expectedHeaders.put("X-SBG-Auth-Token", "token123");
		expectedHeaders.put("Content-Type", "application/json");

		final CgcRequest expected = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/{file}")
				.setHeaders(expectedHeaders)
				.setPathVariables(singletonMap("file", "file#id#123"))
				.setBody("{\"name\":\"newName\"}")
				.createCgcRequest();

		assertThat(result).isEqualTo(expected);

	}
}
