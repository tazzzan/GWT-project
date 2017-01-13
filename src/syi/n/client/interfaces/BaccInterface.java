package syi.n.client.interfaces;


import syi.n.client.model.Bacc;
import syi.n.client.model.User;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BaccInterface  extends Composite
{
	public DockPanel dpanelBacc;
	public Vpanel vpanelBacc;
	public Ftable ftable;
	
	ClickHandler deleteBaccButtonHandler;
	
	FlowPanel baccOrder;
	public TextBox baccNameType;
	Button createBaccButton;
	String infoNameBacc;
	FlowPanel infoBalance;
	String infoBalanceOnBacc;
	
	public BaccInterface(final ClickHandler CreateBaccButtonHandler, final ClickHandler DeleteBaccButtonHandler)
	{
		dpanelBacc = new DockPanel();		
		ftable = new Ftable(DeleteBaccButtonHandler);
		vpanelBacc = new Vpanel(CreateBaccButtonHandler);

		deleteBaccButtonHandler = DeleteBaccButtonHandler;
		
		dpanelBacc.add(vpanelBacc, DockPanel.WEST);
		dpanelBacc.add(ftable, DockPanel.CENTER);
		dpanelBacc.setSize("1000px", "350px");
		
		initWidget(dpanelBacc);
	}

	public String getInfoNameBacc() {
		return infoNameBacc;
	}

	public void setInfoNameBacc(String infoNameBacc) {
		this.infoNameBacc = infoNameBacc;
	}

	public FlowPanel getInfoBalance() {
		return infoBalance;
	}

	public void setInfoBalance(FlowPanel infoBalance) {
		this.infoBalance = infoBalance;
	}

	public String getInfoBalanceOnBacc() {
		return infoBalanceOnBacc;
	}

	public void setInfoBalanceOnBacc(String infoBalanceOnBacc) {
		this.infoBalanceOnBacc = infoBalanceOnBacc;
	}




	public TextBox getBaccNameType() {
		return baccNameType;
	}


	public void setBaccOrder(FlowPanel baccOrder) {
		this.baccOrder = baccOrder;
	}
	
	public Ftable getFtable() {
		return ftable;
	}
	
	public void setFtable()
	{
		this.ftable = new Ftable(deleteBaccButtonHandler);
	}

		
	public class Vpanel extends Composite
	{
		public VerticalPanel vpanelBacc;
				
		public Vpanel(ClickHandler CreateBaccButtonHandler)
		{
			vpanelBacc = new VerticalPanel();
			baccOrder = new FlowPanel();
			baccNameType = new TextBox();
			createBaccButton = new Button("Create");
			infoNameBacc = new String();
			infoBalance = new FlowPanel();
			infoBalanceOnBacc = new String();

					
			baccOrder.add(new Label("Enter the name for the new Balance-Account"));
			baccOrder.setHeight("25px");
			createBaccButton.addClickHandler(CreateBaccButtonHandler);

			vpanelBacc.add(infoBalance);
			vpanelBacc.add(baccOrder);
			vpanelBacc.add(baccNameType);
			vpanelBacc.add(createBaccButton);
			
			vpanelBacc.setSize("300px", "100px");
			
			initWidget(vpanelBacc);
		}
	}
	
	public class Ftable extends Composite
	{
		public FlexTable ftable;
		ClickHandler deleteBaccButtonHandler;
		
		public Ftable(final ClickHandler DeleteBaccButtonHandler)
		{
			ftable = new FlexTable();
			deleteBaccButtonHandler = DeleteBaccButtonHandler;
			initWidget(ftable);
		}
		
		public void setFTableContent(User selectedUser)
		{
			// FlexTable "ftableBacc" gets its cell's content
			int i= 0;
				ftable.setText(i, 1, "B-Acc");
				ftable.setText(i, 2, "Balance");
				i++;
				
				ftable.setText(i,1, "____________");
				ftable.setText(i,2, "____________");
				i++;
	 		for (Bacc elem : selectedUser.getBaccsSet())
		 	{
	 				String infoNameBacc = elem.getNameBacc();
	 				String infoBalanceOnBacc = String.valueOf(elem.getBalance());				
	 				ftable.setText(i,1,infoNameBacc);
	 				ftable.setText(i,2,infoBalanceOnBacc);	
	 				final Button deleteBaccButton = new Button("Delete "+infoNameBacc);
	 				deleteBaccButton.addClickHandler(deleteBaccButtonHandler);
	 				ftable.setWidget(i, 3, deleteBaccButton);
	 				i++;
 			}
	 		
	 		ftable.setText(i,1, "____________");
			ftable.setText(i,2, "____________");
			i++;
				
			ftable.setText(i,1, "Total      ");
			ftable.setText(i,2, ""+selectedUser.getBalance());
			
			ftable.setSize("300px", "250px");
		}
		
		public void clearFTable()
		{
			ftable.removeAllRows();
		}
	}
	
	public class DeleteBaccButton extends Composite
	{
		Button deleteBaccButton;
		
		String baccName;
		
		public DeleteBaccButton(String nameButton, final ClickHandler DeleteBaccButtonHandler)
		{
			deleteBaccButton = new Button(nameButton);
			deleteBaccButton.addClickHandler(DeleteBaccButtonHandler);
			initWidget(deleteBaccButton);
		}
		public void setBaccName(String baccName)
		{
			this.baccName = baccName;
		}
		public String getBaccName()
		{
			return baccName;
		}
	}
	
}
