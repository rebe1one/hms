package org.ece.hms.control;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.ece.hms.data.AppointmentDAO;
import org.ece.hms.data.AppointmentVisitUsersViewDAO;
import org.ece.hms.model.Appointment;
import org.ece.hms.model.AppointmentVisitUsersView;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

public class AppointmentViewController extends GenericForwardComposer<Window> {
	private Textbox doctorId, patientId, length;
	private Datebox date;
	private Timebox time;
	private Label mesgLbl;
	private Window appointmentWin;

	@Override
	public void doAfterCompose(Window window) throws Exception {
		super.doAfterCompose(window);
		if (arg.containsKey("appointment")) {
			AppointmentVisitUsersView appointment = (AppointmentVisitUsersView) arg
					.get("appointment");
			doctorId.setValue(Integer.toString(appointment.getDoctorId()));
			patientId.setValue(Integer.toString(appointment.getPatientId()));
			length.setValue(Integer.toString(appointment.getAppointmentLength()));
			date.setValue(appointment.getDate());
			time.setValue(appointment.getDate());
		}
	}

	public void onClick$lookupPatientButton() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role", "PATIENT");
		map.put("textbox", patientId);
		Executions.createComponents("modal_lookup.zul", null, map);
	}

	public void onClick$lookupDoctorButton() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role", "DOCTOR");
		map.put("textbox", doctorId);
		Executions.createComponents("modal_lookup.zul", null, map);
	}

	@Listen("onClick=#confirmBtn")
	public void onClick$confirmBtn() {
		boolean valid = true;
		if (Util.isEmpty(patientId.getValue())) {
			patientId.setErrorMessage("Required Field.");
			valid = false;
		}
		if (Util.isEmpty(doctorId.getValue())) {
			doctorId.setErrorMessage("Required Field.");
			valid = false;
		}
		if (Util.isEmpty(date.getValue())) {
			date.setErrorMessage("Required Field.");
			valid = false;
		}
		if (Util.isEmpty(time.getValue())) {
			time.setErrorMessage("Required Field.");
			valid = false;
		}
		if (valid) {
			Appointment appt = new Appointment();
			appt.setDoctorId(Integer.valueOf(doctorId.getValue()));
			appt.setPatientId(Integer.valueOf(patientId.getValue()));
			Date date = this.date.getValue();
			date.setHours(time.getValue().getHours());
			date.setMinutes(time.getValue().getMinutes());
			appt.setDate(new Timestamp(date.getTime()));
			appt.setLength(Integer.valueOf(length.getValue()));
			AppointmentDAO apptDAO = new AppointmentDAO();

			if (arg.containsKey("appointment")) {
				AppointmentVisitUsersView appointment = (AppointmentVisitUsersView) arg
						.get("appointment");
				appt.setId(appointment.getAppointmentId());
				apptDAO.update(appt);
			} else {
				apptDAO.insert(appt);
			}

			if (arg.containsKey("grid")) {
				Grid appointmentGrid = (Grid) arg.get("grid");
				AppointmentVisitUsersViewDAO appointmentVisitUsersViewDAO = new AppointmentVisitUsersViewDAO();
				List<AppointmentVisitUsersView> appointments = appointmentVisitUsersViewDAO
						.findAll();
				appointmentGrid
						.setModel(new ListModelList<AppointmentVisitUsersView>(
								appointments));
			}
			appointmentWin.onClose();
		}
	}

}
