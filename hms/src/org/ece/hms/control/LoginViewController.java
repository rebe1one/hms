package org.ece.hms.control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.ece.hms.data.UserDAO;
import org.ece.hms.model.User;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginViewController extends SelectorComposer<Window> {
	@Wire
    private Textbox nameTxb, passwordTxb;
    
    @Wire
    private Label mesgLbl;
    
    @Listen("onClick=#confirmBtn")
    public void confirm() {
        doLogin();
    }
    
    private boolean doLogin() {
    	try {
    		MessageDigest md = MessageDigest.getInstance("SHA1");
    		String passwordString = new String(passwordTxb.getValue());
    		md.update(passwordString.getBytes());
    		BigInteger hash = new BigInteger(1, md.digest());
            String hashStr = hash.toString(16);
    		
    		User user = new User();
    		user.setUsername(nameTxb.getValue());
    		UserDAO userDAO = new UserDAO();
    		user = userDAO.findByLogin(user);
    		
    		if (user.getPassword().equals(hashStr)) {
    			UserCredentialManager.getInstance().authenticate(user);
    			if (UserCredentialManager.getInstance().isAuthenticated())
    				mesgLbl.setValue("Login Successful!");
    			return true;
    		}
    		mesgLbl.setValue("Login Failed!");
    		return false;
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public void doAfterCompose(Window window) throws Exception {
        super.doAfterCompose(window);
     
        //to be implemented, let’s check for a login
    }
}
