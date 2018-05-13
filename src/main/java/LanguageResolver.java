import org.languagetool.Language;
import org.languagetool.Languages;
import org.languagetool.language.Russian;

import java.util.HashMap;
import java.util.Map;

public class LanguageResolver {

    private static LanguageResolver languageResolver = null;

    private Language defaultLanguage;
    private Map<String, Language> languageMap = new HashMap<>();

    public LanguageResolver() {
        defaultLanguage = new Russian();
    }

    public void setLanguage(String caseId, String language) {
        languageMap.put(caseId, Languages.getLanguageForShortCode(language));
    }

    public Language getLanguage(String caseId) {
        if (!languageMap.containsKey(caseId)) {
            return defaultLanguage;
        }
        return languageMap.get(caseId);
    }

    public static LanguageResolver getInstance() {
        if (languageResolver == null) {
            languageResolver = new LanguageResolver();
        }
        return languageResolver;
    }
}
