package org.ece.hms.control;

import java.util.HashMap;
import java.util.List;

import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.PatientUserView;
import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Center;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class StaffController extends GenericForwardComposer<Window> {
	private Listbox patientBox;
	private Grid patientVisitsGrid;
	private Button newPatientBtn;
	
    public void onClick$patientBox() {
    	int id = Integer.valueOf(((Listcell) patientBox.getSelectedItem().getFirstChild()).getLabel());
    	System.out.println(id);
    	AppointmentVisitViewDAO dao = new AppointmentVisitViewDAO();
    	List<AppointmentVisitView> visits = dao.findByPatientId(id);
    	patientVisitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
    	((Center)patientVisitsGrid.getParent()).setTitle("Past visits for " + ((Listcell) patientBox.getSelectedItem().getChildren().get(1)).getLabel());
    }
    
    public void onClick$newPatientBtn() {
    	Executions.createComponents("new_patient.zul", null, null);
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
