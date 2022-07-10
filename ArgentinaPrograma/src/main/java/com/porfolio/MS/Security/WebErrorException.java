package com.porfolio.MS.Security;
/**
 *
 * @author MANUEL SAPONARO
 *
 */
public class WebErrorException extends RuntimeException {

    private String message;

    public WebErrorException() {}

    public WebErrorException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}