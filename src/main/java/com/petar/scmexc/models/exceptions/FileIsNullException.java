package com.marco.scmexc.models.exceptions;

public class FileIsNullException extends RuntimeException {

    public FileIsNullException() {
        super("The file your want to upload is null");
    }
}
