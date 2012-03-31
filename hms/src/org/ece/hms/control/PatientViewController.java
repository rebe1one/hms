package org.ece.hms.control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import org.ece.hms.model.Patient;
import org.ece.hms.data.PatientDAO;
import org.ece.hms.model.User;
import org.ece.hms.data.UserDAO;

public class PatientViewController extends SelectorComposer<Window> {
	@Wire
    private Textbox firstNameTxb, lastNameTxb, addressTxb, provinceTxb, sinTxb, healthCardNumberTxb, phoneNumberTxb;
    
    @Wire
    private Label mesgLbl, welcomeLbl;
    
    @Wire
    private Label firstNameLbl, lastNameLbl, addressLbl, provinceLbl, sinLbl, healthCardNumberLbl, phoneNumberLbl;
    
    @Listen("onClick=#submitBtn")
    public void confirm() {
        doSubmit();
    }
    
    private boolean doSubmit() {
    	try {   		   		
    		Patient patient = new Patient();
    		patient.setAddress(addressTxb.getValue());
    		patient.setProvince(provinceTxb.getValue());
    		patient.setSIN(Integer.valueOf(sinTxb.getValue()));
    		patient.setHealthCardNumber(healthCardNumberTxb.getValue());
    		patient.setPhoneNumber(phoneNumberTxb.getValue());
    		patient.setUserId(UserCredentialManager.getInstance().getUser().getId());
    		PatientDAO patientDAO = new PatientDAO();
    		patientDAO.update(patient);
    		
    		User user = new User();
    		UserDAO userDAO = new UserDAO();
    		user = userDAO.findById(UserCredentialManager.getInstance().getUser().getId());
    		user.setFirstName(firstNameTxb.getValue());
    		user.setLastName(lastNameTxb.getValue());
    		userDAO.update(user);
    		mesgLbl.setValue("Success");
    		resetValues();
    		return true;
    	} 
    	catch(Exception e){
    		mesgLbl.setValue(e.toString());
    		return false;
    	}
    }
    
    private void resetValues() {
    	welcomeLbl.setValue("Welcome " + firstNameTxb.getValue() + "!");
		firstNameLbl.setValue(firstNameTxb.getValue());
		lastNameLbl.setValue(lastNameTxb.getValue());
		addressLbl.setValue(addressTxb.getValue());
		provinceLbl.setValue(provinceTxb.getValue());
		sinLbl.setValue(sinTxb.getValue());
		healthCardNumberLbl.setValue(healthCardNumberTxb.getValue());
		phoneNumberLbl.setValue(phoneNumberTxb.getValue());
    }
}
