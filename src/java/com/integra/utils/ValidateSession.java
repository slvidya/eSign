/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.utils;

import com.integra.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Vidya
 */
public class ValidateSession {

    /**
     * This is a static class that will validate the user session and return
     * boolean value
     */
    private static Logger log = Logger.getLogger(ValidateSession.class);

    public static boolean validateUserSession(HttpServletRequest objRequest) {

        boolean isSuccess = false;
        HttpSession objInitSession = objRequest.getSession();
        if (objInitSession == null) {
//            System.out.println("invalid session. forward to login.jsp");
            log.info("invalid session. forward to login.jsp");
            return isSuccess;
        }
        try {
            User objUserInfo = (User) objInitSession.getAttribute("user");
            if (objUserInfo == null) {
//                System.out.println("invalid session user name not present.forward to login.jsp");
                log.info("invalid session user name not present.forward to login.jsp");
                return isSuccess;
            }
            isSuccess = true;
        } catch (Exception e) {
             log.info("[util] [validateUserSession][Exception] " + e.getMessage());
        }
//        System.out.println("[util] [validateUserSession] " + isSuccess);
        log.info("[util] [validateUserSession] " + isSuccess);
        return isSuccess;
    }
}
