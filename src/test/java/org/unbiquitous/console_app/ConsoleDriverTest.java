package org.unbiquitous.console_app;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.unbiquitous.uos.core.applicationManager.CallContext;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpService.ParameterType;
import org.unbiquitous.uos.core.messageEngine.messages.Call;
import org.unbiquitous.uos.core.messageEngine.messages.Response;

public class ConsoleDriverTest {

	ConsoleDriver driver = new ConsoleDriver();
	
	@Test public void ConsoleDriverIsADriver(){
		assertThat(driver).isInstanceOf(UosDriver.class);
	}
	
	@Test public void ConsoleDriverhasAProperInterface(){
		UpDriver definition = new UpDriver("uos.console");
		definition.addService("showMessage")
			.addParameter("message", ParameterType.MANDATORY);
		definition.addService("askInput")
			.addParameter("message", ParameterType.MANDATORY);
		driver.init(null, null, null);
		assertThat(driver.getDriver()).isEqualTo(definition);
	}
	
	@Test public void consoleWindowMustBeVisibleOnInit(){
		assertThat(driver.window.isVisible()).isFalse();
		driver.init(null, null, null);
		assertThat(driver.window.isVisible()).isTrue();
	}
	
	@Test public void showsAMessageToTheUser() throws Exception{
		Call call = new Call();
		call.addParameter("message", "Hey good looking.");
		driver.showMessage(call, new Response(), new CallContext());
		
		assertThat(driver.window.output.getText()).isEqualTo("Hey good looking.");
	}
	
	@Test public void askForAUserResponse() throws Exception{
		final Call call = new Call();
		call.addParameter("message", "What's up?");
		final Response response = new Response();
		Thread t = new Thread(){
			public void run() {
				driver.askInput(call, response, new CallContext());
			};
		};
		t.start();
		
		driver.window.input.setText("Nothing much.");
		driver.window.send.doClick();
		t.join(1000);
		
		assertThat(driver.window.output.getText()).isEqualTo("What's up?");
		assertThat(response.getResponseData("input")).isEqualTo("Nothing much.");
	}
	
}
