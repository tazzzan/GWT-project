package syi.n.client.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;



public class LoginInterface extends Composite
{
	HorizontalPanel hpanelLogin;
	VerticalPanel vpanelLogin;
	public CreateAccountInterface vpanelCreateUser;
		
	FlowPanel loginOrder;
	public FlowPanel loginFailed;
	TextBox loginType;
	FlowPanel passwordOrder;
	PasswordTextBox passwordType;
	Button loginButton;
	

	public LoginInterface(final ClickHandler logInUserHandler,
						  final ClickHandler createNewUserHandler)
	{
		hpanelLogin = new HorizontalPanel();
		vpanelLogin = new VerticalPanel();
		vpanelCreateUser = new CreateAccountInterface(createNewUserHandler);
		
		loginOrder = new FlowPanel();
		loginFailed = new FlowPanel();
		loginType = new TextBox();
		passwordOrder = new FlowPanel();
		passwordType = new PasswordTextBox();
		loginButton = new Button("Login");
		
		loginOrder.add(new Label("Please enter your nick:"));
		loginOrder.setHeight("30px");	
		loginType.setWidth("120px");
		passwordOrder.add(new Label("Your password:"));
		passwordOrder.setHeight("30px");
		passwordType.setWidth("120px");
		loginButton.addClickHandler(logInUserHandler);
		
		vpanelLogin.add(loginOrder);
		vpanelLogin.add(loginType);		
		vpanelLogin.add(passwordOrder);
		vpanelLogin.add(passwordType);	
		vpanelLogin.add(loginFailed);
		vpanelLogin.add(loginButton);
		vpanelLogin.setHeight("200px");
		
		hpanelLogin.add(vpanelLogin);
		hpanelLogin.add(vpanelCreateUser);
		
		initWidget(hpanelLogin);	
	}
	
	public void setLoginFailed(String text) {
		this.loginFailed.add(new Label(text));
	}

	public VerticalPanel getVpanelLogin() {
		return vpanelLogin;
	}

	public String getLoginType() {
		return loginType.getText();
	}

	public String getPasswordType() {
		return passwordType.getText();
	}


}