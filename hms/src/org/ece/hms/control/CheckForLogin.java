package org.ece.hms.control;

import java.util.Map;

import org.ece.hms.model.RoleType;
import org.ece.hms.model.User;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

public class CheckForLogin implements Initiator {
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		if (!UserCredentialManager.getInstance().isAuthenticated()) {
			Executions.sendRedirect("index.zul?" + "redirectUrl" + "="
					+ args.get("arg0"));
		} else {
			User user = UserCredentialManager.getInstance().getUser();

			if (args.get("arg0").equals("/doctor.zul")
					&& !user.getRole().equals(RoleType.DOCTOR)) {
				UserCredentialManager.getInstance().logout();
			} else if (args.get("arg0").equals("/patient.zul")
					&& !user.getRole().equals(RoleType.PATIENT)) {
				UserCredentialManager.getInstance().logout();
			} else if (args.get("arg0").equals("/staff.zul")
					&& !user.getRole().equals(RoleType.STAFF)) {
				UserCredentialManager.getInstance().logout();
			} else if (args.get("arg0").equals("/finance.zul")
					&& !user.getRole().equals(RoleType.FINANCE)) {
				UserCredentialManager.getInstance().logout();
			} else if (args.get("arg0").equals("/admin.zul")
					&& !user.getRole().equals(RoleType.ADMIN)) {
				UserCredentialManager.getInstance().logout();
			}
		}
	}
}
