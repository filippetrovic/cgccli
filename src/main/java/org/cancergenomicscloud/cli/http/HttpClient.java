package org.cancergenomicscloud.cli.http;

/**
 * Created by Filip.
 */

public interface HttpClient {
	CgcResponse get(CgcRequest cgcRequest);

	void shutdown() throws Exception;
}
