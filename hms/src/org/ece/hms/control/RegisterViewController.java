package org.ece.hms.control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.ece.hms.data.UserDAO;
import org.ece.hms.model.User;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class RegisterViewController extends SelectorComposer<Window> {
	@Wire
    private Textbox firstNameTxb, lastNameTxb, usernameTxb, passwordTxb;
	
	@Wire
	private Listbox roleList;
	
    @Wire
    private Label mesgLbl;
    
    @Listen("onClick=#confirmBtn")
    public void confirm() {
        doRegister();
    }
    
    private void doRegister() {
    	try {
    		if (!Util.isEmpty(firstNameTxb.getValue()) && !Util.isEmpty(lastNameTxb.getValue())
        			&& !Util.isEmpty(roleList.getSelectedItem().getValue().toString()) && !Util.isEmpty(usernameTxb.getValue())
        			&& !Util.isEmpty(passwordTxb.getValue())) {
        		MessageDigest md = MessageDigest.getInstance("SHA1");
        		String passwordString = new String(passwordTxb.getValue());
        		md.update(passwordString.getBytes());
        		BigInteger hash = new BigInteger(1, md.digest());
                String hashStr = hash.toString(16);
                
                User user = new User(usernameTxb.getValue(), hashStr, roleList.getSelectedItem().getValue().toString(), firstNameTxb.getValue(), lastNameTxb.getValue(), User.ACTIVE);
        		UserDAO userDAO = new UserDAO();
        		if (!userDAO.insert(user)) {
        			mesgLbl.setValue("");
        		}
        	}
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    	}
    }
}
