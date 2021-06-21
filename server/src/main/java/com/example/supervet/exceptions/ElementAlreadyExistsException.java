package com.example.supervet.exceptions;

public class ElementAlreadyExistsException extends RuntimeException{

    public ElementAlreadyExistsException(String message){
        super(message);
    }
}
