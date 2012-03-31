package org.ece.hms.control;

import java.util.HashMap;

import org.ece.hms.model.PatientUserView;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class StaffController extends GenericForwardComposer<Window> {

	private static final long serialVersionUID = 1L;

	public void onAssignUnassignedPatient(Event event) {
		PatientUserView patient = getSelectedPatient(event);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("patient", patient);
		map.put("type", "DOCTOR");
		//map.put("textbox", doctorId);
		Executions.createComponents("modal_assign_doctor.zul", null, map);
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
