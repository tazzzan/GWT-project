package syi.n.client.interfaces;

//import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import syi.n.client.model.Bacc;
import syi.n.client.model.Change;
import syi.n.client.model.User;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class creates the interface where changes can be managed.
 * 
 * @author ilja
 * 
 */
public class ChangesInterface extends Composite {
	public DockPanel dpanelChanges;
	public HorizontalPanel hpanelChanges;
	public VerticalPanel vpanelChanges;
	public FtableChanges ftableChanges;

	FlowPanel changeOrder;
	TextBox changeType;
	FlowPanel descriptionChangeOrder;
	TextBox descriptionChangeType;
	FlowPanel baccsOrder;
	public ListBox baccs;
	FlowPanel baccToChangeBalance;
	Button createChangeButton;
	Double changeValue;
	CheckBox cb;
	List<CheckBox> cbs;
	Button deleteChangesButton;

	public ChangesInterface(final ClickHandler CreateChangeButtonHandler,
			final ClickHandler DeleteChangeButtonHandler) {
		dpanelChanges = new DockPanel();
		vpanelChanges = new VerticalPanel();
		hpanelChanges = new HorizontalPanel();
		ftableChanges = new FtableChanges();

		changeOrder = new FlowPanel();
		changeType = new TextBox();
		descriptionChangeOrder = new FlowPanel();
		descriptionChangeType = new TextBox();
		baccsOrder = new FlowPanel();
		baccs = new ListBox();
		baccToChangeBalance = new FlowPanel();
		createChangeButton = new Button("Change Balance");
		changeValue = new Double(0);
		cbs = new ArrayList<CheckBox>();
		deleteChangesButton = new Button("Delete selected Changes");

		changeOrder.add(new Label("What value has your payment or income?"));
		descriptionChangeOrder.add(new Label("Whats name of your transaction"));
		baccsOrder.add(new Label(
				"Select the Balance-Account that should be changed: "));
		createChangeButton.addClickHandler(CreateChangeButtonHandler);
		deleteChangesButton.addClickHandler(DeleteChangeButtonHandler);
		baccs.setPixelSize(200, 70);
		baccs.setVisibleItemCount(2);

		hpanelChanges.add(ftableChanges);

		vpanelChanges.add(changeOrder);
		vpanelChanges.add(changeType);
		vpanelChanges.add(descriptionChangeOrder);
		vpanelChanges.add(descriptionChangeType);
		vpanelChanges.add(baccsOrder);
		vpanelChanges.add(baccs);
		vpanelChanges.add(createChangeButton);
		vpanelChanges.add(deleteChangesButton);

		dpanelChanges.add(vpanelChanges, DockPanel.WEST);
		dpanelChanges.add(hpanelChanges, DockPanel.CENTER);
		dpanelChanges.setSize("1000px", "350px");

		initWidget(dpanelChanges);
	}

	public FlowPanel getBaccToChangeBalance() {
		return baccToChangeBalance;
	}

	public void setBaccToChangeBalance(FlowPanel baccToChangeBalance) {
		this.baccToChangeBalance = baccToChangeBalance;
	}

	public Double getChangeValue() {
		changeValue = Double.parseDouble(changeType.getText());
		return changeValue;
	}

	public void setChangeValue(Double changeValue) {
		this.changeValue = changeValue;
	}

	public String getDescriptionChangeType() {
		return descriptionChangeType.getText();
	}

	public void setDescriptionChangeType(TextBox descriptionChangeType) {
		this.descriptionChangeType = descriptionChangeType;
	}

	public FlowPanel getDescriptionChangeOrder() {
		return descriptionChangeOrder;
	}

	public DockPanel getDpanelChanges() {
		return dpanelChanges;
	}

	public VerticalPanel getVpanelChanges() {
		return vpanelChanges;
	}

	public ListBox getBaccs() {
		return baccs;
	}

	public void setBaccs(ListBox baccs) {
		this.baccs = baccs;
	}

	public TextBox getChangeType() {
		return changeType;
	}

	public CheckBox getCb() {
		return cb;
	}

	public List<CheckBox> getCbs() {
		return cbs;
	}

	public void clearCbs() {
		getCbs().clear();
	}

	public FtableChanges getFtableChanges() {
		return ftableChanges;
	}

	public FtableChanges getFtableChangesType() {
		FtableChanges ftableChanges;
		ftableChanges = this.ftableChanges.createFtableChanges();
		return ftableChanges;
	}

	public void setLBoxContent(User selectedUser) {
		for (Bacc elem : selectedUser.getBaccsSet()) {
			String infoNameBacc = elem.getNameBacc();
			baccs.addItem(infoNameBacc);
		}
	}

	public String getSelectedLBoxContent() {
		int indexOfBaccsSelected = getBaccs().getSelectedIndex();
		String n = getBaccs().getValue(indexOfBaccsSelected);
		return n;
	}

	public class FtableChanges extends Composite {
		public FlexTable ftableChanges;

		public FtableChanges() {
			ftableChanges = new FlexTable();
			initWidget(ftableChanges);
		}

		public void setFtableContent(Bacc shownBacc) {
			// FlexTable "ftableBacc" gets its cell's content
			int i = 0;

			ftableChanges.setText(i, 1, "B-Acc        :");
			ftableChanges.setText(i, 2, shownBacc.getNameBacc());
			i++;

			ftableChanges.setText(i, 1, "____________");
			ftableChanges.setText(i, 2, "____________");
			i++;

			for (Change elem : shownBacc.getChangesSet()) {
				if (i >= 2) {
					String infoValueOfChange = String.valueOf(elem.getChange());
					String infoDescriptionChange = String.valueOf(elem
							.getDescriptionChange());
					cb = new CheckBox(infoDescriptionChange);
					cb.setName("" + elem.getId());
					cbs.add(cb);
					ftableChanges.setWidget(i, 1, cb);
					ftableChanges.setText(i, 2, infoValueOfChange);
					i++;
				}
			}
		}

		public FtableChanges createFtableChanges() {
			FtableChanges ftableChanges = new FtableChanges();
			return ftableChanges;
		}

		public void clearFTable() {
			ftableChanges.removeAllRows();
		}
	}

}