package syi.n.client.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateAccountInterface  extends Composite
{
	VerticalPanel vpanelCreateUser;
	
	FlowPanel nickOrder;
	TextBox nickType;
	FlowPanel passwordOrder;
	FlowPanel nickOrderOne;
	FlowPanel passwordOrderAgain;
	PasswordTextBox passwordType;
	PasswordTextBox passwordTypeAgain;
	Button createUserButton;

	public CreateAccountInterface(final ClickHandler CreateNewUserHandler)
	{
		vpanelCreateUser = new VerticalPanel();
		
		nickOrder = new FlowPanel();
		nickOrderOne = new FlowPanel();
		nickType = new TextBox();
		passwordOrder = new FlowPanel();
		passwordOrderAgain = new FlowPanel();
		passwordType = new PasswordTextBox();
		passwordTypeAgain = new PasswordTextBox();
		createUserButton = new Button("Create Account");
		
		nickOrderOne.add(new Label("You don't have an Account?"));
		nickOrderOne.setHeight("30px");
		nickOrder.add(new Label("Enter your new nick here"));
		passwordOrder.add(new Label("Enter your password"));
		passwordOrderAgain.add(new Label("Enter your password again"));
		createUserButton.addClickHandler(CreateNewUserHandler);
		
		vpanelCreateUser.add(nickOrderOne);
		vpanelCreateUser.add(nickOrder);
		vpanelCreateUser.add(nickType);		
		vpanelCreateUser.add(passwordOrder);
		vpanelCreateUser.add(passwordType);	
		vpanelCreateUser.add(passwordOrderAgain);
		vpanelCreateUser.add(passwordTypeAgain);
		vpanelCreateUser.add(createUserButton);
		
		initWidget(vpanelCreateUser);
	}
	
	public VerticalPanel getVpanelCreateUser() {
		return vpanelCreateUser;
	}

	public String getNickType() 
	{
		return nickType.getText();
	}

	public String getPasswordType() {
		return passwordType.getText();
	}
	
	public String getPasswordTypeAgain() {
		return passwordTypeAgain.getText();
	}

	public boolean checkIfPasswordTypesAreEqual()
	{
		boolean check=false;
		if (getPasswordType().equals(getPasswordTypeAgain()))
			check = true;
			
		return check;
	}
	
	
}
