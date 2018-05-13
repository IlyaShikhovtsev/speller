import model.Message;
import util.LangUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                        messageManager.sendMessage(LangUtil.createResponseMessage(
                                message,
                                new String(Files.readAllBytes(Paths.get("src/main/resources/helpText"))))
                        );
                    } else if(textMessage.startsWith("/lang")) {
                        if (textMessage.split(" ").length > 1) {
                            languageResolver.setLanguage(
                                    message.getCaseId(),
                                    textMessage.split(" ")[1]
                            );
                        }
                        messageManager.sendMessage(LangUtil.createResponseMessage(
                                message,
                                "Выбранный язык - " + languageResolver.getLanguage(message.getCaseId()).getName())
                        );
                    } else {
                        messageManager.sendMessage(LangUtil.createResponseMessage(
                                message,
                                langTool.getChecked(message.getNextState().getMessage(), languageResolver.getLanguage(message.getCaseId())))
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
