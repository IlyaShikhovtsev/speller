import model.ResponseMessage;
import org.junit.Test;

import java.util.Properties;

public class MessageHandlerTest {

    @Test(expected = IllegalArgumentException.class)
    public void LangToolTest() throws Exception {
        String message = "Превет. Превет.";
        printResult(message, "ru");
    }

    @Test
    public void LangToolTest2() throws Exception {
        String message = "";
        printResult(message, "ru");
    }

    @Test
    public void languageTest() throws Exception {
        String message = "Превет, ребята!";
        printResult(message, "akngiav adkvjnadknvkaldm");
    }

    @Test
    public void sendMessageTest() {
        Properties properties = new Properties();
        ResponseMessage message = new ResponseMessage("5ac4a2cce537b800262f4e9a", "rtf-host.acm.chat", "Hi!", new String[0]);
        new MessageManager(properties.getProperty("X-ACM-KEY"), properties.getProperty("X-ACM-Chanel")).sendMessage(message);
    }

    private void printResult(String message, String lang) {
        try {
            System.out.println(new LangTool().getChecked(message, lang));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}