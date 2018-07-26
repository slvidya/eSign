/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Vidya
 */
@Entity
@Table(name = "activity_report")
public class ActivityReport {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "transaction_refno")
    private String transactionRefNo;
    
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    
    @Column(name = "esign_status")
    private String esignStatus;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "is_downloaded")
    private String isDownloaded;

    public String getTransactionRefNo() {
        return transactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
        this.transactionRefNo = transactionRefNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getEsignStatus() {
        return esignStatus;
    }

    public void setEsignStatus(String esignStatus) {
        this.esignStatus = esignStatus;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getIsDownloaded() {
        return isDownloaded;
    }

    public void setIsDownloaded(String isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

}
