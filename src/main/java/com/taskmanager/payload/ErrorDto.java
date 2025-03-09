package com.taskmanager.payload;

import java.util.Date;

public class ErrorDto {
    private String message;
    private Date date;
    private int errorCode;
    private  String uri;
    private String trace;

    public ErrorDto(String message, Date date, int errorCode, String uri, String trace) {
        this.message = message;
        this.date = date;
        this.errorCode = errorCode;
        this.uri = uri;
        this.trace = trace;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Date getDate() {
        return date;
    }

    public String getUri() {
        return uri;
    }

    public String getTrace() {
        return trace;
    }
}
