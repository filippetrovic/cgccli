package org.cancergenomicscloud.cli.handler.parser;

import org.json.JSONObject;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Filip.
 */

public class KeyValueToJsonParser {

	public static final String ARRAY_ELEMENT_SEPARATOR = ",";

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

		if (value.contains(ARRAY_ELEMENT_SEPARATOR)) {

			final Object[] arrayElements = Stream.of(value.split(ARRAY_ELEMENT_SEPARATOR))
					.map(String::trim)
					.toArray();

			return JSONObject.wrap(arrayElements);
		} else {
			return JSONObject.wrap(value);
		}
	}

}
