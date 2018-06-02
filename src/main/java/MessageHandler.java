import model.Message;
import util.LangUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageHandler {

    private LanguageResolver languageResolver = LanguageResolver.getInstance();
    private ServiceResolver serviceResolver = ServiceResolver.getInstance();
    private LangTool langTool = new LangTool();

    private String helpCommand;
    private String allLanguages;

    public void processMessage(Message message) {
        MessageManager messageManager = MessageManager.getInstance();
        String textMessage = message.getNextState().getMessage();
        String responseMessage;
        if (textMessage.startsWith("/")) {
            switch (serviceResolver.getService(message.getCaseId())) {
                case ServiceResolver.langToolService: {
                    responseMessage = langToolCommands(textMessage, message);
                    break;
                }
                case ServiceResolver.spellerService: {
                    responseMessage = spellerComands(textMessage, message);
                    break;
                }
                default: {
                    responseMessage = "Неверный сервис";
                }
            }
        } else {
            responseMessage = CheckText(textMessage, message);
        }
        messageManager.sendMessage(LangUtil.createResponseMessage(message, responseMessage));
    }

    private String CheckText(String textMessage, Message message) {
        switch (serviceResolver.getService(message.getCaseId())) {
            case ServiceResolver.langToolService: {
                return langTool.getChecked(textMessage, languageResolver.getLanguage(message.getCaseId()));
            }
            case ServiceResolver.spellerService: {
                return YandexSpeller.CheckText(textMessage);
            }
            default: {
                return "Что-то пошло не так ;(";
            }
        }
    }

    private String spellerComands(String textMessage, Message message) {
        switch (textMessage.split(" ")[0]) {
            case "/langtool": {
                serviceResolver.setService(message.getCaseId(), ServiceResolver.langToolService);
                return "Выбран LanguageTool";
            }
            case "/speller": {
                return "Выбран Яндекс.Спеллер";
            }
            case "/help": {
                return helpCommand();
            }
            case "/lang": {
                return "Команды \"/lang\" доступны только в сервисе LangTool!\nДля выбора LangTool введите \"/langtool\"\nДля просмотра справки введите \"/help\"";
            }
            default: {
                return "Неверная команда ;(\nДля просмотра справки введите \"/help\"";
            }
        }
    }

    private String langToolCommands(String textMessage, Message message) {
        switch (textMessage.split(" ")[0]) {
            case "/speller": {
                serviceResolver.setService(message.getCaseId(), ServiceResolver.spellerService);
                return "Выбран Яндекс.Спеллер";
            }
            case "/langtool": {
                return "Выбран LanguageTool";
            }
            case "/help": {
                return helpCommand();
            }
            case "/lang": {
                return langCommand(textMessage, message.getCaseId());
            }
            default: {
                return "Неверная команда ;(";
            }
        }
    }

    private String langCommand(String textMessage, String caseId) {
        if (textMessage.equals("/lang")) {
            return languageResolver.getLanguage(caseId).getName();
        }
        String result = "Неизвестная команда ;(";
        String command = textMessage.split(" ")[1];
        if (command.length() == 2 || command.length() == 5) {
            result = setLanguage(command, caseId);
        } else {
            switch (command) {
                case "ast": {
                    result = setLanguage(command, caseId);
                    break;
                }
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
            return "Language changed to " + languageResolver.getLanguage(caseId);
        } catch (IllegalArgumentException e) {
            return "Вы указали неподдерживаемый язык ;(\nВведите \"/lang all\" для просмотра доступных языков";
        }
    }
}
