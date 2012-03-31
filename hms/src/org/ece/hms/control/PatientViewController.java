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

public class PatientViewController extends SelectorComposer<Window> {
	@Wire
    private Textbox firstNameTxb, lastNameTxb, addressTxb, provinceTxb, sinTxb, healthCardNumberTxb, phoneNumberTxb;
    
    @Wire
    private Label mesgLbl;
    
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
    		mesgLbl.setValue("Success");
    		return true;
    	} 
    	catch(Exception e){
    		mesgLbl.setValue(e.toString());
    		return false;
    	}
    }
}
