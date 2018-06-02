import com.fasterxml.jackson.databind.ObjectMapper;
import model.SpellerMessage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public final class YandexSpeller {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String CheckText(String text) {
        SpellerMessage[] spellerMessages = Get(text);
        int countOfMistakes = 1;

        if (spellerMessages.length == 0) return "Всё верно!";
        else {
            StringBuilder content = new StringBuilder();
            for (SpellerMessage message : spellerMessages) {

                StringBuilder sb = new StringBuilder(text);
                sb.insert(message.getPos(), "[");
                sb.insert(message.getPos()+message.getLen() + 1, "]");
                String str = sb.toString().substring(message.getPos()-20 > 0 ? message.getPos() - 20 : 0, message.getPos()+message.getLen()+20 < text.length() ? message.getPos()+message.getLen()+20 : text.length()+2);
                sb = new StringBuilder(str);
                sb.insert(0,"...");
                sb.append("...");
                sb.insert(0, countOfMistakes++ + ".) ");
                sb.append("\n");
                content.append(sb.toString());

                content.append(message.getS().length == 0 ? "\n" : ("Варианты: ["));
                for (int i = 0; i < message.getS().length - 1; i++) {
                    content.append(message.getS()[i]+", ");
                }
                content.append(message.getS()[message.getS().length - 1]+ "]\n\n");
            }
            return content.toString();
        }
    }

    private static SpellerMessage[] Get(String text){
        try {
            String _text = URLEncoder.encode(text);
            URL url = new URL("https://speller.yandex.net/services/spellservice.json/checkText?text="+_text);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            return mapper.readValue(con.getInputStream(), SpellerMessage[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
