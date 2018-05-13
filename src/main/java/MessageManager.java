import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message;
import model.ResponseMessage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MessageManager {

    private final String URL_GET = "https://api.acm.chat/getMessages";
    private final String APPLICATION_JSON = "application/json";

    private String xAcmKey;
    private String xAcmChanel;
    private ObjectMapper mapper = new ObjectMapper();

    public MessageManager(String xAcmKey, String xAcmChanel) {
        this.xAcmKey = xAcmKey;
        this.xAcmChanel = xAcmChanel;

    }

    public Message[] getMessages() {
        try {
            URL url = new URL(URL_GET);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-ACM-Key", xAcmKey);
            con.setRequestProperty("X-ACM-Chanel", xAcmChanel);

            return mapper.readValue(con.getInputStream(), Message[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean sendMessage(ResponseMessage message) {
        try {
            URL url = new URL(String.format("https://api.acm.chat/rest/v2/cases/%s/chats/messages", message.getCaseId()));

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("X-ACM-Key", xAcmKey);
            con.setRequestProperty("X-ACM-Chanel", xAcmChanel);
            con.setRequestProperty("Content-Type", APPLICATION_JSON);
            con.setRequestProperty("X-ACM-Transmitter", message.getTeamName());
            mapper.writeValue(con.getOutputStream(), message);
            con.getResponseCode();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
