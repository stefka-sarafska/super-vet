package com.example.supervet.exceptions;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String message){
        super(message);
    }
}
