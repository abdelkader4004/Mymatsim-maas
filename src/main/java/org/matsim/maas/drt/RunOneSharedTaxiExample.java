package org.matsim.maas.drt;

import org.matsim.core.utils.io.IOUtils;
import org.matsim.examples.ExamplesUtils;

public class RunOneSharedTaxiExample {

	public static void main(String[] args) {
		 org.matsim.contrib.drt.run.examples.RunOneSharedTaxiExample.run(
				 IOUtils.extendUrl(ExamplesUtils.getTestScenarioURL( "dvrp-grid" ),
				 "one_shared_taxi_config.xml"), false,0);
	}
}
