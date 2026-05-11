package com.aroos.qr.generator.modules;

/*
 * The {@link ReservedModuleException} class implements behavior for a custom
 * exception type signifying that a reserved QR module was attempted to be set.
 */
public class ReservedModuleException extends RuntimeException
{
    public ReservedModuleException()
    {
        super();
    }

    public ReservedModuleException(String message)
    {
        super(message);
    }

    public ReservedModuleException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ReservedModuleException(Throwable cause)
    {
        super(cause);
    }

    protected ReservedModuleException(
        String message, 
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}