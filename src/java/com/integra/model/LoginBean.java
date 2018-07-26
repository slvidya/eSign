/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Vidya
 */
@Entity
@Table(name="user_master")
public class LoginBean {
    @Id
    private int userId;
    private String loginName;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
}
