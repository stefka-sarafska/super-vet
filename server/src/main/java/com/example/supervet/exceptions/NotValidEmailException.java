package com.example.supervet.exceptions;

public class NotValidEmailException extends RuntimeException{

    public NotValidEmailException(String message){
        super(message);
    }
}
