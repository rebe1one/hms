package org.ece.hms.control;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.DoctorPatientViewDAO;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.DoctorPatientView;
import org.ece.hms.util.DateFilter;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;
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

public class DoctorViewController extends GenericForwardComposer<Borderlayout> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox patientIdFilter;
	private Textbox patientNameFilter;
	private Datebox patientDateFilter;
	private Listbox patientBox;
	private Grid patientVisitsGrid;
    
    public void onClick$patientBox() {
    	if (Util.isNotEmpty(patientBox.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) patientBox.getSelectedItem().getFirstChild()).getLabel());
	    	AppointmentVisitViewDAO dao = new AppointmentVisitViewDAO();
	    	List<AppointmentVisitView> visits = dao.findByPatientId(id);
	    	patientVisitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
	    	((Center)patientVisitsGrid.getParent()).setTitle("Past visits for " + ((Listcell) patientBox.getSelectedItem().getChildren().get(1)).getLabel());
    	}
    }
    
    public void onChanging$patientIdFilter(InputEvent event) {
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
	    	DoctorPatientViewDAO dao = new DoctorPatientViewDAO();
	    	List<DoctorPatientView> patients = dao.findByDoctorId(UserCredentialManager.getInstance().getUser().getId(), filter);
	    	patientBox.setModel(new ListModelList<DoctorPatientView>(patients));
    	}
    }
    
    public void onChanging$patientNameFilter(InputEvent event) {
    	List<Filter> filter = new ArrayList<Filter>();
    	String prevValue = patientNameFilter.getValue();
    	if (Util.isNotEmpty(patientIdFilter.getValue()))
    		filter.add(new Filter("patient_id", Integer.valueOf(patientIdFilter.getValue())));
    	if (Util.isNotEmpty(event.getValue())) {
    		if (filter.size() > 0) filter.add(Filter.AND);
    		String[] split = event.getValue().split(" ");
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
    	patientNameFilter.setValue(event.getValue());
    	if (!prevValue.equals(event.getValue())) {
	    	DoctorPatientViewDAO dao = new DoctorPatientViewDAO();
	    	List<DoctorPatientView> patients = dao.findByDoctorId(UserCredentialManager.getInstance().getUser().getId(), filter);
	    	patientBox.setModel(new ListModelList<DoctorPatientView>(patients));
    	}
    }
    
    public void onChange$patientDateFilter(InputEvent event) {
    	List<Filter> filter = new ArrayList<Filter>();
    	if (Util.isNotEmpty(patientIdFilter.getValue()))
    		filter.add(new Filter("patient_id", Integer.valueOf(patientIdFilter.getValue())));
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
    	DoctorPatientViewDAO dao = new DoctorPatientViewDAO();
    	List<DoctorPatientView> patients = dao.findByDoctorId(UserCredentialManager.getInstance().getUser().getId(), filter);
    	patientBox.setModel(new ListModelList<DoctorPatientView>(patients));
    }
    
    @Override
    public void doAfterCompose(Borderlayout comp) throws Exception {
    	super.doAfterCompose(comp);
    	DoctorPatientViewDAO dao = new DoctorPatientViewDAO();
    	List<DoctorPatientView> patients = dao.findByDoctorId(UserCredentialManager.getInstance().getUser().getId());
    	patientBox.setModel(new ListModelList<DoctorPatientView>(patients));
    }
}
