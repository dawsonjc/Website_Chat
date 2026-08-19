package com.brewery.web.model.record;

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

    public static RecordStatus fromString(String value) {
        for(RecordStatus rs : RecordStatus.values()) {
            if(rs.status.equalsIgnoreCase(value)) {
                return rs;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
