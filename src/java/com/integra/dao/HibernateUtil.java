/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.dao;

import com.integra.utils.ConfigListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Vidya
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
//                sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            Configuration cfg = new AnnotationConfiguration().configure("hibernate.cfg.xml");
            cfg.getProperties().setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            cfg.getProperties().setProperty("hibernate.connection.url", ConfigListener.aspConf.getAsp_databse_url());
            cfg.getProperties().setProperty("hibernate.connection.username", ConfigListener.aspConf.getAsp_databse_username());
            cfg.getProperties().setProperty("hibernate.connection.password", ConfigListener.aspConf.getAsp_databse_password());
            cfg.getProperties().setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            sessionFactory = cfg.buildSessionFactory();

        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
