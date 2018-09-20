package org.cancergenomicscloud.cli.formatter;

import org.cancergenomicscloud.cli.http.CgcResponse; /**
 * Created by Filip.
 */

public interface ResponseFormatter {

	String format(CgcResponse response);

}
