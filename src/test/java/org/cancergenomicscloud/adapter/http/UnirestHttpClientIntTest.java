package org.cancergenomicscloud.adapter.http;

import org.assertj.core.api.Assertions;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by Filip.
 */

public class UnirestHttpClientIntTest {

	private static HttpClient httpClient = new UnirestHttpClient();

	@AfterClass
	public static void tearDown() throws Exception {
		httpClient.shutdown();
	}

	@Test
	public void shouldReturnAllProjects() throws Exception {

		// given
		CgcRequest request = CgcRequest.of("https://cgc-api.sbgenomics.com/v2/projects",
				Collections.singletonMap("X-SBG-Auth-Token", "edac56a0da534863ad63d71edfde207c"));

		// when
		final CgcResponse response = httpClient.get(request);

		// then
		Assertions.assertThat(response.getStatusCode())
				.isEqualTo(200);

		Assertions.assertThat(response.getResult())
				.isNotNull()
				.isNotBlank()
				.contains("\"id\":\"fpetrovic92/test\"",
						"\"name\":\"test\"",
						"\"href\":\"https://cgc-api.sbgenomics.com/v2/projects/fpetrovic92/test\"")
				.contains("\"href\":\"https://cgc-api.sbgenomics.com/v2/projects/fpetrovic92/copy-of-simons-genome-diversity-project-sgdp\"",
						"\"id\":\"fpetrovic92/copy-of-simons-genome-diversity-project-sgdp\"",
						"\"name\":\"Copy of Simons Genome Diversity Project (SGDP)\"")
				.contains("\"href\":\"https://cgc-api.sbgenomics.com/v2/projects/fpetrovic92/copy-of-personal-genome-project-uk-pgp-uk\"",
						"\"id\":\"fpetrovic92/copy-of-personal-genome-project-uk-pgp-uk\"",
						"\"name\":\"Copy of Personal Genome Project UK (PGP-UK)\"");

	}

	@Test
	public void shouldReturnAllFileForProject() throws Exception {

		// given
		CgcRequest request = CgcRequest.of("https://cgc-api.sbgenomics.com/v2/files",
				Collections.singletonMap("X-SBG-Auth-Token", "edac56a0da534863ad63d71edfde207c"),
				Collections.singletonMap("project", "fpetrovic92/copy-of-simons-genome-diversity-project-sgdp"));

		// when
		final CgcResponse response = httpClient.get(request);

		// then
		Assertions.assertThat(response.getStatusCode())
				.isEqualTo(200);

		Assertions.assertThat(response.getResult())
				.isNotNull()
				.isNotBlank()
				.contains("\"href\":\"https://cgc-api.sbgenomics.com/v2/files/5ba35a05e4b0db63ded17600\"",
						"\"id\":\"5ba35a05e4b0db63ded17600\"",
						"\"name\":\"LP6005441-DNA_A01.annotated.nh.vcf.gz\"",
						"\"project\":\"fpetrovic92/copy-of-simons-genome-diversity-project-sgdp\"");

	}

}
