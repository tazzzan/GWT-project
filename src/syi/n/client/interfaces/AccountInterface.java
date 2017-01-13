package syi.n.client.interfaces;

import syi.n.client.entrypoint.Syi;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccountInterface extends Composite
{
	
	VerticalPanel vpanelAccount;
		
	FlowPanel infoUser;
	FlowPanel infoUserBalance;
	FlowPanel infoUserAvailableBalance;
	FlowPanel infoMonthlyBalance;
	FlowPanel infoAverageBalance;
	Button settingsButton;
	Button logoutButton;
	
	
	public AccountInterface(final ClickHandler LogoutButtonHandler,
							final ClickHandler OpenSettingsInterfaceButtonHandler)
	{
		vpanelAccount = new VerticalPanel();
			
		infoUser = new FlowPanel();
		infoUserBalance = new FlowPanel();
		infoUserAvailableBalance = new FlowPanel();
		infoMonthlyBalance = new FlowPanel();
		infoAverageBalance = new FlowPanel();
		settingsButton = new Button("Your Settings");
		logoutButton = new Button("Logout");
		
		settingsButton.addClickHandler(OpenSettingsInterfaceButtonHandler);
		logoutButton.addClickHandler(LogoutButtonHandler);
		setInfoUser("Welcome: "+Syi.getSelectedUser().getNick()+" !");
		setInfoUserBalance(Syi.getSelectedUser().getBalance());
		infoUser.setHeight("20px");
		infoUserBalance.setHeight("20px");
		infoMonthlyBalance.setHeight("40px");
		
		vpanelAccount.add(infoUser);
		vpanelAccount.add(infoUserBalance);
		vpanelAccount.add(infoAverageBalance);
		vpanelAccount.add(infoUserAvailableBalance);
		vpanelAccount.add(infoMonthlyBalance);
		vpanelAccount.add(settingsButton);
		vpanelAccount.add(logoutButton);
		vpanelAccount.setHeight("200px");
		
		initWidget(vpanelAccount);
	}
	
	public VerticalPanel getVpanelAccount() 
	{
		return vpanelAccount;
	}

	public FlowPanel getInfoUser() {
		return infoUser;
	}
	
	public void setInfoUser(String text) {
		this.infoUser.add(new Label (text));
	}
	
	public FlowPanel getInfoUserBalance() {
		return infoUserBalance;
	}

	public void setInfoUserBalance(double balance) 
	{
		this.infoUserBalance.add(new Label ("Your Balance on this Account: "+balance));
	}

	public FlowPanel getInfoAverageBalance() {
		return infoAverageBalance;
	}

	public FlowPanel getInfoUserAvailableBalance() {
		return infoUserAvailableBalance;
	}

	public void setInfoUserAvailableBalance(String text) {
		infoUserAvailableBalance.add(new Label(text));
	}
	public FlowPanel getInfoMonthlyBalance() 
	{
		return infoMonthlyBalance;
	}

	public void setInfoMonthlyBalance(String text) 
	{
		this.infoMonthlyBalance.add(new Label (text));
	}

	public Button getLogoutButton() 
	{
		return logoutButton;
	}
	
	

	
}
