package org.unbiquitous.console_app;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.unbiquitous.uos.core.InitialProperties;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.CallContext;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpService.ParameterType;
import org.unbiquitous.uos.core.messageEngine.messages.Call;
import org.unbiquitous.uos.core.messageEngine.messages.Response;

public class ConsoleDriver implements UosDriver{

	ConsoleFrame window = new ConsoleFrame();
	private UpDriver definition;

	public ConsoleDriver() {
		definition = new UpDriver("uos.console");
		definition.addService("showMessage")
			.addParameter("message", ParameterType.MANDATORY);
		definition.addService("askInput")
			.addParameter("message", ParameterType.MANDATORY);
	}
	
	public void init(Gateway gateway, InitialProperties properties,
			String instanceId) {
		window.setVisible(true);
	}
	
	public UpDriver getDriver() {
		return definition;
	}

	public List<UpDriver> getParent() {
		return null;
	}

	public void destroy() {}

	public void showMessage(Call call, Response response,
			CallContext callContext) {
		window.output.setText(call.getParameterString("message"));
	}

	public void askInput(Call call, Response response, CallContext callContext) {
		window.output.setText(call.getParameterString("message"));
		try {
			while(window.response == null)Thread.sleep(100);
		} catch (InterruptedException e) {}
		response.addParameter("input", window.response);
		window.response = null;
	}

}

@SuppressWarnings("serial")
class ConsoleFrame extends JFrame{
	Container panel;
	JTextArea output;
	JTextArea input;
	JButton send;
	String response;

	public ConsoleFrame() {
		panel = this.getContentPane();
		panel.setLayout(new FlowLayout());
		
		output = new JTextArea(10,50);
		output.setEnabled(false);
		panel.add(output);
		
		input = new JTextArea(10,50);
		panel.add(input);
		
		send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				response = input.getText();
			}
		});
		panel.add(send);
		this.setSize(600, 400);
	}
}

