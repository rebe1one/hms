package org.ece.hms.control;

import java.util.ArrayList;
import java.util.List;

import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.UserDAO;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.User;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Center;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class ModalLookupController extends GenericForwardComposer<Window> {
    private Textbox nameTxb;
    private Label mesgLbl;
    private Listbox results;
    private Window lookupWin;
    
    @Override
	public void doAfterCompose(Window window) throws Exception {
		super.doAfterCompose(window);
    }
    
    public void onClick$confirmBtn() {
    	List<Filter> filter = new ArrayList<Filter>();
    	String[] split = nameTxb.getValue().split(" ");
		if (split.length == 1) {
			filter.add(Filter.LB);
    		filter.add(new Filter("first_name", split[0]));
    		filter.add(Filter.OR);
    		filter.add(new Filter("last_name", split[0]));
    		filter.add(Filter.RB);
		} else if (split.length > 1) {
			filter.add(Filter.LB);
			filter.add(new Filter("first_name", split[0]));
			filter.add(Filter.OR);
    		filter.add(new Filter("last_name", split[1]));
    		filter.add(Filter.RB);
		}
		if (arg.containsKey("role")) {
			if (filter.size() > 0) filter.add(Filter.AND);
			String role = (String)arg.get("role");
			filter.add(new Filter("role", role));
		}
    	UserDAO userDAO = new UserDAO();
    	List<User> users = userDAO.find(filter);
    	results.setModel(new ListModelList<User>(users));
    }
    
    public void onClick$results() {
    	if (Util.isNotEmpty(results.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) results.getSelectedItem().getFirstChild()).getLabel());
	    	if (arg.containsKey("textbox")) {
				Textbox box = (Textbox)arg.get("textbox");
				box.setValue(String.valueOf(id));
				lookupWin.onClose();
			}
    	}
    }
}
