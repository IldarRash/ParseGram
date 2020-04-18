package com.parsegram.boot.handlers;

import com.parsegram.boot.utils.ServerRequestResponseConverter;
import lombok.Data;

@Data
public abstract class AbstractHandler<T> implements Handler<T>{

    private final ServerRequestResponseConverter<T> serverRequestResponseConverter;

    public AbstractHandler(ServerRequestResponseConverter<T> serverRequestResponseConverter) {
        this.serverRequestResponseConverter = serverRequestResponseConverter;
    }


}
