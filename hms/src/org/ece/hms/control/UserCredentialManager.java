package org.ece.hms.control;

import org.ece.hms.data.UserDAO;
import org.ece.hms.model.User;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class UserCredentialManager {
	 
    private static final String KEY_USER_MODEL = UserCredentialManager.class.getName()+"_MODEL";
    private UserDAO userDAO;
    private User user;
     
    private UserCredentialManager(){
        userDAO = new UserDAO();
    }
     
    public static UserCredentialManager getInstance() {
        return getInstance(Sessions.getCurrent());
    }
    /**
     * 
     * @return
     */
    public static UserCredentialManager getInstance(Session zkSession){
        synchronized(zkSession){
            UserCredentialManager userModel = (UserCredentialManager) zkSession.getAttribute(KEY_USER_MODEL);
            if(userModel==null){
                zkSession.setAttribute(KEY_USER_MODEL, userModel = new UserCredentialManager());
            }
            return userModel;
        }
    }
    
    public void authenticate(User user) {
    	this.user = user;
    }
    
    public boolean isAuthenticated() {
    	if (user != null) {
    		return true;
    	}
    	return false;
    }
    
    public User getUser() {
    	return this.user;
    }
}