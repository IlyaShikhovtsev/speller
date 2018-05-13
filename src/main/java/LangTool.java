import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;

import java.util.List;

public class LangTool {

    public String getChecked(String message, String language) throws Exception {
        JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode(language));
        List<RuleMatch> matches = langTool.check(message);
        List<AnalyzedSentence> sentences = langTool.analyzeText(message);

        if (matches.size() == 0) {
            return "Все верно!";
        }

        int countOfMistakes = 1;
        int countOfSentences = 0;
        int lengthOfPrevSentences = 0;
        StringBuilder content = new StringBuilder();

        for (RuleMatch match : matches) {
            content.append(countOfMistakes++).append(".) ").append(match.getShortMessage().length() != 0 ? match.getShortMessage() : match.getMessage()).append("\n");

            StringBuilder sb = new StringBuilder(match.getSentence().getText());
            while (match.getFromPos() >= sentences.get(countOfSentences).getText().length() + lengthOfPrevSentences) {
                lengthOfPrevSentences += sentences.get(countOfSentences++).getText().length();
            }
            sb.insert(match.getFromPos() - lengthOfPrevSentences, "[");
            sb.insert(match.getToPos() + 1 - lengthOfPrevSentences, "]");
            content.append((sb.toString() + "\n").replaceAll("\n\n", "\n"));

            content.append(match.getSuggestedReplacements().size() == 0 ? "\n" : ("Варианты: " +
                    (match.getSuggestedReplacements().size() > 5 ? match.getSuggestedReplacements().subList(0, 5) : match.getSuggestedReplacements()) + "\n\n"));
        }

        return content.toString();
    }

    public String getChecked2(String message) throws Exception {
        return getChecked(message, "ru");
    }
}
