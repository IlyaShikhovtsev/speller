package model;

public class Message {

    private NextState nextState;
    private String caseId;
    private String teamName;
    private String eventName;

    public NextState getNextState() {
        return nextState;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setNextState(NextState nextState) {
        this.nextState = nextState;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
