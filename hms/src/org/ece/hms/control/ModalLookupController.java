package org.ece.hms.control;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.ece.hms.data.UserDAO;
import org.ece.hms.data.VisitDAO;
import org.ece.hms.model.User;
import org.ece.hms.model.Visit;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class ModalLookupController extends SelectorComposer<Window> {
	@Wire
    private Textbox nameTxb;
	
    @Wire
    private Label mesgLbl;
    
    @Wire
    private Grid results;
    
    @Listen("onClick=#confirmBtn")
    public void search() {
    	UserDAO userDAO = new UserDAO();
    	List<User> users = userDAO.findAll();
    	results.setModel(new ListModelList<User>(users));
    }
    
}
