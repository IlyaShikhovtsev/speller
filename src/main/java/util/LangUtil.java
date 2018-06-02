package util;

import model.Message;
import model.ResponseMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LangUtil {

    public static ResponseMessage createResponseMessage(Message message, String text) {
        return new ResponseMessage(
                message.getCaseId(),
                message.getTeamName(),
                text,
                new String[0]);
    }

    public static String read(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) != -1) {
            out .write(buffer, 0, length);
        }
        return new String(out.toByteArray(), "UTF-8");
    }
}