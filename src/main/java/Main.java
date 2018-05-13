import model.Message;
import model.ResponseMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        LanguageResolver languageResolver = new LanguageResolver();
        LangTool langTool = new LangTool();
        properties.load(new FileInputStream("src/main/resources/bot.properties"));
        MessageManager messageManager = new MessageManager(properties.getProperty("X-ACM-Key"), properties.getProperty("X-ACM-Chanel"));

        while(true) {
            for(Message message : messageManager.getMessages()) {
                try {
                    String textMessage = message.getNextState().getMessage();
                    if(textMessage.startsWith("/help")) {

                    } else if(textMessage.startsWith("/lang")) {
                        languageResolver.setLanguage(textMessage.split(" ")[1], message.getCaseId());
                    } else {
                        ResponseMessage responseMessage = new ResponseMessage(message.getCaseId(), message.getTeamName(), langTool.getChecked(message.getNextState().getMessage(), "ru"), new String[0]);
                        messageManager.sendMessage(responseMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
