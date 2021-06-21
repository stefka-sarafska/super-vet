package com.example.supervet.exceptions;

public class ErrorCreatingEntityException extends RuntimeException{

    public ErrorCreatingEntityException(String message){
        super(message);
    }
}
