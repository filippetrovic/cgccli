package org.cancergenomicscloud.adapter.output;

import org.cancergenomicscloud.cli.output.StringOutput;

/**
 * Created by Filip.
 */

public class SystemOutStringOutput implements StringOutput {

	@Override
	public void print(String text) {
		System.out.println(text);
	}

}
