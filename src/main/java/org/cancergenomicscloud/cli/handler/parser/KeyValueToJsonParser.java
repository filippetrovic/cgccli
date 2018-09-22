package org.cancergenomicscloud.cli.handler.parser;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Filip.
 */

public class KeyValueToJsonParser {

	public String getBody(Map<String, String> keyValues) {
		return new JSONObject(keyValues).toString();
	}

}
