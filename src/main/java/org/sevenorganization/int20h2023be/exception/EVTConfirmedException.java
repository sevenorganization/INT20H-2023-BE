package org.sevenorganization.int20h2023be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EVTConfirmedException extends RuntimeException {
    public EVTConfirmedException() {
        super("Email is already confirmed");
    }
}
