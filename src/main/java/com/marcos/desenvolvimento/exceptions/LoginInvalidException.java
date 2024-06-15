package com.marcos.desenvolvimento.exceptions;

public class LoginInvalidException extends RuntimeException {
    public LoginInvalidException(String s) {
        super(s);
    }
}
