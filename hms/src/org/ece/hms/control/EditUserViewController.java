package org.ece.hms.control;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import org.ece.hms.data.DoctorDAO;
import org.ece.hms.data.FinanceDAO;
import org.ece.hms.data.StaffDAO;
import org.ece.hms.data.UserDAO;
import org.ece.hms.model.Doctor;
import org.ece.hms.model.Finance;
import org.ece.hms.model.RoleType;
import org.ece.hms.model.Staff;
import org.ece.hms.model.User;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class EditUserViewController extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox firstNameTxb, lastNameTxb, usernameTxb, doctorId, passwordTxb;
	private Listbox roleLbx, activeLbx;
	private Window editUserWindow;
	private Label mesgLbl;
	private Row doctorRow;
	
	private String role;
	
	public void onSelect$roleLbx(SelectEvent<Listitem, ?> event) {
		Listitem selection = (Listitem)event.getReference();
		if (!selection.getValue().equals(RoleType.STAFF)) {
			doctorRow.setVisible(false);
		} else {
			doctorRow.setVisible(true);
		}
	}

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
			String previousRole = "";
			UserDAO userDAO = new UserDAO();
			User user = new User();
			if (arg.containsKey("id")) {
				user = userDAO.findById(Integer.valueOf(arg.get("id").toString()));
				previousRole = user.getRole();
			}
			if (Util.isEmpty(role)) role = roleLbx.getSelectedItem().getValue().toString();
			user.setFirstName(firstNameTxb.getValue());
			user.setLastName(lastNameTxb.getValue());
			user.setUsername(usernameTxb.getValue());
			if (!arg.containsKey("id") || Util.isNotEmpty(passwordTxb.getValue())) {
				user.setPassword(Util.getPasswordHash(passwordTxb.getValue()));
			}
			user.setRole(role);
			user.setActive(Integer.valueOf(activeLbx.getSelectedItem().getValue().toString()));
			userDAO.startTransaction();
			if (arg.containsKey("id")) {
				userDAO.update(user);
			} else {
				user.setId(userDAO.insertAndReturn(user));
			}
			// create new user, previous role is null, no delete, yes insert
			// edit old user, previous role is valid, different, yes delete, yes insert
			// edit old user, previous role is valid, same, no delete, no insert
			if (Util.isNotEmpty(previousRole) && !user.getRole().equals(previousRole)) {
				if (previousRole.equals(RoleType.DOCTOR)) {
					DoctorDAO doctorDAO = new DoctorDAO();
					Doctor doctor = doctorDAO.findByUserId(user.getId());
					if (Util.isNotEmpty(doctor.getUserId())) doctorDAO.delete(doctor);
				} else if (previousRole.equals(RoleType.FINANCE)) {
					FinanceDAO financeDAO = new FinanceDAO();
					Finance finance = financeDAO.findByUserId(user.getId());
					if (Util.isNotEmpty(finance.getUserId())) financeDAO.delete(finance);
				} else if (previousRole.equals(RoleType.STAFF)) {
					StaffDAO staffDAO = new StaffDAO();
					Staff staff = staffDAO.findByUserId(user.getId());
					if (Util.isNotEmpty(staff.getUserId())) staffDAO.delete(staff);
				}
			}
			if (Util.isEmpty(previousRole) || !user.getRole().equals(previousRole)) {
				if (role.equals(RoleType.DOCTOR)) {
					DoctorDAO doctorDAO = new DoctorDAO();
					doctorDAO.insert(new Doctor(user.getId()));
				} else if (role.equals(RoleType.FINANCE)) {
					FinanceDAO financeDAO = new FinanceDAO();
					financeDAO.insert(new Finance(user.getId()));
				} else if (role.equals(RoleType.STAFF)) {
					StaffDAO staffDAO = new StaffDAO();
					staffDAO.insert(new Staff(user.getId(), Integer.valueOf(doctorId.getValue())));
				}
			}
			if (arg.containsKey("grid")) {
				Listbox usersGrid = (Listbox)arg.get("grid");
		    	List<User> unassignedPatients = userDAO.findAll();
		    	usersGrid.setModel(new ListModelList<User>(unassignedPatients));
			}
			userDAO.commmitTransaction();
			editUserWindow.onClose();
		} catch (WrongValueException e) {
			mesgLbl.setValue("Please fix the errors listed above");
			return;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public void onClick$lookupDoctorButton() {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("role", "DOCTOR");
    	map.put("textbox", doctorId);
    	Executions.createComponents("modal_lookup.zul", null, map);
    }
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if (arg.containsKey("id")) {
			UserDAO userDAO = new UserDAO();
			User user = userDAO.findById(Integer.valueOf(arg.get("id").toString()));
			role = user.getRole();
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
		if (arg.containsKey("role")) {
			role = arg.get("role").toString();
			for (Listitem item : roleLbx.getItems()) {
				if (item.getValue().equals(role)) {
					roleLbx.setSelectedItem(item);
				}
			}
			roleLbx.setDisabled(true);
			if (arg.containsKey("title")) {
				editUserWindow.setTitle(arg.get("title").toString());
			}
		}
		if (!role.equals(RoleType.STAFF)) {
			doctorRow.setVisible(false);
		}
	}
}
