package org.matsim.maas.pizza_delivery;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Route;
import org.matsim.contrib.dvrp.examples.onetaxi.OneTaxiRequest;
import org.matsim.contrib.dvrp.optimizer.Request;
import org.matsim.contrib.dvrp.passenger.PassengerRequest;
import org.matsim.contrib.dvrp.passenger.PassengerRequestCreator;
import org.matsim.core.mobsim.framework.events.MobsimAfterSimStepEvent;
import org.matsim.core.mobsim.framework.listeners.MobsimAfterSimStepListener;

public class PizzaRequest implements PassengerRequest {
	public PizzaRequest(Id<Request> id, Id<Person> passengerId, String motorcycle, Link fromLink, Link toLink, double departureTime, double submissionTime) {
	}

	@Override
	public double getEarliestStartTime() {
		return 0;
	}

	@Override
	public Link getFromLink() {
		return null;
	}

	@Override
	public Link getToLink() {
		return null;
	}

	@Override
	public Id<Person> getPassengerId() {
		return null;
	}

	@Override
	public String getMode() {
		return null;
	}

	@Override
	public double getSubmissionTime() {
		return 0;
	}

	@Override
	public Id<Request> getId() {
		return null;
	}

	public static final class PizzaRequestCreator implements MobsimAfterSimStepListener {

		public PizzaRequest createRequest(Id<Request> id, Id<Person> passengerId, Route route, Link fromLink,
											Link toLink, double departureTime, double submissionTime) {
			return new PizzaRequest(id, passengerId, TransportMode.motorcycle, fromLink, toLink, departureTime,
					submissionTime);
		}

		@Override
		public void notifyMobsimAfterSimStep(MobsimAfterSimStepEvent e) {

		}
	}
}
