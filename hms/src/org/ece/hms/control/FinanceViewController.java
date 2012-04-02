package org.ece.hms.control;

import java.util.ArrayList;
import java.util.List;

import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.DoctorPatientViewDAO;
import org.ece.hms.data.UserDAO;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.DoctorPatientView;
import org.ece.hms.model.RoleType;
import org.ece.hms.model.User;
import org.ece.hms.util.DateFilter;
import org.ece.hms.util.Filter;
import org.ece.hms.util.OrderFilter;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;

public class FinanceViewController extends GenericForwardComposer<Borderlayout> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Listbox doctorBox;
	private Grid visitsGrid;
	private Textbox filterUserId, filterVisitsDiagnosis, filterVisitsPrescription, filterVisitsComments;
	private Datebox filterFromDate, filterToDate;
    
    public void onClick$doctorBox() {
    	if (Util.isNotEmpty(doctorBox.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) doctorBox.getSelectedItem().getFirstChild()).getLabel());
	    	AppointmentVisitViewDAO avvdao = new AppointmentVisitViewDAO();
	    	List<AppointmentVisitView> visits = avvdao.findByDoctorId(id);
	    	visitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
	    	filterVisits();
    	}
    }
    
    private void filterVisits() {
    	List<Filter> filter = new ArrayList<Filter>();
    	int id = Integer.valueOf(((Listcell) doctorBox.getSelectedItem().getFirstChild()).getLabel());
    	filter.add(new Filter("doctor_id", id));
    	
    	if (Util.isNotEmpty(filterUserId.getValue())) {
    		filter.add(Filter.AND);
    		filter.add(new Filter("patient_id", Integer.valueOf(filterUserId.getValue())));
    	}
    	if (Util.isNotEmpty(filterFromDate.getValue()) && Util.isNotEmpty(filterToDate.getValue())) {
    		filter.add(Filter.AND);
    		filter.add(new DateFilter("timestamp", filterFromDate.getValue(), filterToDate.getValue()));
    	}
    	if (Util.isNotEmpty(filterVisitsDiagnosis.getValue())) {
    		filter.add(Filter.AND);
    		filter.add(new Filter("diagnosis", filterVisitsDiagnosis.getValue()));
    	}
    	if (Util.isNotEmpty(filterVisitsPrescription.getValue())) {
    		filter.add(Filter.AND);
    		filter.add(new Filter("prescription", filterVisitsPrescription.getValue()));
    	}
    	if (Util.isNotEmpty(filterVisitsComments.getValue())) {
    		filter.add(Filter.AND);
    		filter.add(new Filter("comments", filterVisitsComments.getValue()));
    	}
    	filter.add(new OrderFilter("timestamp", OrderFilter.DESC));
    	if (Util.isNotEmpty(doctorBox.getSelectedItem())) {
	    	AppointmentVisitViewDAO avvdao = new AppointmentVisitViewDAO();
	    	List<AppointmentVisitView> visits = avvdao.find(filter);
	    	visitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
    	}
    }
    
    public void onChanging$filterUserId(InputEvent event) {
    	filterUserId.setValue(event.getValue());
    	filterVisits();
    }
    
    public void onChanging$filterVisitsDiagnosis(InputEvent event) {
		filterVisitsDiagnosis.setValue(event.getValue());
    	filterVisits();
    }
	
	public void onChanging$filterVisitsPrescription(InputEvent event) {
		filterVisitsPrescription.setValue(event.getValue());
    	filterVisits();
    }
	
	public void onChanging$filterVisitsComments(InputEvent event) {
		filterVisitsComments.setValue(event.getValue());
    	filterVisits();
    }
    
    public void onChange$filterFromDate() {
    	filterVisits();
    }
    
    public void onChange$filterToDate() {
    	filterVisits();
    }
    
    @Override
    public void doAfterCompose(Borderlayout comp) throws Exception {
    	super.doAfterCompose(comp);
    	UserDAO dao = new UserDAO();
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(new Filter("role", RoleType.DOCTOR));
    	List<User> doctors = dao.find(filters);
    	doctorBox.setModel(new ListModelList<User>(doctors));
    }
}
