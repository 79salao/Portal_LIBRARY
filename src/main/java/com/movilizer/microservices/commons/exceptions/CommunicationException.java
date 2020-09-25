package com.movilizer.microservices.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CommunicationException extends RuntimeException {
    public CommunicationException(String fromMicroservice, String toMicroservice) {
        super("Communication failed between " + fromMicroservice + "  and " + toMicroservice + " microservices.");
    }
}
