package org.unbiquitous.console_app;

import java.util.List;

import org.unbiquitous.uos.core.InitialProperties;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;
import org.unbiquitous.uos.core.applicationManager.UosApplication;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;
import org.unbiquitous.uos.core.messageEngine.messages.Call;
import org.unbiquitous.uos.core.messageEngine.messages.Response;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyDeploy;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyStart;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyUndeploy;

public class ConsoleApp implements UosApplication{

	public void init(OntologyDeploy knowledgeBase,
			InitialProperties properties, String appId) {}

	public void start(Gateway gateway, OntologyStart ontology) {
		askForInput(gateway);
	}

	private void askForInput(Gateway gateway) {
		try {
			List<DriverData> userDrivers = gateway.listDrivers("uos.console");
			if (userDrivers == null){
				System.out.println("No console here, sry.");
				return;
			}
			UpDevice device = userDrivers.get(0).getDevice();
			Call call = new Call("uos.console","askInput");
			call.addParameter("message", "Wat u doin?");
			Response response = gateway.callService(device, call);
			String input = response.getResponseString("input");
			System.out.println(String.format("User is '%s'", input));
			
		} catch (ServiceCallException e) {
			e.printStackTrace();
		}
	}

	private void printUser(Gateway gateway) {
		try {
			Thread.sleep(5000);
			List<DriverData> userDrivers = gateway.listDrivers("uos.user");
			if (userDrivers == null){
				System.out.println("No user here, sry.");
				return;
			}
			
			UpDevice device = userDrivers.get(0).getDevice();
			Call call = new Call("uos.user","userInfo");
			Response response = gateway.callService(device, call);
			String name = response.getResponseString("name");
			System.out.println(String.format("Here we have %s", name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() throws Exception {}

	public void tearDown(OntologyUndeploy ontology) throws Exception {}

}
