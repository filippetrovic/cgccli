package org.cancergenomicscloud.adapter.http;

import org.assertj.core.api.Assertions;
import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

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
		CgcRequest request = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/projects")
				.setHeaders(Collections.singletonMap("X-SBG-Auth-Token", "edac56a0da534863ad63d71edfde207c"))
				.createCgcRequest();

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
		CgcRequest request = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files")
				.setHeaders(Collections.singletonMap("X-SBG-Auth-Token", "edac56a0da534863ad63d71edfde207c"))
				.setQueryParams(Collections.singletonMap("project", "fpetrovic92/copy-of-simons-genome-diversity-project-sgdp"))
				.createCgcRequest();

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

	@Test
	public void shouldReturnFileDetails() throws Exception {

		// given
		CgcRequest request = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/{file}")
				.setHeaders(Collections.singletonMap("X-SBG-Auth-Token", "edac56a0da534863ad63d71edfde207c"))
				.setPathVariables(Collections.singletonMap("file", "5ba35a05e4b0db63ded17600"))
				.createCgcRequest();

		// when
		final CgcResponse response = httpClient.get(request);

		// then
		Assertions.assertThat(response.getStatusCode())
				.isEqualTo(200);

		Assertions.assertThat(response.getResult())
				.isNotNull()
				.isNotBlank()
				.contains("\"id\":\"5ba35a05e4b0db63ded17600\"",
						"\"name\":\"LP6005441-DNA_A01.annotated.nh.vcf.gz\"",
						"\"size\":26721560843",
						"\"project\":\"fpetrovic92/copy-of-simons-genome-diversity-project-sgdp");

	}

	@Test
	public void shouldSetAndRemoveFileTags() throws Exception {

		HashMap<String, String> headers = new HashMap<>();
		headers.put("X-SBG-Auth-Token", "edac56a0da534863ad63d71edfde207c");
		headers.put("Content-Type", "application/json");


		// set tags
		// given
		CgcRequest setTagsRequest = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/5ba35a05e4b0db63ded17600")
				.setHeaders(headers)
				.setBody("{\"tags\":[\"int_test1\",\"int_test2\"]}")
				.createCgcRequest();

		// when
		final CgcResponse setTagsResponse = httpClient.patch(setTagsRequest);

		// then
		Assertions.assertThat(setTagsResponse.getStatusCode())
				.isEqualTo(200);

		Assertions.assertThat(setTagsResponse.getResult())
				.isNotNull()
				.isNotBlank()
				.contains("int_test1", "int_test2");


		// remove tags
		// given
		CgcRequest removeTagsRequest = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/5ba35a05e4b0db63ded17600")
				.setHeaders(headers)
				.setBody("{\"tags\":[]}")
				.createCgcRequest();

		// when
		final CgcResponse removeTagsResponse = httpClient.patch(removeTagsRequest);

		// then
		Assertions.assertThat(removeTagsResponse.getStatusCode())
				.isEqualTo(200);

		Assertions.assertThat(removeTagsResponse.getResult())
				.isNotNull()
				.isNotBlank()
				.doesNotContain("int_test1", "int_test2");
	}

}
