/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.exception;

/**
 *
 * @author vidya
 */
public class ASPException extends RuntimeException
{
    private String errorCode;
    private String errorMessage;
    private Throwable originalCause;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getOriginalCause() {
        return originalCause;
    }

    public void setOriginalCause(Throwable originalCause) {
        this.originalCause = originalCause;
    }

    public ASPException(String errorCode, String errorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ASPException(String errorCode, String errorMessage, Throwable originalCause) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.originalCause = originalCause;
    }
    
    public static final String ERR_INTERNAL_ERROR = "ERR_INTERNAL_ERROR";
    public static final String ERR_PROPERTIES_FILE_DOES_NOT_EXISTS = "ERR_PROPERTIES_FILE_DOES_NOT_EXISTS";
    
}

