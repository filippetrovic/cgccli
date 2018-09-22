package org.cancergenomicscloud.cli.formatter;

import org.cancergenomicscloud.cli.http.CgcResponse;
import org.json.JSONObject;

/**
 * Created by Filip.
 */

public class CliResponseFormatter implements ResponseFormatter {

	public String format(CgcResponse response) {
		final String formatterOutput = new JSONObject(response.getResult())
				.toString(2) // indentation
				.substring(1) // remove leading {
				.replace(",\n", "\n") // no commas
				.replace("\"", ""); // no quotes

		return formatterOutput.substring(0, formatterOutput.lastIndexOf('}')); // remove trailing }
	}

}
