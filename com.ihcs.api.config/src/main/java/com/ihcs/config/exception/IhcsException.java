/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.exception;

/**
 *
 * @author Andri Setiady
 */
public class IhcsException extends RuntimeException{
    public IhcsException(String message){
        super(message);
    }
}
