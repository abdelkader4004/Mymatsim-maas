package org.matsim.maas.pizza_delivery;


import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.contrib.dvrp.passenger.PassengerEngineQSimModule;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.contrib.dvrp.run.DvrpModule;
import org.matsim.contrib.dvrp.run.DvrpQSimComponents;
import org.matsim.contrib.otfvis.OTFVisLiveModule;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigGroup;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.vis.otfvis.OTFVisConfigGroup;

public class Main {

	public static void main(String[] args) {
		Config config= ConfigUtils.loadConfig("scenarios/MyDvrp/pizza_config.xml", new DvrpConfigGroup(),
				new OTFVisConfigGroup());
		config.controler().setLastIteration(0);
		Scenario scenario= ScenarioUtils.loadScenario(config);
		Controler controler=new Controler(scenario);
		controler.addOverridingModule(new DvrpModule());
		String fleet="pizza_vehicles.xml";
		controler.addOverridingModule(new PizzaModule(ConfigGroup.getInputFileURL(config.getContext(),fleet),
				"motorcycle", PassengerEngineQSimModule.PassengerEngineType.DEFAULT));//TODO: add needed entries for PizzaModule constructor
		controler.configureQSimComponents(DvrpQSimComponents.activateModes(TransportMode.motorcycle));
		if (0==9) {
			controler.addOverridingModule(new OTFVisLiveModule()); // OTFVis visualisation
		}

		// run simulation
		controler.run();
	}
}
