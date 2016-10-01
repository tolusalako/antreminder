/** Copyright (c) 2016 SalakoTech.
 * This file and all of its contents belong to SalakoTech and should not be shared.
 * Created on: Jul 2, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 2, 2016
 */
package net.csthings.antreminder.services;

public final class ServiceException extends Exception{
    
    public ServiceException(String message){
        super(message);
    }
    
    public ServiceException(String message, Throwable throwable){
        super(message, throwable);
    }
    
}
