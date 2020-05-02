package com.parsegram.boot.exceptions;

public class EmailIsAlreadyExistException extends RuntimeException {

    public EmailIsAlreadyExistException() {
        super("Пользователь с такми эмайлом уже существет");
    }
}
