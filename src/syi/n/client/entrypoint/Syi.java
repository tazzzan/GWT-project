package syi.n.client.entrypoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import syi.n.client.interfaces.AccountInterface;
import syi.n.client.interfaces.AdminInterface;
import syi.n.client.interfaces.BaccInterface;
import syi.n.client.interfaces.ChangesInterface;
import syi.n.client.interfaces.CreateAccountInterface;
import syi.n.client.interfaces.CreateFirstBaccInterface;
import syi.n.client.interfaces.LoginInterface;
import syi.n.client.interfaces.SettingsInterface;
import syi.n.client.interfaces.ChangesInterface.FtableChanges;
import syi.n.client.model.Admin;
import syi.n.client.model.Bacc;
import syi.n.client.model.User;
import syi.n.client.services.UserService;
import syi.n.client.services.UserServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * This class "Syi" (SaveYourIncome) creates the start interface on the load of
 * the module.
 * 
 * The start interface is the login interface. If there is no admin onModuleLoad
 * will create one.
 * 
 * This class also contains the most ClickHandler. They create other interfaces
 * and call the methods from the class "UserServiceAsync".
 * 
 * Interfaces that will be load:
 * 
 * -LoginInterface -AdminInterface(for Admins) -AccountInterface(for Users)
 * -Settings -BaccInterface(Management of Balance-Accounts) -CreateFirstBacc
 * -ChangeInterface(Management of Changes on Balance-Accounts)
 * 
 * Methods that will be load:
 * 
 * -createNewAdmin(nickName, password) -getAdmin(nickName)
 * 
 * -createNewUser(nickName, password) -getUser(nickName) -deleteUser(nickName)
 * 
 * -createBacc(nickName, nameBacc) -getBacc(nickName, nameBacc)
 * -deleteBacc(nickName, nameBacc)
 * 
 * -createChange(nickName, nameBacc, change, descriptionChange)
 * -deleteChange(nickName, List<Integer> changeId)
 * 
 * -changeUserBalance(nickName) -setMonthlyBalance(nickName,
 * typedMonthlyBalance) -setAverageBalance(nickName) -updateAverageBalance()
 * 
 * -writeXML() -deleteDB()
 * 
 * 
 * 
 * 
 * @author ilja
 * 
 */
public class Syi implements EntryPoint {
	/**
	 * Declaration of interfaces
	 */
	LoginInterface loginInterface;
	AccountInterface accountInterface;
	CreateAccountInterface createAccountInterface;
	CreateFirstBaccInterface createFirstBaccInterface;
	BaccInterface baccInterface;
	ChangesInterface changesInterface;
	SettingsInterface settingsInterface;
	AdminInterface adminInterface;

	/**
	 * This Panel "TabPanel" will be added to the RootPanel. It will be the main
	 * panel.
	 */
	final TabPanel tpanel = new TabPanel();

	/**
	 * To set the interfaces it is necessary to declare: -selectedUser (User
	 * that is logined) -selectedBacc (Balance-Account that should be managed)
	 */
	public static User selectedUser = new User();
	public static Bacc selectedBacc = new Bacc();
	/**
	 * Implementation of UserServiceAsync to get the methods from
	 * UserServiceImpl
	 */
	UserServiceAsync handleUser = GWT.create(UserService.class);

	/**
	 * Loading onModuleLoad
	 */
	public void onModuleLoad() {
		handleUser.getAdmin("root", new AsyncCallback<Admin>() 
				// Check whether an	admin-account exist
				{
					@Override
					public void onFailure(Throwable caught) {
					} // get admin failed

					@Override
					public void onSuccess(Admin result) { 
						
						if (result==null){ // if there is no admin-account
							handleUser.createNewAdmin("root", "root",
									new AsyncCallback<Void>() // create new admin-account
									{
										@Override
										public void onFailure(Throwable caught) {
										} // create admin failed

										public void onSuccess(Void result) {
											tpanel.setPixelSize(1000, 800); // Setting tpanel's size
											RootPanel.get("content")
													.add(tpanel); // adding tpanel to RootPanel

											loginInterface = new LoginInterface(
													new SetLogInUserButtonHandler(),
													new SetCreateNewUserButtonHandler());
											showLoginInterface();
										}
									});
						} else {
							tpanel.setPixelSize(1000, 800); // Setting tpanel's size
							RootPanel.get("content").add(tpanel); // adding tpanel to RootPanel

							loginInterface = new LoginInterface(
									new SetLogInUserButtonHandler(),
									new SetCreateNewUserButtonHandler());
							showLoginInterface();
						}
					}
				});
	}

