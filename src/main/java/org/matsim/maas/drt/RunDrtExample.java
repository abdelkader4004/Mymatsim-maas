/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.maas.drt;

import org.matsim.contrib.drt.run.DrtControlerCreator;
import org.matsim.contrib.drt.run.MultiModeDrtConfigGroup;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.contrib.taxi.run.MultiModeTaxiConfigGroup;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.vis.otfvis.OTFVisConfigGroup;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;


public class RunDrtExample {
	public static void main(String[] args) throws URISyntaxException, MalformedURLException {
		Config config = ConfigUtils.loadConfig(
				new URI("file:///C:/Users/abd/IdeaProjects/Mymatsim-maas/scenarios/MyDRT/My_drtconfig_door2door.xml").toURL(),
				new MultiModeDrtConfigGroup(),
				new DvrpConfigGroup(),
				new OTFVisConfigGroup());
		run(config,false);
	}
	public static void run(Config config, boolean otfvis) {
		DrtControlerCreator.createControler(config, otfvis).run();
	}
}
