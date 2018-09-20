package org.cancergenomicscloud.cli;

import org.assertj.core.api.Assertions;
import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

/**
 * Created by Filip.
 */

@RunWith(MockitoJUnitRunner.class)
public class CgcCliTest {

	@Mock
	private CliCommandHandler handler;

	@Mock
	private CliCommandHandler dummyHandler;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void shouldInvokeCommandHandlerWithAppropriateArguments() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder()
				.withHandler("cmd", handler)
				.get();

		// when
		cgcCli.execute(new String[]{"cmd", "param1", "param2"});

		// then
		final ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.verify(handler).handleCommand(argumentCaptor.capture());

		Assertions.assertThat(argumentCaptor.getValue())
				.isNotEmpty()
				.containsExactly("param1", "param2");

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForIllegalCommand() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder()
				.withHandler("cmd", handler)
				.get();

		// when
		cgcCli.execute(new String[]{"illegalCommand", "param1", "param2"});

		// then - exception is thrown

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfHandlerIsAlreadyRegistered() throws Exception {

		// given
		CgcCli cgcCli = new CgcCli();
		cgcCli.registerHandler("cmd", handler);

		// when
		cgcCli.registerHandler("cmd", handler);

		// then - exception is thrown

	}

	@Test
	public void shouldExecuteRightHandlerWithAppropriateArguments() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder()
				.withHandler("cmd", handler)
				.withHandler("not_used_command_1", dummyHandler)
				.withHandler("not_used_command_2", dummyHandler)
				.get();

		// when
		cgcCli.execute(new String[]{"cmd", "param1", "param2"});

		// then
		final ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.verify(handler).handleCommand(argumentCaptor.capture());

		Assertions.assertThat(argumentCaptor.getValue())
				.isNotEmpty()
				.containsExactly("param1", "param2");
	}
}
