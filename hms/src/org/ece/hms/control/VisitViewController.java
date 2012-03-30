package org.ece.hms.control;

import java.sql.Timestamp;
import java.util.Date;

import org.ece.hms.data.VisitDAO;
import org.ece.hms.model.Visit;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.rms.collector.util.Util;

public class VisitViewController extends SelectorComposer<Window> {
	@Wire
    private Textbox appointmentId, length, diagnosis, prescription, scheduling, comments;
	
    @Wire
    private Label mesgLbl;
    
    @Listen("onClick=#confirmBtn")
    public void createVisit() {
    	boolean valid = true;
        if (Util.isEmpty(appointmentId.getValue())) {
        	appointmentId.setErrorMessage("Required Field.");
        	valid = false;
        }
        if (Util.isEmpty(length.getValue())) {
        	length.setErrorMessage("Required Field.");
        	valid = false;
        }
        if (Util.isEmpty(diagnosis.getValue())) {
        	diagnosis.setErrorMessage("Required Field.");
        	valid = false;
        }
        if (Util.isEmpty(prescription.getValue())) {
        	prescription.setErrorMessage("Required Field.");
        	valid = false;
        }
        if (Util.isEmpty(scheduling.getValue())) {
        	scheduling.setErrorMessage("Required Field.");
        	valid = false;
        }
        if (Util.isEmpty(comments.getValue())) {
        	comments.setErrorMessage("Required Field.");
        	valid = false;
        }
        if (valid) {
        	Visit visit = new Visit();
        	visit.setAppointmentId(Integer.valueOf(appointmentId.getValue()));
        	visit.setLength(Integer.valueOf(length.getValue()));
        	visit.setDiagnosis(diagnosis.getValue());
        	visit.setPrescription(prescription.getValue());
        	visit.setScheduling(scheduling.getValue());
        	visit.setComments(comments.getValue());
        	visit.setTimestamp(new Timestamp(new Date().getTime()));
        	visit.setCreatedBy(UserCredentialManager.getIntance().getUser().getId());
        	VisitDAO visitDAO = new VisitDAO();
        	visitDAO.insert(visit);
        	mesgLbl.setValue("Success!");
        }
    }
    
}
