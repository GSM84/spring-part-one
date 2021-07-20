package ru.geekbrains.rest;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String _msg){
        super(_msg);
    }
}
