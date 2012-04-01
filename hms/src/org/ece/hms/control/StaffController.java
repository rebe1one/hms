package org.ece.hms.control;

import java.util.HashMap;
import java.util.List;

import org.ece.hms.data.AppointmentVisitUsersViewDAO;
import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.PatientUserViewDAO;
import org.ece.hms.data.StaffPatientViewDAO;
import org.ece.hms.model.AppointmentVisitUsersView;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.DoctorPatientView;
import org.ece.hms.model.PatientUserView;
import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Row;

public class StaffController extends GenericForwardComposer<Borderlayout> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Listbox patientBox;
	private Grid patientVisitsGrid, unassignedPatientsGrid, appointmentGrid;
	
    public void onClick$patientBox() {
    	int id = Integer.valueOf(((Listcell) patientBox.getSelectedItem().getFirstChild()).getLabel());
    	System.out.println(id);
    	AppointmentVisitViewDAO dao = new AppointmentVisitViewDAO();
    	List<AppointmentVisitView> visits = dao.findByPatientId(id);
    	patientVisitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
    	((Center)patientVisitsGrid.getParent()).setTitle("Past visits for " + ((Listcell) patientBox.getSelectedItem().getChildren().get(1)).getLabel());
    }
    
    @Override
    public void doAfterCompose(Borderlayout comp) throws Exception {
    	super.doAfterCompose(comp);
    	PatientUserViewDAO patientUserViewDAO = new PatientUserViewDAO();
    	List<PatientUserView> unassignedPatients = patientUserViewDAO.findUnassigned();
    	unassignedPatientsGrid.setModel(new ListModelList<PatientUserView>(unassignedPatients));
    	
    	StaffPatientViewDAO staffPatientViewDAO = new StaffPatientViewDAO();
    	List<DoctorPatientView> patients = staffPatientViewDAO.findByStaffId(UserCredentialManager.getInstance().getUser().getId());
    	patientBox.setModel(new ListModelList<DoctorPatientView>(patients));
    	
    	AppointmentVisitUsersViewDAO appointmentVisitUsersViewDAO = new AppointmentVisitUsersViewDAO();
    	List<AppointmentVisitUsersView> appointments = appointmentVisitUsersViewDAO.findAll();
    	appointmentGrid.setModel(new ListModelList<AppointmentVisitUsersView>(appointments));
    }
    
    public void onClick$newPatientBtn() {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("grid", unassignedPatientsGrid);
    	Executions.createComponents("new_patient.zul", null, map);
    }
    
    public void onClick$newAppointmentBtn() {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("grid", appointmentGrid);
    	Executions.createComponents("appointment.zul", null, map);
    }

	public void onAssignUnassignedPatient(Event event) {
		PatientUserView patient = getSelectedPatient(event);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("patient", patient);
		map.put("type", "DOCTOR");
		Executions.createComponents("modal_lookup.zul", null, map);
	}

	private PatientUserView getSelectedPatient(Event event) {

		if (event instanceof ForwardEvent) {
			ForwardEvent fwevent = (ForwardEvent) event;
			Event eventOrigin = fwevent.getOrigin();
			Row row = (Row) eventOrigin.getTarget().getParent();

			PatientUserView selectedPatient = (PatientUserView) row.getValue();
			return selectedPatient;
		}

		return null;
	}

}
