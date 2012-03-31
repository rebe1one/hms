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
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class CreatePatientViewController extends GenericForwardComposer<Window> {
    private Textbox firstNameTxb, lastNameTxb, usernameTxb, passwordTxb;
    private Textbox addressTxb, provinceTxb, sinTxb, healthCardNumberTxb, phoneNumberTxb;
	
	private Listbox roleList;
	
    private Label mesgLbl;
    
    public void onClick$confirmBtn() {
        doRegister();
    }
    
    private void doRegister() {
    	try {
    		if (!Util.isEmpty(firstNameTxb.getValue()) && !Util.isEmpty(lastNameTxb.getValue())
        			&& !Util.isEmpty(usernameTxb.getValue())
        			&& !Util.isEmpty(passwordTxb.getValue()) && Util.isNotEmpty(sinTxb.getValue())
					&& Util.isNotEmpty(healthCardNumberTxb.getValue()) && Util.isNotEmpty(phoneNumberTxb.getValue())) {
        		MessageDigest md = MessageDigest.getInstance("SHA1");
        		String passwordString = new String(passwordTxb.getValue());
        		md.update(passwordString.getBytes());
        		BigInteger hash = new BigInteger(1, md.digest());
                String hashStr = hash.toString(16);
                
                User user = new User(usernameTxb.getValue(), hashStr, RoleType.PATIENT, firstNameTxb.getValue(), lastNameTxb.getValue(), User.ACTIVE);
        		UserDAO userDAO = new UserDAO();
        		int userId = userDAO.insertAndReturn(user);
        		if (userId < 1) {
        			mesgLbl.setValue("Error!");
        		}
        		Patient patient = new Patient();
        		patient.setUserId(userId);
        		patient.setAddress(addressTxb.getValue());
        		patient.setProvince(provinceTxb.getValue());
        		patient.setSIN(Integer.valueOf(sinTxb.getValue()));
        		patient.setHealthCardNumber(Integer.valueOf(healthCardNumberTxb.getValue()));
        		patient.setPhoneNumber(Integer.valueOf(phoneNumberTxb.getValue()));
        		PatientDAO patientDAO = new PatientDAO();
        		patientDAO.insert(patient);
        	}
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    	}
    }
}
