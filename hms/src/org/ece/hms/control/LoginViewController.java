package org.ece.hms.control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.ece.hms.data.UserDAO;
import org.ece.hms.model.RoleType;
import org.ece.hms.model.User;
import org.ece.hms.util.Util;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginViewController extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox nameTxb, passwordTxb;
	private Label mesgLbl;
	private String redirectUrl;

	@Override
	public org.zkoss.zk.ui.metainfo.ComponentInfo doBeforeCompose(
			org.zkoss.zk.ui.Page page, org.zkoss.zk.ui.Component parent,
			org.zkoss.zk.ui.metainfo.ComponentInfo compInfo) {
		redirectUrl = Executions.getCurrent().getParameter("redirectUrl");
		return super.doBeforeCompose(page, parent, compInfo);
	};

	public void onOK$loginWin() {
		onClick$confirmBtn();
	}

	public void onClick$confirmBtn() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			String passwordString = new String(passwordTxb.getValue());
			md.update(passwordString.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			String hashStr = hash.toString(16);

			User user = new User();
			user.setUsername(nameTxb.getValue());
			UserDAO userDAO = new UserDAO();
			user = userDAO.findByLogin(user);

			if (Util.isNotEmpty(user) && user.getPassword().equals(hashStr)) {
				if (user.isActive()) {
					UserCredentialManager.getInstance().authenticate(user);
					if (UserCredentialManager.getInstance().isAuthenticated()) {
						if (!Util.isEmpty(redirectUrl)) {
							Executions.sendRedirect(redirectUrl);
						} else if (user.getRole().equals(RoleType.DOCTOR)) {
							Executions.sendRedirect("/doctor.zul");
						} else if (user.getRole().equals(RoleType.PATIENT)) {
							Executions.sendRedirect("/patient.zul");
						} else if (user.getRole().equals(RoleType.STAFF)) {
							Executions.sendRedirect("/staff.zul");
						} else if (user.getRole().equals(RoleType.ADMIN)) {
							Executions.sendRedirect("/admin.zul");
						} else if (user.getRole().equals(RoleType.FINANCE)) {
							Executions.sendRedirect("/finance.zul");
						}
					}
				} else {
					mesgLbl.setValue("This account has been disabled.");
				}
			} else {
				mesgLbl.setValue("Login Failed!");
				passwordTxb.setValue("");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
