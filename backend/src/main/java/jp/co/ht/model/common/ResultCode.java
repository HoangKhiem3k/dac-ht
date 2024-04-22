package jp.co.ht.model.common;

public enum ResultCode implements IErrorCode {
	
    SUCCESS(200, "OK"),
    
    VALIDATE_FAILED2(900, "Validate Failed"),
    
    FAILED(500, "Internal Server Error"),
    
    UNAVAILABLE(503, "Service Unavailable"),
    
    VALIDATE_FAILED(400, "Bad Request"),
    
    UNAUTHORIZED(401, "Unauthorized"),
    
    FORBIDDEN(403, "Forbidden");
	
    private long code;
    
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
