package com.api.docsen.exchanges;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String error;
    public ErrorResponse(String error) {
        this.error = error;
    }
    public String getError() {
        return this.error;
    }
}
