package org.cancergenomicscloud.cli.http;

/**
 * Created by Filip.
 */

public interface HttpClient {

	CgcResponse get(CgcRequest cgcRequest);
	CgcResponse patch(CgcRequest request);

	void shutdown() throws Exception;
}
