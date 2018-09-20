package org.cancergenomicscloud.cli.parser;

import org.assertj.core.api.Assertions;
import org.cancergenomicscloud.cli.handler.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Filip.
 */
@RunWith(Parameterized.class)
public class CliArgumentsParserTest {

	@Parameterized.Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{new String[] {"projects", "list"},
						Command.of("projects list", Collections.emptyList())},

				{new String[] {"files", "list", "--project", "test/simons-genome-diversity-project-sgdp"},
						Command.of("files list", Arrays.asList("--project", "test/simons-genome-diversity-project-sgdp"))},

				{new String[] {"files", "stat", "--file", "file_id"},
						Command.of("files stat", Arrays.asList("--file", "file_id"))},

				{new String[] {"files", "update", "--file", "file_id", "name=bla"},
						Command.of("files update", Arrays.asList("--file", "file_id", "name=bla"))},

				{new String[] {"files", "update", "--file", "file_id", "metadata.sample_id=asdasf"},
						Command.of("files update", Arrays.asList("--file", "file_id", "metadata.sample_id=asdasf"))},

				{new String[] {"files", "download", "--file", "file_id", "--dest", "/tmp/foo.bar"},
						Command.of("files download", Arrays.asList("--file", "file_id", "--dest", "/tmp/foo.bar"))}
		});
	}

	private CliArgumentsParser cliArgumentsParser = new CliArgumentsParser();

	private String[] cliInputArgs;
	private Command expectedCommand;

	public CliArgumentsParserTest(String[] input, Command expected) {
		cliInputArgs = input;
		expectedCommand = expected;
	}

	@Test
	public void test() {
		final Command command = cliArgumentsParser.parseCommand(cliInputArgs);

		Assertions.assertThat(command).isEqualTo(expectedCommand);
	}

}
