package org.ece.hms.control;

import java.util.List;

import org.ece.hms.data.AppointmentVisitViewDAO;
import org.ece.hms.data.DoctorPatientViewDAO;
import org.ece.hms.model.AppointmentVisitView;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Textbox;

public class DoctorViewController extends GenericForwardComposer<Borderlayout> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox patientIdFilter;
	private Textbox patientNameFilter;
	private Textbox patientDateFilter;
	private Listbox patientBox;
	private Grid patientVisitsGrid;
    
    public void onClick$patientBox() {
    	int id = Integer.valueOf(((Listcell) patientBox.getSelectedItem().getFirstChild()).getLabel());
    	AppointmentVisitViewDAO dao = new AppointmentVisitViewDAO();
    	List<AppointmentVisitView> visits = dao.findByPatientId(id);
    	patientVisitsGrid.setModel(new ListModelList<AppointmentVisitView>(visits));
    	((Center)patientVisitsGrid.getParent()).setTitle("Past visits for " + ((Listcell) patientBox.getSelectedItem().getChildren().get(1)).getLabel());
    }
    
    public void onChanging$patientIdFilter() {
    	DoctorPatientViewDAO dao = new DoctorPatientViewDAO();
    	List patients = dao.findByDoctorId(UserCredentialManager.getInstance().getUser().getId());
    }
}
