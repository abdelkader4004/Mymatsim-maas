package org.matsim.maas.pizza_delivery;

import com.google.inject.Inject;
import org.matsim.api.core.v01.TransportMode;

import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.schedule.Task;
import org.matsim.contrib.dvrp.vrpagent.VrpAgentLogic;
import org.matsim.contrib.dvrp.vrpagent.VrpLegFactory;
import org.matsim.contrib.dynagent.DynAction;
import org.matsim.contrib.dynagent.DynAgent;
import org.matsim.contrib.dynagent.IdleDynActivity;
import org.matsim.core.mobsim.framework.MobsimTimer;

public class PizzaActionCreator implements VrpAgentLogic.DynActionCreator{
	private final MobsimTimer timer;
@Inject
	public PizzaActionCreator(MobsimTimer timer) {
		this.timer = timer;
	}

	@Override
	public DynAction createAction(DynAgent dynAgent, DvrpVehicle vehicle, double now) {
	Task task =vehicle.getSchedule().getCurrentTask();
		switch ((PizzaOptimizer.PizzaTaskType)task.getTaskType()) {
			case EMPTY_DRIVE:
			case LOADED_DRIVE:
				return VrpLegFactory.createWithOfflineTracker(TransportMode.motorcycle, vehicle, timer);

			case PICKUP:
			case DELIVERY:
			case WAIT:
				return new IdleDynActivity(task.getTaskType() + "", task::getEndTime);

			default:
				throw new IllegalStateException();
		}

	}
}
