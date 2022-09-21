package com.marco.scmexc.models.response;

public class SelectOptionResponse {
    public final Long id;
    public final String label;

    private SelectOptionResponse(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public static SelectOptionResponse of(Long id, String label) {
        return new SelectOptionResponse(id, label);
    }

}
