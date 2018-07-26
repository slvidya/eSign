/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.dao;

import com.integra.model.ActivityReport;
import com.integra.model.LoginBean;
import com.integra.model.User;
import java.util.List;

/**
 *
 * @author Vidya
 */
public interface ASPDAO {
public boolean registerUser(User user);
public List<User> loginCheck(String loginName,String password);
public List<User> getUserReport(String loginName,String password);
public boolean insertAcativityReport(ActivityReport activityReport);

}
