package org.cancergenomicscloud.adapter.http;

import org.cancergenomicscloud.cli.http.CgcRequest;
import org.cancergenomicscloud.cli.http.CgcRequestBuilder;
import org.cancergenomicscloud.cli.http.CgcResponse;
import org.cancergenomicscloud.cli.http.HttpClient;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Filip.
 */

public class UnirestHttpClientIntTest {

	private final static String TMP_DIR = "unirest";

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
		assertThat(response.getStatusCode())
				.isEqualTo(200);

		assertThat(response.getResult())
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
		assertThat(response.getStatusCode())
				.isEqualTo(200);

		assertThat(response.getResult())
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
		assertThat(response.getStatusCode())
				.isEqualTo(200);

		assertThat(response.getResult())
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
		CgcRequest setTagsRequest = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/{file}")
				.setHeaders(headers)
				.setPathVariables(Collections.singletonMap("file", "5ba35a05e4b0db63ded17600"))
				.setBody("{\"tags\":[\"int_test1\",\"int_test2\"]}")
				.createCgcRequest();

		// when
		final CgcResponse setTagsResponse = httpClient.patch(setTagsRequest);

		// then
		assertThat(setTagsResponse.getStatusCode())
				.isEqualTo(200);

		assertThat(setTagsResponse.getResult())
				.isNotNull()
				.isNotBlank()
				.contains("int_test1", "int_test2");


		// remove tags
		// given
		CgcRequest removeTagsRequest = new CgcRequestBuilder("https://cgc-api.sbgenomics.com/v2/files/{file}")
				.setHeaders(headers)
				.setPathVariables(Collections.singletonMap("file", "5ba35a05e4b0db63ded17600"))
				.setBody("{\"tags\":[]}")
				.createCgcRequest();

		// when
		final CgcResponse removeTagsResponse = httpClient.patch(removeTagsRequest);

		// then
		assertThat(removeTagsResponse.getStatusCode())
				.isEqualTo(200);

		assertThat(removeTagsResponse.getResult())
				.isNotNull()
				.isNotBlank()
				.doesNotContain("int_test1", "int_test2");
	}

	@Test
	public void shouldDownloadFile() throws Exception {

		// given
		final String unirestDir = getClass().getClassLoader().getResource(TMP_DIR).getPath();
		final String fileDestination = unirestDir + File.separator + "test";

		CgcRequest downloadFileRequest = new CgcRequestBuilder("https://cgc-main.s3.amazonaws.com/cfafcf2c-e" +
				"4e3-4e32-953a-9c1482bd96e3%2Bannotated_HG002-NA24385-50x.tab.tab.svf.output.vcf.tsv?x-usern" +
				"ame=fpetrovic92&x-requestId=680ad45f-aa1e-471c-bc3e-2ba5f57553b8&x-project=fpetrovic92%2Fte" +
				"st&response-content-disposition=attachment%3Bfilename%3Dannotated_HG002-NA24385-50x.tab.tab" +
				".svf.output.vcf.tsv&response-content-type=application%2Foctet-stream&X-Amz-Algorithm=AWS4-H" +
				"MAC-SHA256&X-Amz-Date=20180923T102434Z&X-Amz-SignedHeaders=host&X-Amz-Expires=172800&X-Amz-" +
				"Credential=AKIAJQD4ZMI5SNVG2A2A%2F20180923%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=" +
				"e1f6b9ab0aa7606779041491005e552d3537903b78946a7e007059732acb856d")
				.createCgcRequest();

		// when
		httpClient.downloadFile(downloadFileRequest, fileDestination);

		// then
		assertThat(new File(fileDestination))
				.exists();

		// tear down
		for (File file : new File(unirestDir).listFiles()) {
			file.delete();
		}

	}

}
