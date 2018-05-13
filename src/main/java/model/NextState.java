package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NextState {
    @JsonIgnore
    private String lastUpdateDate;
    @JsonIgnore
    private String mentions;
    @JsonIgnore
    private String read;
    @JsonIgnore
    private String forwarded;
    @JsonIgnore
    private String quote;
    @JsonIgnore
    private String previews;
    @JsonIgnore
    private String important;
    @JsonIgnore
    private String attachments;
    @JsonIgnore
    private String createdDate;
    @JsonIgnore
    private String createdBy;

    private String message;

    @JsonIgnore
    private String _id;
    @JsonIgnore
    private String caseId;
    public String getMessage() {
        return message;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public void setForwarded(String forwarded) {
        this.forwarded = forwarded;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setPreviews(String previews) {
        this.previews = previews;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
