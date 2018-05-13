import model.Message;
import util.LangUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageHandler {

    private LanguageResolver languageResolver = LanguageResolver.getInstance();
    private LangTool langTool = new LangTool();

    private String helpCommand;
    private String allLanguages;

    public void processMessage(Message message) {
        MessageManager messageManager = MessageManager.getInstance();
        String textMessage = message.getNextState().getMessage();
        String responseMessage;
        if (textMessage.startsWith("/")) {
            switch (textMessage.split(" ")[0]) {
                case "/help": {
                    responseMessage = helpCommand();
                    break;
                }
                case "/lang": {
                    responseMessage = langCommand(textMessage, message.getCaseId());
                    break;
                }
                default: {
                    responseMessage = "Неизвестная команда ;(";
                }
            }
        } else {
            responseMessage = langTool.getChecked(message.getNextState().getMessage(), languageResolver.getLanguage(message.getCaseId()));
        }
        messageManager.sendMessage(LangUtil.createResponseMessage(message, responseMessage));
    }

    private String langCommand(String textMessage, String caseId) {
        if (textMessage.equals("/lang")) {
            return languageResolver.getLanguage(caseId).getName();
        }
        String result = "Неизвестная команда ;(";
        String command = textMessage.split(" ")[1];
        if (command.length() == 2) {
            result = setLanguage(command, caseId);
        } else {
            switch (command) {
                case "all": {
                    result = getAllLanguages();
                    break;
                }
            }
        }
        return result;
    }

    private String getAllLanguages() {
        try {
            if (allLanguages == null) {
                allLanguages = new String(Files.readAllBytes(Paths.get("src/main/resources/allLanguages")));
            }
            return allLanguages;
        } catch (IOException e) {
            e.printStackTrace();
            return "Упс, что-то пошло не так ;(";
        }
    }

    private String helpCommand() {
        try {
            if (helpCommand == null) {
                helpCommand = new String(Files.readAllBytes(Paths.get("src/main/resources/helpText")));
            }
            return helpCommand;
        } catch (IOException e) {
            e.printStackTrace();
            return "Упс, что-то пошло не так ;(";
        }
    }

    private String setLanguage(String language, String caseId) {
        try {
            languageResolver.setLanguage(
                    caseId,
                    language
            );
            return "Language " + languageResolver.getLanguage(caseId);
        } catch (IllegalArgumentException e) {
            return "Вы указали неподдерживаемый язык ;(\nВведите \"/lang all\" для просмотра доступных языков";
        }
    }
}
