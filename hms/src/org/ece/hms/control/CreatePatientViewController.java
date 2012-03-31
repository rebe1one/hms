package org.ece.hms.control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.ece.hms.data.PatientDAO;
import org.ece.hms.data.UserDAO;
import org.ece.hms.model.Patient;
import org.ece.hms.model.RoleType;
import org.ece.hms.model.User;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CreatePatientViewController extends GenericForwardComposer<Window> {
	private Textbox firstNameTxb, lastNameTxb, usernameTxb, passwordTxb;
	private Textbox addressTxb, provinceTxb, sinTxb, healthCardNumberTxb,
			phoneNumberTxb;
	private Window createPatientWindow;

	private Label mesgLbl;
	
	private Grid unassignedPatientsGrid;

	public void onClick$confirmBtn() {
		doRegister();
	}

	private void doRegister() {
		if (Util.isEmpty(firstNameTxb.getValue())) {
			mesgLbl.setValue("Please enter a first name");
			return;
		} else if (Util.isEmpty(lastNameTxb.getValue())) {
			mesgLbl.setValue("Please enter a last name");
			return;
		} else if (Util.isEmpty(usernameTxb.getValue())) {
			mesgLbl.setValue("Please enter a username");
			return;
		} else if (Util.isEmpty(passwordTxb.getValue())) {
			mesgLbl.setValue("Please enter a password");
			return;
		} else if (Util.isEmpty(sinTxb.getValue())) {
			mesgLbl.setValue("Please enter a SIN");
			return;
		} else if (Util.isEmpty(healthCardNumberTxb.getValue())) {
			mesgLbl.setValue("Please enter a health card number");
			return;
		} else if (Util.isEmpty(phoneNumberTxb.getValue())) {
			mesgLbl.setValue("Please enter a phone number");
			return;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			String passwordString = new String(passwordTxb.getValue());
			md.update(passwordString.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			String hashStr = hash.toString(16);
			User user = new User(usernameTxb.getValue(), hashStr,
					RoleType.PATIENT, firstNameTxb.getValue(),
					lastNameTxb.getValue(), User.ACTIVE);
			UserDAO userDAO = new UserDAO();
			userDAO.startTransaction();
			int userId = userDAO.insertAndReturn(user);
			if (userId < 1) {
				userDAO.rollbackTransaction();
				mesgLbl.setValue("Error!");
				return;
			}
			Patient patient = new Patient();
			patient.setUserId(userId);
			patient.setAddress(addressTxb.getValue());
			patient.setProvince(provinceTxb.getValue());
			patient.setSIN(Integer.valueOf(sinTxb.getValue()));
			patient.setHealthCardNumber(healthCardNumberTxb.getValue());
			patient.setPhoneNumber(phoneNumberTxb.getValue());
			PatientDAO patientDAO = new PatientDAO();
			if (!patientDAO.insert(patient)) {
				patientDAO.rollbackTransaction();
				mesgLbl.setValue("Error!");
			} else {
				patientDAO.commmitTransaction();
				createPatientWindow.onClose();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
