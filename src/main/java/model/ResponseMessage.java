package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseMessage {

    @JsonIgnore
    private String caseId;

    @JsonIgnore
    private String teamName;

    private String message;

    private String[] attachments;

    public ResponseMessage(String caseId, String teamName, String message, String[] attachments) {
        this.caseId = caseId;
        this.teamName = teamName;
        this.message = message;
        this.attachments = attachments;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getMessage() {
        return message;
    }

    public String[] getAttachments() {
        return attachments;
    }
}