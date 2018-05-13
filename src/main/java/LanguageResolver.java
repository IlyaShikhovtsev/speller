import org.languagetool.Language;
import org.languagetool.Languages;

import java.util.HashMap;
import java.util.Map;

public class LanguageResolver {

    private static final String DEFAULT_LANGUAGE = "ru";

    private Map<String, Language> languageMap = new HashMap<>();

    public void setLanguage(String caseId, String language) {
            languageMap.put(caseId, Languages.getLanguageForShortCode(language));
    }

    public Language getLanguage(String caseId) {
        if(!languageMap.containsKey(caseId)) {
            setLanguage(caseId, DEFAULT_LANGUAGE);
        }
        return languageMap.get(caseId);
    }
}
