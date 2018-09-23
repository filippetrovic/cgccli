package org.cancergenomicscloud.cli.handler;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonMap;

/**
 * Created by Filip.
 */

public class JsonBodyGenerator {

	public static final String ARRAY_ELEMENT_SEPARATOR = ",";
	public static final String KEY_NESTING_SEPARATOR = ".";
	public static final String KEY_NESTING_SEPARATOR_REGEX = "\\.";

	public String getBodyAsJson(Map<String, String> keyValues) {

		final Map<String, Object> remapped = keyValues.entrySet()
				.stream()
				.collect(Collectors.toMap(
						entry -> entry.getKey(),
						entry -> mapValueToObjectOrArray(entry.getValue())
				))
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						entry -> getLeftmostKeyAfterDot(entry.getKey()),
						entry -> nestJsonObjectsForEachDot(entry)
				));

		return new JSONObject(remapped).toString();
	}

	private Object mapValueToObjectOrArray(String value) {
		if (value.contains(ARRAY_ELEMENT_SEPARATOR)) {

			final Object[] arrayElements = Stream.of(value.split(ARRAY_ELEMENT_SEPARATOR))
					.map(String::trim)
					.toArray();

			return JSONObject.wrap(arrayElements);
		} else {
			return JSONObject.wrap(value);
		}
	}

	private String getLeftmostKeyAfterDot(String key) {
		if (key.contains(KEY_NESTING_SEPARATOR)) {
			return key.substring(0, key.indexOf(KEY_NESTING_SEPARATOR));
		} else {
			return key;
		}
	}

	private Object nestJsonObjectsForEachDot(Map.Entry<String, Object> entry) {

		final String key = entry.getKey();
		if (!key.contains(KEY_NESTING_SEPARATOR)) {
			return entry.getValue();
		}

		final String keyWithoutLeadingKeyPart = key.substring(key.indexOf(KEY_NESTING_SEPARATOR) + 1);

		final List<String> keyParts = Arrays.asList(keyWithoutLeadingKeyPart.split(KEY_NESTING_SEPARATOR_REGEX));

		Collections.reverse(keyParts);

		Object result = JSONObject.wrap(entry.getValue());
		for (String keyPart : keyParts) {
			result = new JSONObject(singletonMap(keyPart, result));
		}

		return result;
	}

}
