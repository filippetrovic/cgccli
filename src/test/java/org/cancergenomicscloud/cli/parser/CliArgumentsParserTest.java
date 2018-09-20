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
				{new String[] {"--token", "321token123", "projects", "list"},
						Command.of("projects list", "321token123", Collections.emptyList())},

				{new String[] {"--token", "321token123", "files", "list", "--project", "test/simons-genome-diversity-project-sgdp"},
						Command.of("files list", "321token123", Arrays.asList("--project", "test/simons-genome-diversity-project-sgdp"))},

				{new String[] {"--token", "321token123", "files", "stat", "--file", "file_id"},
						Command.of("files stat", "321token123", Arrays.asList("--file", "file_id"))},

				{new String[] {"--token", "321token123", "files", "update", "--file", "file_id", "name=bla"},
						Command.of("files update", "321token123", Arrays.asList("--file", "file_id", "name=bla"))},

				{new String[] {"--token", "321token123", "files", "update", "--file", "file_id", "metadata.sample_id=asdasf"},
						Command.of("files update", "321token123", Arrays.asList("--file", "file_id", "metadata.sample_id=asdasf"))},

				{new String[] {"--token", "321token123", "files", "download", "--file", "file_id", "--dest", "/tmp/foo.bar"},
						Command.of("files download", "321token123", Arrays.asList("--file", "file_id", "--dest", "/tmp/foo.bar"))},
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
