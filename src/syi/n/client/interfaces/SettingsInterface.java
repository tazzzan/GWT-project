package syi.n.client.interfaces;
import syi.n.client.entrypoint.Syi;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SettingsInterface extends Composite
{
	VerticalPanel vpanelSettings;
	
	Button changeSettings;
	FlowPanel monthlyBalanceOrder;
	FlowPanel infoMonthlyBalance;
	TextBox monthlyBalanceType;
	Button deleteUserButton;
	
	public SettingsInterface(final ClickHandler ChangeSettingsButtonHandler,
							 final ClickHandler DeleteUserButtonHandler)
	{
		vpanelSettings = new VerticalPanel();
		
		changeSettings = new Button("Change Settings");
		monthlyBalanceOrder = new FlowPanel();
		infoMonthlyBalance = new FlowPanel();
		monthlyBalanceType = new TextBox();
		deleteUserButton = new Button("Delete User");
		
		if(Syi.getSelectedUser().getSettings()!=null)
		infoMonthlyBalance.add(new Label ("actual maximal outcome: ["+Syi.getSelectedUser().getSettings().getMonthlyBalance()+"]"));
		
		monthlyBalanceOrder.add(new Label("Enter your maximal outcome for this mounth:"));
		changeSettings.addClickHandler(ChangeSettingsButtonHandler);
		deleteUserButton.addClickHandler(DeleteUserButtonHandler);
		
		vpanelSettings.add(monthlyBalanceOrder);
		vpanelSettings.add(infoMonthlyBalance);
		vpanelSettings.add(monthlyBalanceType);
		vpanelSettings.add(changeSettings);

		vpanelSettings.add(deleteUserButton);
		vpanelSettings.setHeight("200px");
		
		initWidget(vpanelSettings);
	}

	public VerticalPanel getVpanelSettings() {
		return vpanelSettings;
	}

	public Button getChangeSettings() {
		return changeSettings;
	}

	public FlowPanel getMonthlyBalanceOrder() {
		return monthlyBalanceOrder;
	}

	public TextBox getMonthlyBalanceType() {
		return monthlyBalanceType;
	}
	
	public double getTypedMonthlyBalance()
	{	
		double mounthlyBalance= Double.parseDouble(monthlyBalanceType.getText());
		return mounthlyBalance;
	}
}
