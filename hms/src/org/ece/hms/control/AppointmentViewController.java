package org.ece.hms.control;

import java.util.Date;
import java.util.HashMap;

import org.ece.hms.data.AppointmentDAO;
import org.ece.hms.model.Appointment;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;


public class AppointmentViewController extends SelectorComposer<Window> {
	@Wire
    private Textbox doctorId, patientId;
	
	@Wire
	private Datebox date;
	
	@Wire
	private Timebox time;
	
    @Wire
    private Label mesgLbl;
    
    @Override
	public void doAfterCompose(Window window) throws Exception {
		super.doAfterCompose(window);
    }
    
    @Listen("onClick=#lookupPatientButton")
    public void modalPatientLookup() {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("type", "PATIENT");
    	map.put("textbox", patientId);
    	Executions.createComponents("modal_lookup.zul", null, map);
    }
    
    @Listen("onClick=#lookupDoctorButton")
    public void modalDoctorLookup() {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("type", "DOCTOR");
    	map.put("textbox", doctorId);
    	Executions.createComponents("modal_lookup.zul", null, map);
    }
    
    @Listen("onClick=#confirmBtn")
    public void createAppointment() {
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
        	appt.setDate(date);
        	AppointmentDAO apptDAO = new AppointmentDAO();
        	apptDAO.insert(appt);
        }
    }
    
}
