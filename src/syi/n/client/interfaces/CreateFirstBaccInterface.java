package syi.n.client.interfaces;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateFirstBaccInterface extends Composite
{
	VerticalPanel vpanelFirstBacc;
	
	FlowPanel baccOrder;
	TextBox baccNameType;
	Button createBaccButton;
	
	public CreateFirstBaccInterface(final ClickHandler CreateBaccButtonHandler)
	{
		vpanelFirstBacc = new VerticalPanel();
		baccOrder = new FlowPanel();
		baccNameType = new TextBox();
		createBaccButton = new Button("Create Balance-Account");
		
		baccOrder.add(new Label("Type the name for the new Balance-Account"));
		createBaccButton.addClickHandler(CreateBaccButtonHandler);
		
		vpanelFirstBacc.add(baccOrder);
		vpanelFirstBacc.add(baccNameType);
		vpanelFirstBacc.add(createBaccButton);
		
		initWidget(vpanelFirstBacc);


		
	}

	public FlowPanel getBaccOrder() {
		return baccOrder;
	}

	public void setBaccOrder(FlowPanel baccOrder) {
		this.baccOrder = baccOrder;
	}

	public VerticalPanel getVpanelFirstBacc() {
		return vpanelFirstBacc;
	}

	public TextBox getBaccNameType() {
		return baccNameType;
	}

	public Button getCreateBaccButton() {
		return createBaccButton;
	}
}
