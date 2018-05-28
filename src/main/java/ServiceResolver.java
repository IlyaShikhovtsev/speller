import java.util.HashMap;
import java.util.Map;

public class ServiceResolver {
    private static ServiceResolver serviceResolver = null;

    private String defaultService;
    private Map<String, String> serviceMap = new HashMap<>();

    public static final String langToolService = "LanguageToolService";
    public static final String spellerService = "SpellerService";

    public ServiceResolver() {
        defaultService = "LanguageToolService";
    }

    public void setService(String caseId, String service) {
        serviceMap.put(caseId, service);
    }

    public String getService(String caseId) {
        if (!serviceMap.containsKey(caseId)) {
            return defaultService;
        }
        return serviceMap.get(caseId);
    }

    public static ServiceResolver getInstance() {
        if (serviceResolver == null) {
            serviceResolver = new ServiceResolver();
        }
        return serviceResolver;
    }
}
