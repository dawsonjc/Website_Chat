package com.brewery.web.model;

public enum RecordStatus {
    ACTIVE("Active"),
    DISABLED("Disabled");

    private final String status;

    RecordStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
