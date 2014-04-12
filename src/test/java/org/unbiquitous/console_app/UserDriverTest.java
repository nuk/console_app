package org.unbiquitous.console_app;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.unbiquitous.uos.core.applicationManager.CallContext;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.messages.Call;
import org.unbiquitous.uos.core.messageEngine.messages.Response;

public class UserDriverTest {

	UserDriver driver = new UserDriver();
	
	@Test public void userDriverIsAUosDriver(){
		assertThat(driver).isInstanceOf(UosDriver.class);		
	}
	
	@Test public void userDriverHasAProperInterface(){
		driver.init(null, null, null);
		UpDriver driverDefinition = new UpDriver("uos.user");
		driverDefinition.addService("userInfo");
		assertThat(driver.getDriver()).isEqualTo(driverDefinition);
	}
	
	@Test public void requestUserNameFromUserOnInit(){
		UserDriver driver = new UserDriver();
		driver.nameDialog = new UserDialog(){
			public String requestUserName(){
				return "Clark Kent";
			}
		};
		driver.init(null, null, null);
		
		Response response = new Response();
		driver.userInfo(new Call(), response, new CallContext());
		
		assertThat(response.getResponseString("name")).isEqualTo("Clark Kent");
	}
	
}
