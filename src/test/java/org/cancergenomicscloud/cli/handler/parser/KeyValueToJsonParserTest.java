package org.cancergenomicscloud.cli.handler.parser;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * Created by Filip.
 */
public class KeyValueToJsonParserTest {

	private KeyValueToJsonParser parser;

	@Before
	public void setUp() {
		parser = new KeyValueToJsonParser();
	}

	@Test
	public void shouldReturnEmptyJsonObjectForEmptyMap() throws Exception {

		// given
		Map<String, String> keyValues = emptyMap();

		// when
		final String result = parser.getBody(keyValues);

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
		keyValues.put("key3", "value3");
		keyValues.put("key4", "value4");

		// when
		final String result = parser.getBody(keyValues);

		// then
		JSONAssert.assertEquals(
				"{" +
						"\"key1\":\"value1\"," +
						"\"key2\":\"value2\"," +
						"\"key3\":\"value3\"," +
						"\"key4\":\"value4\"" +
				"}",
				result,
				JSONCompareMode.NON_EXTENSIBLE);
	}

}
