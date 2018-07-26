/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.controller;

import com.inetgra.auth.AuthConstants;
import com.integra.dao.ASPDAO;
import com.integra.dao.ASPDAOImpl;
import com.integra.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.util.Base64;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.json.JSONObject;

/**
 *
 * @author Vidya
 */
public class RegisterController extends DispatchAction {

    private final static String SUCCESS = "success";

    public ActionForward register(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardTo = "failure";
        String tbFName = request.getParameter("tbFName");
        String tbMName = request.getParameter("tbMName");
        String tbLName = request.getParameter("tbLName");
        String tbAdharName = request.getParameter("tbAdharName");
        String slGender = request.getParameter("slGender");
        String tbDOB = request.getParameter("tbDOB");
        String tbEmail = request.getParameter("tbEmail");
        String tbMobileNo = request.getParameter("tbMobileNo");
        String tbAadhaar = request.getParameter("tbAadhaar");
        String tbLoginName = request.getParameter("tbLoginName");
        String tbPwd = request.getParameter("tbPwd");
        tbPwd = Base64.encode(tbPwd.getBytes());
        String tbCnfPwd = request.getParameter("tbCnfPwd");

        User user = new User();
        user.setFirstName(tbFName);
        if (tbMName != null) {
            user.setMiddleName(tbMName);
        }
        user.setLastName(tbLName);
        user.setAadhaarName(tbAdharName);
        user.setGender(slGender);
        user.setDob(tbDOB);
        user.setEmailId(tbEmail);
        user.setMobileNumber(tbMobileNo);
        user.setAadhaarNumber(tbAadhaar);
        user.setLoginName(tbLoginName);
        user.setPassword(tbPwd);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", user.getAadhaarNumber());
        jsonObject.put("name", user.getAadhaarName());
        jsonObject.put("nameMatchStrategy", AuthConstants.nameMatchStrategy);
        jsonObject.put("nameMatchValue", AuthConstants.nameMatchValue);
        jsonObject.put("localNameMatchValue", AuthConstants.localNameMatchValue);
        jsonObject.put("gender", user.getGender());
        jsonObject.put("dob", user.getDob());
        jsonObject.put("phoneNo", user.getMobileNumber());
        jsonObject.put("pinCode", AuthConstants.pincode);
        jsonObject.put("addressMatchStrategy", AuthConstants.addressMatchStrategy);
        jsonObject.put("fullAddressMatchStrategy", AuthConstants.fullAddressMatchStrategy);
        jsonObject.put("fullAddressMatchValue", AuthConstants.fullAddressMatchValue);
        jsonObject.put("localFullAddressMatchValue", AuthConstants.localFullAddressMatchValue);

        JSONObject deviceMetaData = new JSONObject();
        deviceMetaData.put("dpId", AuthConstants.dpId);
        deviceMetaData.put("rdsId", AuthConstants.rdsId);
        deviceMetaData.put("rdsVer", AuthConstants.rdsVer);
        deviceMetaData.put("dc", AuthConstants.dc);
        deviceMetaData.put("mi", AuthConstants.mi);
        deviceMetaData.put("mc", AuthConstants.mc);
        jsonObject.put("deviceMetaData", deviceMetaData);

        JSONObject uses = new JSONObject();
        uses.put("pi", AuthConstants.pi);
        uses.put("pa", AuthConstants.pa);
        uses.put("pfa", AuthConstants.pfa);
        jsonObject.put("uses", uses);
        jsonObject.put("auaCode", AuthConstants.auaCode);
        jsonObject.put("subAuaCode", AuthConstants.subAuaCode);
        jsonObject.put("merchantCategoryCode", AuthConstants.merchantCategoryCode);
        jsonObject.put("posEntryMode", AuthConstants.posEntryMode);
        jsonObject.put("posConditionCode", AuthConstants.posConditionCode);
        jsonObject.put("cardAcptIdCode", AuthConstants.cardAcptIdCode);
        jsonObject.put("cardAcptNameLoc", AuthConstants.cardAcptNameLoc);
        jsonObject.put("communicationId", AuthConstants.communicationId);
        jsonObject.put("rc", AuthConstants.rc);

        System.out.println("jsonObject " + jsonObject.toString());

        AuthController authController = new AuthController();
        String authResponse = authController.authenticateDemo(jsonObject.toString());

        JSONObject authResponseObj = new JSONObject(authResponse);
        if (authResponseObj.get("ret").toString().equalsIgnoreCase("Y")) {
            ASPDAO aspdao = new ASPDAOImpl();
            boolean isAdded = aspdao.registerUser(user);
            if (isAdded) {
                forwardTo = SUCCESS;
            } else {
                request.setAttribute("error", "User Already Exists");
            }
        } else {
            request.setAttribute("error", "Demo Auth Failed");
        }

        return mapping.findForward(forwardTo);
    }
}