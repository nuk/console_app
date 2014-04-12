package org.unbiquitous.console_app;

import java.util.List;

import javax.swing.JOptionPane;

import org.unbiquitous.uos.core.InitialProperties;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.CallContext;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.messages.Call;
import org.unbiquitous.uos.core.messageEngine.messages.Response;

public class UserDriver implements UosDriver {

	private String name;
	private UpDriver driver;
	public UserDialog nameDialog = new UserDialog();

	public UserDriver() {
		driver = new UpDriver("uos.user");
		driver.addService("userInfo");
	}
	
	public void init(Gateway gateway, InitialProperties properties,
			String instanceId) {
		name = nameDialog.requestUserName();
	}
	
	public UpDriver getDriver() {
		return driver;
	}

	public List<UpDriver> getParent() {
		return null;
	}
	
	public void userInfo(Call call, Response response, CallContext context) {
		response.addParameter("name", name);
	}

	public void destroy() {}
}

class UserDialog {
	public String requestUserName(){
		return JOptionPane.showInputDialog("Who are you?");
	}
}
