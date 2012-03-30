package org.ece.hms.control;

import java.util.List;

import org.ece.hms.data.UserDAO;
import org.ece.hms.model.User;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class ModalLookupController extends GenericForwardComposer<Window> {
    private Textbox nameTxb;
    private Label mesgLbl;
    private Grid results;
    
    @Override
	public void doAfterCompose(Window window) throws Exception {
		super.doAfterCompose(window);

		if (arg.containsKey("textbox")) {
			Textbox box = (Textbox)arg.get("textbox");
			box.setValue("lol");
		}
    }
    
    public void onClick$confirmBtn() {
    	UserDAO userDAO = new UserDAO();
    	List<User> users = userDAO.findAll();
    	results.setModel(new ListModelList<User>(users));
    }
    
}
