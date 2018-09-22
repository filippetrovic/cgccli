package org.cancergenomicscloud.cli.handler.parser;

import org.json.JSONObject;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class KeyValueToJsonParser {

	public String getBody(Map<String, String> keyValues) {

		final Map<String, Object> remapped = keyValues.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						this::mapValueToObjectOrArray
				));

		return new JSONObject(remapped).toString();
	}

	private Object mapValueToObjectOrArray(Map.Entry<String, String> entry) {
		final String value = entry.getValue();

		if (value.contains(",")) {

			final Object[] arrayElements = Stream.of(value.split(","))
					.map(String::trim)
					.toArray();

			return JSONObject.wrap(arrayElements);
		} else {
			return JSONObject.wrap(value);
		}
	}

}
