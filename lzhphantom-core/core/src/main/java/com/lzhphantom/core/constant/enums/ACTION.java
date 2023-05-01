package com.lzhphantom.core.constant.enums;

public enum ACTION {
    INSERT("Insert"),
    INSERT_TO_SUBMIT("Submitted Insert"),
    INSERT_TO_APPROVE("Approved Insert"),
    INSERT_TO_DRAFT("Draft Insert"),
    UPDATE("Update"),
    UPDATE_TO_SUBMIT("Submitted Update"),
    UPDATE_TO_APPROVE("Approved Update"),
    DELETE("Delete"),
    DELETE_TO_SUBMIT("Submitted Delete"),
    DELETE_TO_APPROVE("Approved Delete"),
    SUBMIT("Submitted"),
    RETURN("Returned"),
    RETURN_TO_SUBMIT("Submitted Return"),
    RETURN_TO_APPROVE("Approved Return"),
    WITHDRAW("Withdrawn"),
    DRAFT("Draft"),
    REJECT("Rejected"),
    REJECT_TO_SUBMIT("Submitted Reject"),
    REJECT_TO_APPROVE("Approved Reject"),
    APPROVED("Approved"),
    PROCESS("Processing"),
    UPLOAD("Uploaded"),
    FAILED("Failed"),
    NEW("New"),
    COMPLETED("Completed");

    private final String status;

    ACTION(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
