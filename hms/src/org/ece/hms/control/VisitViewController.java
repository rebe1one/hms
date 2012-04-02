package org.ece.hms.control;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ece.hms.data.AppointmentVisitUsersViewDAO;
import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.VisitDAO;
import org.ece.hms.model.AppointmentVisitUsersView;
import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.Visit;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class VisitViewController extends GenericForwardComposer<Window> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox length, diagnosis, prescription, scheduling, comments;
    private Label mesgLbl;
    private Window visitWin;
    
    public void onClick$confirmBtn() {
    	boolean valid = true;
    	int id = 0;
    	if (arg.containsKey("appointmentId")) {
    		id = (Integer)arg.get("appointmentId");
    	} else if (arg.containsKey("visitId")) {
    		id = (Integer)arg.get("visitId");
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
        	VisitDAO visitDAO = new VisitDAO();
        	if (arg.containsKey("visitId")) {
	        	List<Filter> filters = new ArrayList<Filter>();
	        	filters.add(new Filter("id", id));
	        	List<Visit> existingVisits = visitDAO.find(filters);
	        	visit = existingVisits.get(0);
	        	visit.setId(0);
        	}
        	if (arg.containsKey("appointmentId")) visit.setAppointmentId(id);
        	visit.setLength(Integer.valueOf(length.getValue()));
        	visit.setDiagnosis(diagnosis.getValue());
        	visit.setPrescription(prescription.getValue());
        	visit.setScheduling(scheduling.getValue());
        	visit.setComments(comments.getValue());
        	visit.setTimestamp(new Timestamp(new Date().getTime()));
        	visit.setCreatedBy(UserCredentialManager.getInstance().getUser().getId());
        	visitDAO.insert(visit);
        	Listbox appointmentGrid = (Listbox)arg.get("grid");
        	Tabpanel tabPanel = (Tabpanel)arg.get("tab");
        	Listbox visitsGrid = (Listbox)arg.get("tabGrid");
        	
        	AppointmentVisitUsersViewDAO appointmentVisitUsersViewDAO = new AppointmentVisitUsersViewDAO();
        	List<AppointmentVisitUsersView> appointments = appointmentVisitUsersViewDAO.findByDoctorId(UserCredentialManager.getInstance().getUser().getId());
        	appointmentGrid.setModel(new ListModelList<AppointmentVisitUsersView>(appointments));
        	
        	AppointmentVisitViewDAO avvdao = new AppointmentVisitViewDAO();
        	List<AppointmentVisitView> visits = avvdao.findByDoctorId(UserCredentialManager.getInstance().getUser().getId());
        	visitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
        	
        	if (arg.containsKey("controller")) {
        		((DoctorViewController)arg.get("controller")).filterVisits();
        	}
        	
        	visitWin.onClose();
        	Tabbox tabbox = (Tabbox)tabPanel.getParent().getParent();
        	tabbox.setSelectedIndex(1);
        	mesgLbl.setValue("Success!");
        }
    }
    @Override
    public void doAfterCompose(Window comp) throws Exception {
    	super.doAfterCompose(comp);
    	if (arg.containsKey("visitId")) {
    		int id = (Integer)arg.get("visitId");
    		VisitDAO visitDAO = new VisitDAO();
        	List<Filter> filters = new ArrayList<Filter>();
        	filters.add(new Filter("id", id));
        	List<Visit> existingVisits = visitDAO.find(filters);
        	Visit visit = existingVisits.get(0);
        	length.setValue(String.valueOf(visit.getLength()));
        	diagnosis.setValue(visit.getDiagnosis());
        	prescription.setValue(visit.getPrescription());
        	scheduling.setValue(visit.getScheduling());
        	comments.setValue(visit.getComments());
    	}
    }
}
