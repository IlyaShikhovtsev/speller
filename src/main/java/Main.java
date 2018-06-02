import model.Message;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static Properties properties = new Properties();

    public static void main(String[] args) throws IOException {
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("bot.properties"));
            MessageManager messageManager = MessageManager.getInstance();
            MessageHandler messageHandler = new MessageHandler();
            while (true) {
                for (Message message : messageManager.getMessages()) {
                    messageHandler.processMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}