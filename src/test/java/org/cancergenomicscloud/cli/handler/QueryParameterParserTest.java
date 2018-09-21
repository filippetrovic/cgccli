package org.cancergenomicscloud.cli.handler;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Created by Filip.
 */

public class QueryParameterParserTest {

	private QueryParameterParser parser = new QueryParameterParser();

	@Test
	public void shouldParseOneQueryParameter() throws Exception {
		// given
		List<String> cliArgs = Arrays.asList("--testParam", "testValue");

		// when
		Map<String, Object> queryParams = parser.generateQueryParams(cliArgs);

		// then
		assertThat(queryParams)
				.containsOnly(entry("testParam", "testValue"));
	}

	@Test
	public void shouldParseMultipleQueryParameter() throws Exception {
		// given
		List<String> cliArgs = Arrays.asList("--testParam", "testValue", "--testParam2", "testParamValue", "--param3", "paramValue3");

		// when
		Map<String, Object> queryParams = parser.generateQueryParams(cliArgs);

		// then
		assertThat(queryParams)
				.containsOnly(
						entry("testParam", "testValue"),
						entry("testParam2", "testParamValue"),
						entry("param3", "paramValue3"));
	}

	@Test
	public void shouldReturnEmptyMapForEmptyInput() throws Exception {
		// given
		List<String> cliArgs = Collections.emptyList();

		// when
		Map<String, Object> queryParams = parser.generateQueryParams(cliArgs);

		// then
		assertThat(queryParams).isEmpty();
	}

	@Test
	public void shouldIgnoreTrailingArgs() throws Exception {
		// given
		List<String> cliArgs = Arrays.asList("--testParam", "testValue", "--testParam2", "testParamValue", "blah-=blah2", "asd=asf");

		// when
		Map<String, Object> queryParams = parser.generateQueryParams(cliArgs);

		// then
		assertThat(queryParams)
				.hasSize(2)
				.containsOnly(
						entry("testParam", "testValue"),
						entry("testParam2", "testParamValue"));
	}

}
