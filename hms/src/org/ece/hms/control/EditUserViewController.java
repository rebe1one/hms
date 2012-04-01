package org.ece.hms.control;

import java.util.List;
import org.ece.hms.data.UserDAO;
import org.ece.hms.model.User;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class EditUserViewController extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox firstNameTxb, lastNameTxb, usernameTxb;
	private Listbox roleLbx, activeLbx;
	private Window editUserWindow;

	private Label mesgLbl;

	public void onClick$confirmBtn() {
		if (Util.isEmpty(firstNameTxb.getValue())) {
			mesgLbl.setValue("Please enter a first name");
			return;
		} else if (Util.isEmpty(lastNameTxb.getValue())) {
			mesgLbl.setValue("Please enter a last name");
			return;
		} else if (Util.isEmpty(usernameTxb.getValue())) {
			mesgLbl.setValue("Please enter a username");
			return;
		}
		try {
			UserDAO userDAO = new UserDAO();
			User user = new User();
			if (arg.containsKey("id")) {
				user = userDAO.findById(Integer.valueOf(arg.get("id").toString()));
			}
			user.setFirstName(firstNameTxb.getValue());
			user.setLastName(lastNameTxb.getValue());
			user.setUsername(usernameTxb.getValue());
			user.setRole(roleLbx.getSelectedItem().getValue().toString());
			user.setActive(Integer.valueOf(activeLbx.getSelectedItem().getValue().toString()));
			if (arg.containsKey("id")) {
				userDAO.update(user);
			} else {
				userDAO.insert(user);
			}
			if (arg.containsKey("grid")) {
				Listbox usersGrid = (Listbox)arg.get("grid");
		    	List<User> unassignedPatients = userDAO.findAll();
		    	usersGrid.setModel(new ListModelList<User>(unassignedPatients));
			}
			editUserWindow.onClose();
		} catch (WrongValueException e) {
			mesgLbl.setValue("Please fix the errors listed above");
			return;
		}
	}
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if (arg.containsKey("id")) {
			UserDAO userDAO = new UserDAO();
			User user = userDAO.findById(Integer.valueOf(arg.get("id").toString()));
			firstNameTxb.setValue(user.getFirstName());
			lastNameTxb.setValue(user.getLastName());
			usernameTxb.setValue(user.getUsername());
			for (Listitem item : roleLbx.getItems()) {
				if (item.getValue().equals(user.getRole())) {
					roleLbx.setSelectedItem(item);
				}
			}
			activeLbx.setSelectedIndex(user.getActive());
		}
	}
}