	/**
	 * Here the ClickHandler of the Buttons will be setted
	 * 
	 * -SetLogInUserButtonHandler -SetCreateAdminButtonHandler
	 * -SetCreateXMLButtonHandler -SetDeleteDBButtonHandler
	 * -SetCreateNewUserButtonHandler -SetDeleteUserButtonHandler
	 * -SetLogoutButtonHandler -SetCreateBaccButtonHandler
	 * -SetDeleteBaccButtonHandler -SetCreateChangeButtonHandler
	 * -SetDeleteChangeButtonHandler -SetOpenSettingsInterfaceButtonHandler
	 */

	/**
	 * The function of the Button "Login" will be set
	 * 
	 * @author ilja
	 */
	class SetLogInUserButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			// Check whether User exists. If "true" the selectedUser will be set
			// to loginedUser
			handleUser.getUser(loginInterface.getLoginType(),
					new AsyncCallback<User>() {
						@Override
						public void onFailure(Throwable caught) {}

						@Override
						public void onSuccess(User result) {
							if (result==null){
								handleUser.getAdmin(loginInterface.getLoginType(),
										new AsyncCallback<Admin>() {
											@Override
											public void onFailure(Throwable caught) {
											}

											public void onSuccess(Admin result) {
												result.checkPassword(loginInterface
														.getPasswordType());
												if (result.getPermission() == true) {
													adminInterface = new AdminInterface(
															new SetLogoutButtonHandler(),
															new SetCreateXMLButtonHandler(),
															new SetCreateAdminButtonHandler(),
															new SetDeleteDBButtonHandler());

													setAdminInterface(result);

													showAdminInterface();
												}
											}
										});
							} else {
							result.checkPassword(loginInterface
									.getPasswordType());
							if (result.getPermission() == true) {
								setSelectedUser(result);

								accountInterface = new AccountInterface(
										new SetLogoutButtonHandler(),
										new SetOpenSettingsInterfaceButtonHandler());
								baccInterface = new BaccInterface(
										new SetCreateBaccButtonHandler(),
										new SetDeleteBaccButtonHandler());

								changesInterface = new ChangesInterface(
										new SetCreateChangeButtonHandler(),
										new SetDeleteChangeButtonHandler());

								setAccountInterface();
								setBaccInterface();
								setChangesInterface();

								showAccountInterface();
							} else {
								setLoginFailed();
							}
							}
						}
					});
		}
	}

	/**
	 * The function of the Button "CreateAdmin" will be set
	 * 
	 * @author ilja
	 */
	class SetCreateAdminButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			handleUser.createNewAdmin(adminInterface.getNickType().getText(),
					adminInterface.getPasswordType().getText(),
					new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(Void result) {
							adminInterface.enableLogoutButton();
						}
					});
		}
	}

	/**
	 * The function of the Button "CreateXML-File" will be set
	 * 
	 * @author ilja
	 */
	class SetCreateXMLButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			handleUser.writeXML(new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Void result) {
				}
			});
		}
	}

	/**
	 * The function of the Button "Delete DataBase" will be set
	 * 
	 * @author ilja
	 */
	class SetDeleteDBButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			handleUser.deleteDB(new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
				} // error on delete DB

				@Override
				public void onSuccess(Void result) {
					adminInterface.disableLogoutButton();
				}
			});
		}
	}

	/**
	 * The function of the Button "Create" will be set (on creating new user)
	 * 
	 * @author ilja
	 */
	class SetCreateNewUserButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			// the newUser will be set. The generalUser will be set to newUser
			if (loginInterface.vpanelCreateUser.checkIfPasswordTypesAreEqual() == true) {
				handleUser.createNewUser(
						loginInterface.vpanelCreateUser.getNickType(),
						loginInterface.vpanelCreateUser.getPasswordType(),
						new AsyncCallback<Void>() {
							@Override
							public void onFailure(Throwable caught) {
								System.out.println(" fail on create User");
							}

							@Override
							public void onSuccess(Void result) {
								// select the newUser and setInfoUser()
								handleUser.getUser(
										loginInterface.vpanelCreateUser
												.getNickType(),
										new AsyncCallback<User>() {
											@Override
											public void onFailure(
													Throwable caught) {
												System.out
														.println("Fail on get User");
											}

											@Override
											public void onSuccess(User result) {
												selectedUser = new User();
												setSelectedUser(result);

												System.out.println("got user "
														+ result.getNick()
														+ " before creating firstbacc");
												accountInterface = new AccountInterface(
														new SetLogoutButtonHandler(),
														new SetOpenSettingsInterfaceButtonHandler());
												baccInterface = new BaccInterface(
														new SetCreateBaccButtonHandler(),
														new SetDeleteBaccButtonHandler());

												changesInterface = new ChangesInterface(
														new SetCreateChangeButtonHandler(),
														new SetDeleteChangeButtonHandler());
											}
										});

								// the CreateFirstBaccInterface will be shown.
								// So the user has to add a bacc to his account.
								createFirstBaccInterface = new CreateFirstBaccInterface(
										new SetCreateBaccButtonHandler());

								showCreateFirstBaccInterface();
							}
						});
			}
		}
	}

	/**
	 * The function of the Button "Delete User" will be set
	 * 
	 * @author ilja
	 */
	class SetDeleteUserButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			handleUser.deleteUser(selectedUser.getNick(),
					new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Fail on receive data");
						}

						@Override
						public void onSuccess(Void result) {
							loginInterface = new LoginInterface(
									new SetLogInUserButtonHandler(),
									new SetCreateNewUserButtonHandler());
							showLoginInterface();
						}
					});
		}
	}

	/**
	 * The function of the Button "Logout" will be set
	 * 
	 * @author ilja
	 */
	class SetLogoutButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			setSelectedUser(null);

			loginInterface = new LoginInterface(
					new SetLogInUserButtonHandler(),
					new SetCreateNewUserButtonHandler());
			showLoginInterface();
		}
	}

	/**
	 * The function of the Button "Create" will be set (on creating new
	 * Balance-Account)
	 * 
	 * @author ilja
	 */
	class SetCreateBaccButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			// If a selectedBacc doesn't exist the nameBacc for the new Bacc
			// will be transfered from "createFirstBaccInterface"
			// Otherthise the nameBacc will be transfered from "baccInterface"

			String typedBaccName;
			Iterator<Bacc> it = selectedUser.getBaccsSet().iterator();
			if (it.hasNext()) {
				typedBaccName = baccInterface.getBaccNameType().getText();
			} else {
				typedBaccName = createFirstBaccInterface.getBaccNameType()
						.getText();
			}

			handleUser.createBacc(selectedUser.getNick(), typedBaccName,
					new AsyncCallback<User>() {
						@Override
						public void onFailure(Throwable caught) {
							System.out.println("failed to create a bacc");
						}

						@Override
						public void onSuccess(User result) {
							setSelectedUser(result);

							setAccountInterface();
							setBaccInterface();
							setChangesInterface();

							showChangesInterface();
						}
					});
		}
	}

	/**
	 * The function of the Button "Delete" will be set (the user has the
	 * possibility to select the Balance-Account that should be deleted)
	 * 
	 * @author ilja
	 */
	class SetDeleteBaccButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			String namePressedButton = ((Button) event.getSource()).getText();
			namePressedButton = namePressedButton.replace("Delete ", "");

			handleUser.deleteBacc(getSelectedUser().getNick(),
					namePressedButton, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
						} // error on deleting bacc

						@Override
						public void onSuccess(Void result) {
							handleUser.getUser(getSelectedUser().getNick(),
									new AsyncCallback<User>() {
										@Override
										public void onFailure(Throwable caught) {
										}

										@Override
										public void onSuccess(User result) {
											setSelectedUser(result);
											setAccountInterface();
											setChangesInterface();
											/**
											 * Here the application checks
											 * whether user has any
											 * Balance-Account.
											 * 
											 * If not: the
											 * CreateFirstBaccInterface will be
											 * shown
											 */
											@SuppressWarnings("rawtypes")
											Iterator it = selectedUser
													.getBaccsSet().iterator();
											if (it.hasNext()) {
												setBaccInterface();
												showBalanceAccountInterface();
											} else {
												createFirstBaccInterface = new CreateFirstBaccInterface(
														new SetCreateBaccButtonHandler());
												showCreateFirstBaccInterface();
											}
										}
									});
						}
					});
		}
	}

	/**
	 * The function of the Button "Change Balance" will be set
	 * 
	 * @author ilja
	 */
	class SetCreateChangeButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			/**
			 * The Balance-Account that was selected in the ListBox "baccs" on
			 * ChangesInterface will be set to "selectedBacc"
			 */
			String content = changesInterface.getSelectedLBoxContent();

			handleUser.getBacc(selectedUser.getNick(), content,
					new AsyncCallback<Bacc>() // get selected Bacc in "baccs"
					{ 						  // on ChangesInterface
						@Override
						public void onFailure(Throwable caught) {
						} // getting bacc error

						@Override
						public void onSuccess(Bacc result) {
							setSelectedBacc(result);
							{
								if (changesInterface.getChangeType().getText() != null) 
									// Check whether user typed something
								{
									handleUser.createChange(selectedUser
											.getNick(), selectedBacc
											.getNameBacc(), changesInterface
											.getChangeValue(), changesInterface
											.getDescriptionChangeType(),
											new AsyncCallback<User>() {
												@Override
												public void onFailure(
														Throwable caught) {
												} // error on creating change

												@Override
												public void onSuccess(
														User result) {
													setSelectedUser(result);

													setAccountInterface();
													setBaccInterface();
													setChangesInterface();

													showChangesInterface();
												}
											});
								} else {
									// ADD change value false
								}
							}
						}
					});
		}
	}

	/**
	 * The function of the Button "Delete selected Changes" will be set
	 * 
	 * @author ilja
	 */
	class SetDeleteChangeButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			/**
			 * The changes that were selected in the FlexTable "ftableChanges"
			 * will be saved in List <Integer> changeId.
			 * 
			 * It is possible because the CheckBoxes "cb" in "ftableChanges" are
			 * saved in the List <CheckBox> cbs. The CheckBoxes got the changeId
			 * as the name.
			 */
			List<Integer> changeId = new ArrayList<Integer>();
			for (CheckBox elem : changesInterface.getCbs()) {
				if (elem.getValue() == true) {
					changeId.add(Integer.parseInt(elem.getName()));
					System.out.println(elem.getName());
				}
			}

			handleUser.deleteChange(getSelectedUser().getNick(), changeId,
					new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
						} // error on delete Change

						@Override
						public void onSuccess(Void result) {
							handleUser.getUser(getSelectedUser().getNick(),
									new AsyncCallback<User>() {
										@Override
										public void onFailure(Throwable caught) {
										}

										@Override
										public void onSuccess(User result) {
											setSelectedUser(result);

											setAccountInterface();
											setBaccInterface();
											setChangesInterface();

											showChangesInterface();
										}
									});
						}
					});
		}
	}

	/**
	 * The function of the Button "Your Settings" will be set
	 * 
	 * @author ilja
	 */
	class SetOpenSettingsInterfaceButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			settingsInterface = new SettingsInterface(
					new SetChangeSettingsButtonHandler(),
					new SetDeleteUserButtonHandler());

			showSettingsInterface();
		}
	}

	/**
	 * The function of the Button "Change Settings" will be set
	 * 
	 * @author ilja
	 */
	class SetChangeSettingsButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			double typedMonthlyBalance = settingsInterface
					.getTypedMonthlyBalance();
			handleUser.setMonthlyBalance(getSelectedUser().getNick(),
					typedMonthlyBalance, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
						} // error on setting monthly balance

						@Override
						public void onSuccess(Void result) {
							handleUser.getUser(getSelectedUser().getNick(),
									new AsyncCallback<User>() {
										@Override
										public void onFailure(Throwable caught) {
										} // error on getting user

										@Override
										public void onSuccess(User result) {
											setSelectedUser(result);

											setAccountInterface();

											showAccountInterface();
										}
									});
						}
					});
		}
	}

	/**
	 * Methods needed in this class
	 */

	public void setSelectedUser(User givenUser) {
		Syi.selectedUser = givenUser;
	}

	public static User getSelectedUser() {
		return selectedUser;
	}

	public static Bacc getSelectedBacc() {
		return selectedBacc;
	}

	public void setSelectedBacc(Bacc selectedBacc) {
		Syi.selectedBacc = selectedBacc;
	}

	public UserServiceAsync getHandleUser() {
		return handleUser;
	}

	public double getMonthlyBalance() {
		return getSelectedUser().getSettings().getMonthlyBalance();
	}

	/**
	 * This methods makes the interfaces visible First TabPanel "tpanel" will be
	 * cleared and then the wished interface will be added
	 */

	/**
	 * This method makes "login interface" visible
	 */
	public void showLoginInterface() {
		tpanel.clear();
		tpanel.add(loginInterface, "Login or Create a new User-Account");
		tpanel.selectTab(0);
	}

	/**
	 * This method makes "admin interface" visible
	 */
	public void showAdminInterface() {
		tpanel.clear();
		tpanel.add(adminInterface, "Welcome Admin");
		tpanel.selectTab(0);
	}

	/**
	 * This method makes "account interface" visible
	 */
	public void showAccountInterface() {
		/**
		 * If user does not have any Balance-Account the
		 * CreateFirstBaccInterface will be showm
		 */
		Iterator<Bacc> it = selectedUser.getBaccsSet().iterator();
		if (it.hasNext()) {
			showAccountInterfaceB();
		} else {
			createFirstBaccInterface = new CreateFirstBaccInterface(
					new SetCreateBaccButtonHandler());
			showAccountInterfaceFB();
		}
	}

	/**
	 * This method makes "account interface" visible
	 * ("balance-account interface" is shown because user has a balance-account)
	 */
	public void showAccountInterfaceB() {
		tpanel.clear();
		tpanel.add(accountInterface, "Your Account");
		tpanel.add(baccInterface, "Balance-Accounts");
		tpanel.add(changesInterface, "Changes");
		tpanel.selectTab(0);
	}

	/**
	 * This method makes "account interface" visible
	 * ("create first balance-account interface" is shown because user does not
	 * have a balance-account)
	 */
	public void showAccountInterfaceFB() {
		tpanel.clear();
		tpanel.add(accountInterface, "Your Account");
		tpanel.add(createFirstBaccInterface, "Balance-Accounts");
		tpanel.add(changesInterface, "Changes");
		tpanel.selectTab(0);
	}

	/**
	 * This method makes "Balance-Account interface" visible
	 */
	public void showSettingsInterface() {
		tpanel.clear();
		tpanel.add(settingsInterface, "Your Settings");
		tpanel.selectTab(0);
	}

	/**
	 * This method makes "create first Balance-Account interface" visible
	 */
	public void showCreateFirstBaccInterface() {
		tpanel.clear();
		tpanel.add(createFirstBaccInterface, "Balance-Accounts");
		tpanel.selectTab(0);
	}

	/**
	 * This method makes "Balance-Account interface" visible
	 */
	public void showBalanceAccountInterface() {
		tpanel.clear();
		tpanel.add(accountInterface, "Your Account");
		tpanel.add(baccInterface, "Balance-Accounts");
		tpanel.add(changesInterface, "Changes");
		tpanel.selectTab(1);
	}

	/**
	 * This method makes "changes interface" visible
	 */
	public void showChangesInterface() {
		tpanel.clear();
		tpanel.add(accountInterface, "Your Account");
		tpanel.add(baccInterface, "Balance-Accounts");
		tpanel.add(changesInterface, "Changes");
		tpanel.selectTab(2);
	}

	/**
	 * This methods set the widgets of interfaces
	 */

	public void setLoginFailed() {
		loginInterface.loginFailed.clear();
		loginInterface.setLoginFailed("Login Failed! Please create new User!");
	}

	public void setAdminInterface(Admin admin) {
		adminInterface.enableLogoutButton();
	}

	public void setAccountInterface() {
		accountInterface.getInfoUserBalance().clear();
		accountInterface.setInfoUserBalance(getSelectedUser().getBalance());
		try {
			try {
				accountInterface.getInfoMonthlyBalance().clear();
				accountInterface
						.setInfoMonthlyBalance("Your Maximal Balance this Month: "
								+ getMonthlyBalance());
			} catch (java.lang.Exception e) {
			} // no monthlyBalance error
			try {
				getMonthlyBalance();
				try {
					accountInterface.getInfoUserAvailableBalance().clear();
					accountInterface
							.setInfoUserAvailableBalance("Your rest Balance of this Month: "
									+ getSelectedUser().getAvailableBalance());
				} catch (java.lang.Exception e) {
				} // no availableBalance error
			} catch (java.lang.Exception e) {
			} // no monthlyBalance error
		} catch (java.lang.Exception e) {
		} // no settings error
	}

	public void setBaccInterface() {
		baccInterface.getFtable().clearFTable();
		baccInterface.getFtable().setFTableContent(getSelectedUser());
	}

	public void setChangesInterface() {
		changesInterface.baccs.clear();
		changesInterface.setLBoxContent(getSelectedUser());
		changesInterface.hpanelChanges.clear();
		changesInterface.clearCbs();
		for (Bacc elem : getSelectedUser().getBaccsSet()) {
			FtableChanges ftableChanges = changesInterface
					.getFtableChangesType();
			ftableChanges.clearFTable();
			ftableChanges.setFtableContent(elem);
			changesInterface.hpanelChanges.add(ftableChanges);
		}
	}
}
