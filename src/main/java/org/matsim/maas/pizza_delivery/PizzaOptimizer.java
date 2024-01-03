package org.matsim.maas.pizza_delivery;

import com.google.inject.Inject;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Network;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.fleet.Fleet;
import org.matsim.contrib.dvrp.optimizer.Request;
import org.matsim.contrib.dvrp.optimizer.VrpOptimizer;
import org.matsim.contrib.dvrp.router.TimeAsTravelDisutility;
import org.matsim.contrib.dvrp.run.DvrpMode;
import org.matsim.contrib.dvrp.schedule.DefaultStayTask;
import org.matsim.contrib.dvrp.schedule.Task;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.mobsim.framework.MobsimTimer;
import org.matsim.core.router.costcalculators.FreespeedTravelTimeAndDisutility;
import org.matsim.core.router.speedy.SpeedyDijkstraFactory;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.trafficmonitoring.FreeSpeedTravelTime;

public class PizzaOptimizer implements VrpOptimizer {


	enum PizzaTaskType implements Task.TaskType {
		EMPTY_DRIVE, LOADED_DRIVE, PICKUP, DELIVERY, WAIT
	}

	private final EventsManager eventsManager;
	private final MobsimTimer timer;

	private final TravelTime travelTime;
	private final LeastCostPathCalculator router;

	private final DvrpVehicle vehicle;// we have only one vehicle

	public static final double PICKUP_DURATION = 120;
	public static final double DROPOFF_DURATION = 60;
	@Inject
	public PizzaOptimizer(EventsManager eventsManager, @DvrpMode(TransportMode.motorcycle) Network network,
						  @DvrpMode(TransportMode.motorcycle) Fleet fleet, MobsimTimer timer) {
		enum PizzaTaskType implements Task.TaskType {
			EMPTY_DRIVE, LOADED_DRIVE, PICKUP, DELIVERY, WAIT
		}
		this.eventsManager = eventsManager;
		this.timer = timer;
		this.travelTime = new FreeSpeedTravelTime();
		this.router = new SpeedyDijkstraFactory().createPathCalculator(network,new TimeAsTravelDisutility(travelTime),
				travelTime);
		this.vehicle =fleet.getVehicles().values().iterator().next();
		vehicle.getSchedule().addTask(new DefaultStayTask(PizzaOptimizer.PizzaTaskType.WAIT,
				vehicle.getServiceBeginTime(),vehicle.getServiceEndTime(),vehicle.getStartLink()));
	}

	@Override
	public void requestSubmitted(Request request) {

	}

	@Override
	public void nextTask(DvrpVehicle vehicle) {

	}
}
