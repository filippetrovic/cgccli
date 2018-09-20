package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Filip.
 */

@RunWith(MockitoJUnitRunner.class)
public class CgcCliBuilderTest {

	@Mock
	private CliCommandHandler handler;

	@Mock
	private CliCommandHandler dummyHandler;

	@Before
	public void setUp() throws Exception {

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfHandlerIsAlreadyRegistered() throws Exception {

		new CgcCliBuilder()
				.withHandler("cmd", handler)
				.withHandler("cmd", dummyHandler)
				.get();

	}

}
