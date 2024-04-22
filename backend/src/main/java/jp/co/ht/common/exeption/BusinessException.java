package jp.co.ht.common.exeption;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jp.co.ht.common.utils.BusinessMessage;

/**
 * Business exception. Business exceptions have messages.
 * This exception is thrown from the business logic layer to the presentation layer.
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<BusinessMessage> rootErrors = new ArrayList<>();
    private Map<String, BusinessMessage> fieldErrors = new LinkedHashMap<>();

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        rootErrors.add(new BusinessMessage(message));
    }

    public BusinessException(String message, Object... args) {
        super(message);
        rootErrors.add(new BusinessMessage(message, args));
    }

    public BusinessException(String field, String message, Object... args) {
        super(message);
        fieldErrors.put(field, new BusinessMessage(message, args));
    }

    public BusinessException(Throwable cause, String message, Object... args) {
        super(message, cause);
        rootErrors.add(new BusinessMessage(message, args));
    }

    public BusinessException(BusinessMessage businessMessage) {
        super(businessMessage.getMessageId());
        rootErrors.add(businessMessage);
    }

    public BusinessException(String message, BusinessMessage businessMessage) {
        super(message);
        rootErrors.add(businessMessage);
    }

    public BusinessException(String message, List<BusinessMessage> businessMessageList) {
        super(message);
        rootErrors.addAll(businessMessageList);
    }

    public List<BusinessMessage> getMessageList() {
        List<BusinessMessage> result = new ArrayList<>();
        result.addAll(rootErrors);
        result.addAll(fieldErrors.values());
        return result;
    }

    public List<BusinessMessage> getRootErrors() {
        return rootErrors;
    }

    public Map<String, BusinessMessage> getFieldErrors() {
        return fieldErrors;
    }

    public void addMessage(BusinessMessage businessMessage) {
        rootErrors.add(businessMessage);
    }

    public void addMessage(String field, BusinessMessage businessMessage) {
        fieldErrors.put(field, businessMessage);
    }

    public void addMessage(String message, Object... args) {
        rootErrors.add(new BusinessMessage(message, args));
    }

    public void addMessage(String field, String message, Object... args) {
        fieldErrors.put(field, new BusinessMessage(message, args));
    }

    public void throwsIfPresent() throws BusinessException {
        if (getMessageList().size() > 0) throw this;
    }
}
