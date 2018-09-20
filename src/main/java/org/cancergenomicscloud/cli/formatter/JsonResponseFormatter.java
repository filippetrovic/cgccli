package org.cancergenomicscloud.cli.formatter;

import org.cancergenomicscloud.cli.http.CgcResponse;
import org.json.JSONObject;

/**
 * Created by Filip.
 */

public class JsonResponseFormatter implements ResponseFormatter {

	public String format(CgcResponse response) {
		return new JSONObject(response.getResult()).toString(2);
	}

}
