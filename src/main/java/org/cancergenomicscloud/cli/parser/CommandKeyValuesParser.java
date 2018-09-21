package org.cancergenomicscloud.cli.parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Filip.
 */

public class CommandKeyValuesParser {

	public Map<String, String> parse(List<String> args) {
		return args.stream()
				.filter(arg -> arg.contains(CliArgumentsParser.KEY_VALUE_SEPARATOR))
				.map(arg -> arg.split(CliArgumentsParser.KEY_VALUE_SEPARATOR))
				.collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

	}

}
