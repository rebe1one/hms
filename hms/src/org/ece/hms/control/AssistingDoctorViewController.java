package org.ece.hms.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ece.hms.data.FromUserRelationshipViewDAO;
import org.ece.hms.data.RelationshipDAO;
import org.ece.hms.model.FromUserRelationshipView;
import org.ece.hms.model.Relationship;
import org.ece.hms.model.RelationshipType;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class AssistingDoctorViewController extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox doctorId;
	private Window assistingDoctorWindow;
	private Label mesgLbl;
	
	public void onClick$confirmBtn() {
		if (Util.isEmpty(doctorId.getValue())) {
			mesgLbl.setValue("Please enter a doctor id");
			return;
		}
		try {
			RelationshipDAO relationshipDAO = new RelationshipDAO();
			Relationship relationship = new Relationship();
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(new Filter("from_id", Integer.valueOf(doctorId.getValue())));
			filters.add(Filter.AND);
			filters.add(new Filter("to_id", arg.get("userId")));
			filters.add(Filter.AND);
			filters.add(new Filter("rel_type", RelationshipType.ASSISTING_DOCTOR));
			relationshipDAO.startTransaction();
			List<Relationship> rels = relationshipDAO.find(filters);
			if (rels.size() > 0) {
				mesgLbl.setValue("This doctor is already assigned to this patient.");
				relationshipDAO.commmitTransaction();
				return;
			}
			if (arg.containsKey("userId")) {
				relationship.setFromId(Integer.valueOf(doctorId.getValue()));
				relationship.setToId((Integer)arg.get("userId"));
				relationship.setRelationshipType(RelationshipType.ASSISTING_DOCTOR);
			}
			relationshipDAO.insert(relationship);
			relationshipDAO.commmitTransaction();
			
			FromUserRelationshipViewDAO fromUserRelationshipViewDAO = new FromUserRelationshipViewDAO();
	    	filters = new ArrayList<Filter>();
	    	filters.add(new Filter("rel_type", RelationshipType.ASSISTING_DOCTOR));
	    	filters.add(Filter.AND);
	    	filters.add(new Filter("to_id", (Integer)arg.get("userId")));
	    	List<FromUserRelationshipView> assistingDoctors = fromUserRelationshipViewDAO.find(filters);
	    	((Listbox)arg.get("grid")).setModel(new ListModelList<FromUserRelationshipView>(assistingDoctors));
			assistingDoctorWindow.onClose();
		} catch (WrongValueException e) {
			mesgLbl.setValue("Please fix the errors listed above");
			return;
		}
	}
	
	public void onClick$lookupDoctorButton() {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("role", "DOCTOR");
    	map.put("textbox", doctorId);
    	Executions.createComponents("modal_lookup.zul", null, map);
    }
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
	}
}
