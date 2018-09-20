package org.cancergenomicscloud.cli.handler;

import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;


/**
 * Created by Filip.
 */

@RunWith(MockitoJUnitRunner.class)
public class ListProjectsCommandTest {

	@Mock
	private HttpClient httpClient;

	@InjectMocks
	private ListProjectsCommand handler;

	@Test
	public void shouldInvokeHttpClientWithValidRequest() throws Exception {
		// given
		final Command command = Command.of("projects list", "token123", Collections.emptyList());

		// when
		handler.handleCommand(command);

		// then
		Mockito.verify(httpClient)
				.get(CgcRequest.of("https://cgc-api.sbgenomics.com/v2/projects",
						Collections.singletonMap("X-SBG-Auth-Token", "token123")));
	}
}
