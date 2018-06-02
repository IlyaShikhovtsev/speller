import model.Message;
import util.LangUtil;

import java.io.IOException;

public class MessageHandler {

    private LanguageResolver languageResolver = LanguageResolver.getInstance();
    private ServiceResolver serviceResolver = ServiceResolver.getInstance();
    private LangTool langTool = new LangTool();

    private String helpCommand;
    private String allLanguages;

    public MessageHandler() throws IOException {
        helpCommand = LangUtil.read(ClassLoader.getSystemResourceAsStream("helpText"));
        allLanguages = LangUtil.read(ClassLoader.getSystemResourceAsStream("allLanguages"));
    }

    public void processMessage(Message message) {
        MessageManager messageManager = MessageManager.getInstance();
        String textMessage = message.getNextState().getMessage();
        String responseMessage;
        if (textMessage.startsWith("/")) {
            switch (serviceResolver.getService(message.getCaseId())) {
                case ServiceResolver.LANGUAGE_TOOL_SERVICE: {
                    responseMessage = langToolCommands(textMessage, message);
                    break;
                }
                case ServiceResolver.SPELLER_SERVICE: {
                    responseMessage = spellerCommands(textMessage, message);
                    break;
                }
                default: {
                    responseMessage = "Неверный сервис";
                }
            }
        } else {
            responseMessage = checkText(textMessage, message);
        }
        messageManager.sendMessage(LangUtil.createResponseMessage(message, responseMessage));
    }

    private String checkText(String textMessage, Message message) {
        switch (serviceResolver.getService(message.getCaseId())) {
            case ServiceResolver.LANGUAGE_TOOL_SERVICE: {
                return langTool.getChecked(textMessage, languageResolver.getLanguage(message.getCaseId()));
            }
            case ServiceResolver.SPELLER_SERVICE: {
                return YandexSpeller.CheckText(textMessage);
            }
            default: {
                return "Что-то пошло не так ;(";
            }
        }
    }

    private String spellerCommands(String textMessage, Message message) {
        switch (textMessage.split(" ")[0]) {
            case "/langtool": {
                serviceResolver.setService(message.getCaseId(), ServiceResolver.LANGUAGE_TOOL_SERVICE);
                return "Выбран LanguageTool";
            }
            case "/speller": {
                return "Выбран Яндекс.Спеллер";
            }
            case "/help": {
                return helpCommand;
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
                serviceResolver.setService(message.getCaseId(), ServiceResolver.SPELLER_SERVICE);
                return "Выбран Яндекс.Спеллер";
            }
            case "/langtool": {
                return "Выбран LanguageTool";
            }
            case "/help": {
                return helpCommand;
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
                    result = allLanguages;
                    break;
                }
            }
        }
        return result;
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