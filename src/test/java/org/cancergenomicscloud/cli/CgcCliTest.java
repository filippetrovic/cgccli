package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.cancergenomicscloud.cli.handler.Command;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

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
		CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser())
				.withHandler("first_lvl second_lvl", handler)
				.get();

		// when
		cgcCli.execute(new String[]{"first_lvl", "second_lvl", "param1", "param2"});

		// then
		Mockito.verify(handler)
				.handleCommand(Command.of("first_lvl second_lvl", Arrays.asList("param1", "param2")));

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForIllegalCommand() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser())
				.withHandler("first_lvl second_lvl", handler)
				.get();

		// when
		cgcCli.execute(new String[]{"illegalCommand secondPart", "param1", "param2"});

		// then - exception is thrown

	}

	@Test
	public void shouldExecuteRightHandlerWithAppropriateArguments() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser())
				.withHandler("first_lvl second_lvl", handler)
				.withHandler("not_used_command_1 second_lvl", dummyHandler)
				.withHandler("not_used_command_2 second_lvl", dummyHandler)
				.get();

		// when
		cgcCli.execute(new String[]{"first_lvl", "second_lvl", "param1", "param2"});

		// then
		Mockito.verify(handler)
				.handleCommand(Command.of("first_lvl second_lvl", Arrays.asList("param1", "param2")));

	}
}
