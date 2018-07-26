package com.integra.dao;

import com.integra.controller.ESignController;
import com.integra.model.ActivityReport;
import com.integra.model.User;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ASPDAOImpl implements ASPDAO {
private static final Logger log = Logger.getLogger("ASP");
    @Override
    public boolean registerUser(User user) {
        SessionFactory sf = null;
        Session session = null;
        Transaction tx = null;
        boolean isSuccess = false;
        try {

            sf = HibernateUtil.getSessionFactory();
            session = sf.openSession();
            tx = session.getTransaction();
            tx.begin();
            session.save(user);
            tx.commit();
            isSuccess = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
            if (sf != null) {
                sf.close();
            }
        }
        return isSuccess;
    }

    @Override
    public List<User> loginCheck(String loginName, String password) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from User U where U.loginName=:varLoginName and U.password=:varPassword");
        query.setParameter("varLoginName", loginName);
        query.setParameter("varPassword", password);
        List<User> result = query.list();
        session.close();
        sf.close();
        return result;
    }

    @Override
    public List<User> getUserReport(String loginName, String password) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("from User U where U.loginName=:loginName and U.password=:password");
        query.setParameter("loginName", loginName);
        query.setParameter("password", password);
        List<User> result = query.list();
        session.close();
        sf.close();
        return result;
    }

    @Override
    public boolean insertAcativityReport(ActivityReport activityReport) {
        SessionFactory sf = null;
        Session session = null;
        Transaction tx = null;
        boolean isSuccess = false;
        try {

            sf = HibernateUtil.getSessionFactory();
            session = sf.openSession();
            tx = session.getTransaction();
            tx.begin();
            session.save(activityReport);
            tx.commit();
            isSuccess = true;
        } catch (Exception e) {
            if(tx!=null)
            tx.rollback();
//            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            if (sf != null) {
                sf.close();
            }
        }
        return isSuccess;
    }
}