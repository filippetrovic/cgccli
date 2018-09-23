package org.cancergenomicscloud.cli.handler.parser;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * Created by Filip.
 */
public class JsonBodyGeneratorTest {

	private JsonBodyGenerator parser;

	@Before
	public void setUp() {
		parser = new JsonBodyGenerator();
	}

	@Test
	public void shouldReturnEmptyJsonObjectForEmptyMap() throws Exception {

		// given
		Map<String, String> keyValues = emptyMap();

		// when
		final String result = parser.getBodyAsJson(keyValues);

		// then
		JSONAssert.assertEquals(
				"{}",
				result,
				JSONCompareMode.NON_EXTENSIBLE);
	}

	@Test
	public void shouldReturnJsonObjectWithMultipleKeyValuePairs() throws Exception {

		// given
		Map<String, String> keyValues = new HashMap<>();
		keyValues.put("key1", "value1");
		keyValues.put("key2", "value2");
		keyValues.put("key3", "tag1,tag2,tag3");
		keyValues.put("key4", "value4");
		keyValues.put("metadata.key1.key2", "value");

		// when
		final String result = parser.getBodyAsJson(keyValues);

		// then
		JSONAssert.assertEquals(
				"{" +
						"\"key1\":\"value1\"," +
						"\"key2\":\"value2\"," +
						"\"key3\":[\"tag1\",\"tag2\",\"tag3\"]," +
						"\"key4\":\"value4\"," +
						"\"metadata\":{\"key1\": {\"key2\": \"value\"}}" +
				"}",
				result,
				JSONCompareMode.NON_EXTENSIBLE);
	}

	@Test
	public void shouldParseArrayWithSpacesBetweenElements() throws Exception {

		// given
		Map<String, String> keyValues = singletonMap("array", " tag1, tag2,tag3 ");

		// when
		final String result = parser.getBodyAsJson(keyValues);

		// then
		JSONAssert.assertEquals(
				"{\"array\":[\"tag1\",\"tag2\",\"tag3\"]}",
				result,
				JSONCompareMode.NON_EXTENSIBLE);
	}

	@Test
	public void shouldParseDotInKeyAsNestedJsonObjects() throws Exception {

		// given
		Map<String, String> keyValues = singletonMap("metadata.key", "value");

		// when
		final String result = parser.getBodyAsJson(keyValues);

		// then
		JSONAssert.assertEquals(
				"{\"metadata\":{\"key\": \"value\"}}",
				result,
				JSONCompareMode.NON_EXTENSIBLE);
	}

	@Test
	public void shouldParseDotInKeyAsMultipleNestedJsonObjects() throws Exception {

		// given
		Map<String, String> keyValues = singletonMap("metadata.key1.key2", "value");

		// when
		final String result = parser.getBodyAsJson(keyValues);

		// then
		JSONAssert.assertEquals(
				"{\"metadata\":{\"key1\": {\"key2\": \"value\"}}}",
				result,
				JSONCompareMode.NON_EXTENSIBLE);
	}

}
