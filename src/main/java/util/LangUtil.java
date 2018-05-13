package util;

import model.Message;
import model.ResponseMessage;

public class LangUtil {

    public static ResponseMessage createResponseMessage(Message message, String text) {
        return new ResponseMessage(
                message.getCaseId(),
                message.getTeamName(),
                text,
                new String[0]);
    }
}