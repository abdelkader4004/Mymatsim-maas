package org.matsim.maas.mosta;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;

public class MaxSpeed {
	private static final String INPUT_NETWORK = "./mosta/mosta-network-v1.0.xml.gz";

	public static void main(String[] args) {

		Network network = NetworkUtils.readNetwork(INPUT_NETWORK);

		double maxFreeSpeed = Double.MIN_VALUE; // Initialize with a very small value
		Link maxSpeedLink = null; // To keep track of the link with the maximum speed

		for (Link link : network.getLinks().values()) {
			double freeSpeed = link.getFreespeed(); // Retrieve Free speed attribute of the link

			if (freeSpeed > maxFreeSpeed) {
				maxFreeSpeed = freeSpeed;
				maxSpeedLink = link; // Update the link with the maximum speed
			}
		}

		if (maxSpeedLink != null) {
			// Print or handle the link with the maximum speed
			System.out.println("Link with the maximum Free Speed  is: " + maxSpeedLink.getId());
			System.out.println("Maximum Free Speed : " + maxFreeSpeed);
		} else {
			System.out.println("No link found in the network.");
		}
	}
}
