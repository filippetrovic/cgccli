package org.cancergenomicscloud.cli;

import org.cancergenomicscloud.cli.handler.CliCommandHandler;
import org.cancergenomicscloud.cli.handler.Command;
import org.cancergenomicscloud.cli.parser.CliArgumentsParser;
import org.cancergenomicscloud.cli.parser.CommandArgumentsParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

/**
 * Created by Filip.
 */

@RunWith(MockitoJUnitRunner.class)
public class CgcCliTest {

	@Mock
	private CliCommandHandler handler;

	@Mock
	private CliCommandHandler dummyHandler;

	@Test
	public void shouldInvokeCommandHandlerWithAppropriateArguments() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser(new CommandArgumentsParser()))
				.withHandler("first_lvl second_lvl", handler)
				.get();

		// when
		cgcCli.execute(new String[]{"--token", "token123", "first_lvl", "second_lvl", "--paramName", "paramVal"});

		// then
		Mockito.verify(handler)
				.handleCommand(Command.of("first_lvl second_lvl", "token123", Collections.singletonMap("paramName", "paramVal")));

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForIllegalCommand() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser(new CommandArgumentsParser()))
				.withHandler("first_lvl second_lvl", handler)
				.get();

		// when
		cgcCli.execute(new String[]{"--token", "token123", "illegalCommand secondPart", "param1", "param2"});

		// then - exception is thrown

	}

	@Test
	public void shouldExecuteRightHandlerWithAppropriateArguments() throws Exception {
		// given
		CgcCli cgcCli = new CgcCliBuilder(new CliArgumentsParser(new CommandArgumentsParser()))
				.withHandler("first_lvl second_lvl", handler)
				.withHandler("not_used_command_1 second_lvl", dummyHandler)
				.withHandler("not_used_command_2 second_lvl", dummyHandler)
				.get();

		// when
		cgcCli.execute(new String[]{"--token", "token123", "first_lvl", "second_lvl", "--paramName", "paramVal"});

		// then
		Mockito.verify(handler)
				.handleCommand(Command.of("first_lvl second_lvl", "token123", Collections.singletonMap("paramName", "paramVal")));

	}
}
