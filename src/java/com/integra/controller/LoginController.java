/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.controller;

import com.integra.dao.ASPDAO;
import com.integra.dao.ASPDAOImpl;
import com.integra.model.User;
import com.integra.utils.ValidateSession;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.util.Base64;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author Vidya
 */
public class LoginController extends DispatchAction {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward login(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession objHttpSession = request.getSession();
        objHttpSession.setMaxInactiveInterval(300);
        User userSessionObj = null;
        String forwardTo = "failure";
        String tbLoginId = request.getParameter("tbLoginId");
        String tbLoginPwd = request.getParameter("tbLoginPwd");
        if (tbLoginId == null || tbLoginId.trim() == "" || tbLoginPwd == null || tbLoginPwd.trim() == "") {
            request.setAttribute(ERROR, "User Name/Password cannot be empty");
            return mapping.findForward(forwardTo);
        }

        tbLoginPwd = Base64.encode(tbLoginPwd.getBytes());
        ASPDAO aspdao = new ASPDAOImpl();
        List<User> userList = aspdao.loginCheck(tbLoginId, tbLoginPwd);
        if (!userList.isEmpty()) {
            for (User user : userList) {
                System.out.println("user : " + user.getLoginName());
                
                if (user.getLoginName().equalsIgnoreCase("admin")) {
                    forwardTo = "adminhome";
                } else {
                    forwardTo = "home";
                }
                userSessionObj = user;
            }
            objHttpSession.setAttribute("user", userSessionObj);
        } else {
            request.setAttribute(ERROR, "Invalid User Name/Password");
        }
        return mapping.findForward(forwardTo);

    }

    public ActionForward signOut(ActionMapping objActionMapping, ActionForm objActionForm, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        log.info("[signOut]");
        String strForward = "login";
        try {
            if (!ValidateSession.validateUserSession(req)) {
                ActionForward af = new ActionForward("/login.do?method=login", true);
                return af;
            }
            HttpSession objHS = req.getSession();
            objHS.removeAttribute("downloadFileName");
            objHS.removeAttribute("sap");
            objHS.removeAttribute("baos");
            objHS.removeAttribute("txnrefno");
            objHS.removeAttribute("esignStatus");
            objHS.removeAttribute("failureReason");
            objHS.removeAttribute("user");
            objHS.removeAttribute(ERROR);

            req.getSession().invalidate();
        } catch (Exception e) {
            log.error("Error while invalidating session");
        }
        return objActionMapping.findForward(strForward);
    }
}
