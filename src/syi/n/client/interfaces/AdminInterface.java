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

public class AdminInterface extends Composite
{
	HorizontalPanel hpanelAdmin;
	VPanelAdmin vpanelAdmin;
	
	FlowPanel infoAdmin;
	TextBox nickType;
	PasswordTextBox passwordType;
	Button createAdminButton;
	Button deleteDBButton;
	Button createXMLButton;
	Button logoutButton;

	public AdminInterface(final ClickHandler LogoutButtonHandler,
						  final ClickHandler CreateXMLButtonHandler,
						  final ClickHandler CreateAdminButtonHandler,
						  final ClickHandler DeleteDBButtonHandler)
	{
		
			
		hpanelAdmin = new HorizontalPanel();
		vpanelAdmin = new VPanelAdmin(LogoutButtonHandler, CreateXMLButtonHandler, CreateAdminButtonHandler, DeleteDBButtonHandler);
		
		hpanelAdmin.add(vpanelAdmin);
		
		initWidget(hpanelAdmin);
	}

	public FlowPanel getInfoAdmin() {
		return infoAdmin;
	}

	public TextBox getNickType() {
		return nickType;
	}

	public PasswordTextBox getPasswordType() {
		return passwordType;
	}

	
	public Button getCreateAdminButton() {
		return createAdminButton;
	}

	public Button getDeleteDBButton() {
		return deleteDBButton;
	}

	public Button getCreateXMLButton() {
		return createXMLButton;
	}
	
	public Button getLogoutButton()
	{
		return logoutButton;
	}

	public HorizontalPanel getHpanelAdmin() {
		return hpanelAdmin;
	}

	
	public VPanelAdmin getVpanelAdmin()
	{
		return vpanelAdmin;
	}
	
	public void disableLogoutButton()
	{
		getLogoutButton().setVisible(false);
	}
	
	public void enableLogoutButton()
	{
		getLogoutButton().setVisible(true);
	}

	public class VPanelAdmin extends Composite
	{
		public VerticalPanel vpanelAdmin;
			public int id;
		
		public VPanelAdmin(final ClickHandler LogoutButtonHandler,
						   final ClickHandler CreateXMLButtonHandler,
				  		   final ClickHandler CreateAdminButtonHandler,
				  		   final ClickHandler DeleteDBButtonHandler)
		{
			vpanelAdmin = new VerticalPanel();
			
			infoAdmin = new FlowPanel();
			nickType = new TextBox();
			passwordType = new PasswordTextBox();
			createAdminButton = new Button("Create Admin");
			deleteDBButton = new Button("Delete DataBase");
			createXMLButton = new Button("Create XML-File");
			logoutButton = new Button("Logout");
					
			infoAdmin.add(new Label("Create a new admin. First Box: Name, Second Box: Password"));
			createAdminButton.addClickHandler(CreateAdminButtonHandler);
			deleteDBButton.addClickHandler(DeleteDBButtonHandler);
			createXMLButton.addClickHandler(CreateXMLButtonHandler);
			logoutButton.addClickHandler(LogoutButtonHandler);
			
			vpanelAdmin.add(infoAdmin);
			vpanelAdmin.add(nickType);
			vpanelAdmin.add(passwordType);
			vpanelAdmin.add(createAdminButton);
			vpanelAdmin.add(deleteDBButton);
			vpanelAdmin.add(createXMLButton);
			vpanelAdmin.add(logoutButton);
			initWidget(vpanelAdmin);
		}
	}		
}
