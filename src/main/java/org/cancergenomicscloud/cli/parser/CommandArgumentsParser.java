package org.cancergenomicscloud.cli.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Filip.
 */

public class CommandArgumentsParser {

	public Map<String, String> parse(List<String> args) {
		final HashMap<String, String> toRet = new HashMap<>();

		final Iterator<String> iterator = args.iterator();

		while (iterator.hasNext()) {

			final String argument = iterator.next();

			if (argument.startsWith(CliArgumentsParser.QUERY_PREFIX)) {
				final String parameterName = argument.substring(CliArgumentsParser.QUERY_PREFIX.length());
				final String parameterValue = iterator.next();
				toRet.put(parameterName, parameterValue);
			}
		}

		return toRet;
	}

}
