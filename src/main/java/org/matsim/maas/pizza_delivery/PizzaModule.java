package org.matsim.maas.pizza_delivery;

import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.matsim.contrib.dvrp.examples.onetaxi.OneTaxiActionCreator;
import org.matsim.contrib.dvrp.examples.onetaxi.OneTaxiOptimizer;
import org.matsim.contrib.dvrp.examples.onetaxi.OneTaxiRequest;
import org.matsim.contrib.dvrp.fleet.FleetModule;
import org.matsim.contrib.dvrp.optimizer.VrpOptimizer;
import org.matsim.contrib.dvrp.passenger.DefaultPassengerRequestValidator;
import org.matsim.contrib.dvrp.passenger.PassengerEngineQSimModule;
import org.matsim.contrib.dvrp.passenger.PassengerRequestCreator;
import org.matsim.contrib.dvrp.passenger.PassengerRequestValidator;
import org.matsim.contrib.dvrp.router.DvrpModeRoutingModule;
import org.matsim.contrib.dvrp.router.DvrpModeRoutingNetworkModule;
import org.matsim.contrib.dvrp.router.TimeAsTravelDisutility;
import org.matsim.contrib.dvrp.run.AbstractDvrpModeModule;
import org.matsim.contrib.dvrp.run.AbstractDvrpModeQSimModule;
import org.matsim.contrib.dvrp.run.DvrpModes;
import org.matsim.contrib.dvrp.trafficmonitoring.DvrpTravelTimeModule;
import org.matsim.contrib.dvrp.vrpagent.VrpAgentLogic;
import org.matsim.contrib.dvrp.vrpagent.VrpAgentSourceQSimModule;
import org.matsim.core.router.costcalculators.TravelDisutilityFactory;
import org.matsim.core.router.speedy.SpeedyDijkstraFactory;
import org.matsim.core.router.util.TravelTime;

import java.net.URL;

/**
 * Pizza delivery Module
 */

public class PizzaModule extends AbstractDvrpModeModule {


	private final URL fleetSpecificationUrl;
	private final PassengerEngineQSimModule.PassengerEngineType passengerEngineType;


	protected PizzaModule(URL fleetSpecificationUrl, String mode, PassengerEngineQSimModule.PassengerEngineType passengerEngineType) {
		super(mode);
		this.fleetSpecificationUrl=fleetSpecificationUrl;
		this.passengerEngineType = passengerEngineType;
	}
	@Override
	public void install() {
		DvrpModes.registerDvrpMode(binder(), getMode());
		install(new DvrpModeRoutingNetworkModule(getMode(), false));
		install(new DvrpModeRoutingModule(getMode(), new SpeedyDijkstraFactory()));
		bindModal(TravelTime.class).to(Key.get(TravelTime.class, Names.named(DvrpTravelTimeModule.DVRP_ESTIMATED)));
		bindModal(TravelDisutilityFactory.class).toInstance(TimeAsTravelDisutility::new);
		install(new FleetModule(getMode(), fleetSpecificationUrl));
		bindModal(PassengerRequestValidator.class).to(DefaultPassengerRequestValidator.class);
		installQSimModule(new AbstractDvrpModeQSimModule(getMode()) {
			@Override
			protected void configureQSim() {
				install(new VrpAgentSourceQSimModule(getMode()));

				// optimizer that dispatches motorcycles
				bindModal(VrpOptimizer.class).to(PizzaOptimizer.class).asEagerSingleton();

				addModalComponent(PizzaRequest.PizzaRequestCreator.class);

				// converts scheduled tasks into simulated actions (legs and activities)
				bindModal(VrpAgentLogic.DynActionCreator.class).to(PizzaActionCreator.class).asEagerSingleton();
			}
		});
	}
}
