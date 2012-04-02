package org.ece.hms.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ece.hms.data.AppointmentVisitUsersViewDAO;
import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.DoctorPatientViewDAO;
import org.ece.hms.data.FromUserRelationshipViewDAO;
import org.ece.hms.data.RelationshipDAO;
import org.ece.hms.model.AppointmentVisitUsersView;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.DoctorPatientView;
import org.ece.hms.model.FromUserRelationshipView;
import org.ece.hms.model.Relationship;
import org.ece.hms.model.RelationshipType;
import org.ece.hms.util.DateFilter;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;

public class DoctorViewController extends GenericForwardComposer<Borderlayout> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox patientIdFilter;
	private Textbox patientNameFilter;
	private Datebox patientDateFilter;
	private Listbox patientBox, assistingDoctorsGrid, visitsGrid, appointmentGrid;
	private Grid patientVisitsGrid;
	private Button assistingDoctorButton;
	private Tabpanel visitPanel;
	
	public void onClick$visitsGrid() {
		if (Util.isNotEmpty(visitsGrid.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) visitsGrid.getSelectedItem().getFirstChild()).getLabel());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("visitId", id);
	    	map.put("grid", appointmentGrid);
	    	map.put("tab", visitPanel);
	    	map.put("tabGrid", visitsGrid);
	    	Executions.createComponents("/visit.zul", null, map);
		}
	}
	
	public void onClick$appointmentGrid() {
		if (Util.isNotEmpty(appointmentGrid.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) appointmentGrid.getSelectedItem().getFirstChild()).getLabel());
			Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("appointmentId", id);
	    	map.put("grid", appointmentGrid);
	    	map.put("tab", visitPanel);
	    	map.put("tabGrid", visitsGrid);
	    	Executions.createComponents("/visit.zul", null, map);
		}
	}
	
	public void onClick$assistingDoctorButton() {
		if (Util.isNotEmpty(patientBox.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) patientBox.getSelectedItem().getFirstChild()).getLabel());
			Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("userId", id);
	    	map.put("grid", assistingDoctorsGrid);
	    	Executions.createComponents("/assisting_doctor.zul", null, map);
		}
	}
	
	public void onClick$assistingDoctorsGrid() {
		if (Util.isNotEmpty(assistingDoctorsGrid.getSelectedItem())) {
			Messagebox.show("Are you sure to unassign this doctor?", "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					if (evt.getName().equals("onOK")) {
						int to_id = Integer.valueOf(((Listcell) patientBox.getSelectedItem().getFirstChild()).getLabel());
						int from_id = Integer.valueOf(((Listcell) assistingDoctorsGrid.getSelectedItem().getFirstChild()).getLabel());
						RelationshipDAO relationshipDAO = new RelationshipDAO();
						Relationship relationship = new Relationship();
						relationship.setFromId(from_id);
						relationship.setToId(to_id);
						relationship.setRelationshipType(RelationshipType.ASSISTING_DOCTOR);
						relationshipDAO.delete(relationship);
						
						FromUserRelationshipViewDAO fromUserRelationshipViewDAO = new FromUserRelationshipViewDAO();
				    	List<Filter> filters = new ArrayList<Filter>();
				    	filters.add(new Filter("rel_type", RelationshipType.ASSISTING_DOCTOR));
				    	filters.add(Filter.AND);
				    	filters.add(new Filter("to_id", to_id));
				    	List<FromUserRelationshipView> assistingDoctors = fromUserRelationshipViewDAO.find(filters);
				    	assistingDoctorsGrid.setModel(new ListModelList<FromUserRelationshipView>(assistingDoctors));
			        }
				}
			});
		}
	}
    
    public void onClick$patientBox() {
    	if (Util.isNotEmpty(patientBox.getSelectedItem())) {
	    	int id = Integer.valueOf(((Listcell) patientBox.getSelectedItem().getFirstChild()).getLabel());
	    	AppointmentVisitViewDAO dao = new AppointmentVisitViewDAO();
	    	List<AppointmentVisitView> visits = dao.findByPatientId(id);
	    	patientVisitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
	    	((Center)patientVisitsGrid.getParent().getParent()).setTitle("Past visits for " + ((Listcell) patientBox.getSelectedItem().getChildren().get(1)).getLabel());
	    	
	    	FromUserRelationshipViewDAO fromUserRelationshipViewDAO = new FromUserRelationshipViewDAO();
	    	List<Filter> filters = new ArrayList<Filter>();
	    	filters.add(new Filter("rel_type", RelationshipType.ASSISTING_DOCTOR));
	    	filters.add(Filter.AND);
	    	filters.add(new Filter("to_id", id));
	    	List<FromUserRelationshipView> assistingDoctors = fromUserRelationshipViewDAO.find(filters);
	    	assistingDoctorsGrid.setModel(new ListModelList<FromUserRelationshipView>(assistingDoctors));
	    	
	    	assistingDoctorButton.setVisible(true);
    	} else {
    		assistingDoctorButton.setVisible(false);
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
    	
    	AppointmentVisitUsersViewDAO appointmentVisitUsersViewDAO = new AppointmentVisitUsersViewDAO();
    	List<AppointmentVisitUsersView> appointments = appointmentVisitUsersViewDAO.findByDoctorId(UserCredentialManager.getInstance().getUser().getId());
    	appointmentGrid.setModel(new ListModelList<AppointmentVisitUsersView>(appointments));
    	
    	AppointmentVisitViewDAO avvdao = new AppointmentVisitViewDAO();
    	List<AppointmentVisitView> visits = avvdao.findByDoctorId(UserCredentialManager.getInstance().getUser().getId());
    	visitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
    }
}
