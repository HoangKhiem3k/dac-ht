package jp.co.ht.common.utils;

import java.io.Serializable;

/**
 * Business exception
 * A class that holds output messages that occur in the logic layer.
 */
public class BusinessMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String messageId = null;

    private Object[] values = null;

    public String getMessageId() {
        return (this.messageId);
    }

    public Object[] getValues() {
        return (this.values);
    }

    public BusinessMessage(String messageId) {
        this(messageId, new Object[0]);
    }

    public BusinessMessage(String messageId, Object... values) {
        this.messageId = messageId;
        this.values = values;
    }
}
