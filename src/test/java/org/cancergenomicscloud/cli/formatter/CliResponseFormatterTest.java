package org.cancergenomicscloud.cli.formatter;

import org.cancergenomicscloud.cli.http.CgcResponse;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Filip.
 */

public class CliResponseFormatterTest {

	private CliResponseFormatter formatter = new CliResponseFormatter();

	@Test
	public void shouldFormatJson() throws Exception {
		// given
		String plainJson = "{\"node\":\"value\",\"node2\":[\"tag1\",\"tag2\"],\"node3\":{\"inner\":\"innerValue\"}}";
		CgcResponse response = CgcResponse.of(plainJson, 200, "OK");

		// when
		String result = formatter.format(response);

		// then
		assertThat(result).isEqualTo(
						"\n" +
						"  node: value\n" +
						"  node2: [\n" +
						"    tag1\n" +
						"    tag2\n" +
						"  ]\n" +
						"  node3: {inner: innerValue}\n");

	}

}
