package org.unbiquitous.console_app;

import org.unbiquitous.uos.core.InitialProperties;
import org.unbiquitous.uos.core.UOS;

public class Main {
	public static void main(String[] args) {
		UOS uos = new UOS();
		InitialProperties properties = new InitialProperties();
		properties.put("ubiquitos.driver.deploylist", UserDriver.class.getName()+';'
							+ConsoleDriver.class.getName());
		properties.put("ubiquitos.application.deploylist",ConsoleApp.class.getName());
		uos.init(properties);
	}
}
