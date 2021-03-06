package org.ece.hms.control;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ece.hms.data.AppointmentVisitUsersViewDAO;
import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.DoctorPatientViewDAO;
import org.ece.hms.data.PatientUserViewDAO;
import org.ece.hms.data.UserDAO;
import org.ece.hms.model.AppointmentVisitUsersView;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.DoctorPatientView;
import org.ece.hms.model.PatientUserView;
import org.ece.hms.model.RoleType;
import org.ece.hms.model.User;
import org.ece.hms.util.DateFilter;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelExt;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.event.ListDataListener;

public class AdminViewController extends GenericForwardComposer<Borderlayout> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox patientIdFilter;
	private Textbox patientNameFilter;
	private Datebox patientDateFilter;
	private Listbox userBox, patientBox;
    
    public void onClick$userBox() {
    	if (Util.isNotEmpty(userBox.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) userBox.getSelectedItem().getFirstChild()).getLabel());
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("id", id);
	    	map.put("grid", userBox);
	    	Executions.createComponents("/edit_user.zul", null, map);
    	}
    }
    
    public void onClick$newAdmin() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("role", RoleType.ADMIN);
    	map.put("title", "New Admin");
    	map.put("grid", userBox);
    	Executions.createComponents("/edit_user.zul", null, map);
    }
    
    public void onClick$newStaff() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("role", RoleType.STAFF);
    	map.put("title", "New Staff");
    	map.put("grid", userBox);
    	Executions.createComponents("/edit_user.zul", null, map);
    }
    
    public void onClick$newFinance() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("role", RoleType.FINANCE);
    	map.put("title", "New Finance");
    	map.put("grid", userBox);
    	Executions.createComponents("/edit_user.zul", null, map);
    }
    
    public void onClick$newDoctor() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("role", RoleType.DOCTOR);
    	map.put("title", "New Doctor");
    	map.put("grid", userBox);
    	Executions.createComponents("/edit_user.zul", null, map);
    }
    
    public void onChanging$userIdFilter(InputEvent event) {
    	List<Filter> filter = new ArrayList<Filter>();
    	String prevValue = patientIdFilter.getValue();
    	if (Util.isNotEmpty(event.getValue())) {
    		filter.add(new Filter("patient_id", Integer.valueOf(event.getValue())));
    	}
    	if (Util.isNotEmpty(patientNameFilter.getValue())) {
    		if (filter.size() > 0) filter.add(Filter.AND);
    		String[] split = patientNameFilter.getValue().split(" ");
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
    	}
    	if (Util.isNotEmpty(patientDateFilter.getValue())) {
    		if (filter.size() > 0) filter.add(Filter.AND);
    		filter.add(new DateFilter("timestamp", patientDateFilter.getValue()));
    	}
    	patientIdFilter.setValue(event.getValue());
    	if (!prevValue.equals(event.getValue())) {
	    	UserDAO dao = new UserDAO();
	    	List<User> users = dao.findAll();
	    	userBox.setModel(new ListModelList<User>(users));
    	}
    }
    
    @Override
    public void doAfterCompose(Borderlayout comp) throws Exception {
    	super.doAfterCompose(comp);
    	UserDAO dao = new UserDAO();
    	List<User> users = dao.findAll();
    	userBox.setModel(new ListModelList<User>(users));
    	
    	PatientUserViewDAO puvDAO = new PatientUserViewDAO();
    	List<PatientUserView> puvList = puvDAO.findAll();
    	patientBox.setModel(new ListModelList<PatientUserView>(puvList));
    }
}
