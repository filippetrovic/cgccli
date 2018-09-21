package org.cancergenomicscloud.cli.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Filip.
 */

public class QueryParameterParser {

	private static final String QUERY_PREFIX = "--";

	public Map<String, Object> generateQueryParams(List<String> args) {
		final HashMap<String, Object> toRet = new HashMap<>();

		final Iterator<String> iterator = args.iterator();

		while (iterator.hasNext()) {

			final String argument = iterator.next();

			if (argument.startsWith(QUERY_PREFIX)) {
				final String parameterName = argument.substring(QUERY_PREFIX.length());
				final String parameterValue = iterator.next();
				toRet.put(parameterName, parameterValue);
			}
		}

		return toRet;
	}

}
