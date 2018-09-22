package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Filip.
 */

public class ListFilesInProjectRequestBuilderTest {

	private RequestBuilder requestBuilder;

	@Before
	public void setUp() throws Exception {
		requestBuilder = new RequestBuilder(
				"https://cgc-api.sbgenomics.com/v2/files",
				Collections.emptySet(),
				new KeyValueToJsonParser());
	}

	@Test
	public void shouldInvokeHttpClientWithValidRequest() throws Exception {
		// given
		final Command command = Command.of("files list", "token123", singletonMap("project", "test/test"), emptyMap());

		// when
		final CgcRequest resultRequest = requestBuilder.buildRequest(command);

		// then
		final CgcRequest expected = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files")
				.setHeaders(singletonMap("X-SBG-Auth-Token", "token123"))
				.setQueryParams(singletonMap("project", "test/test"))
				.createCgcRequest();

		assertThat(resultRequest).isEqualTo(expected);
	}
}
