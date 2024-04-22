package jp.co.ht.model.common;

public class CommonResult<T> {
	
    private long code;
    
    private String message;
    
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Successful result
     *
     * @param data
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * Successful result
     *
     * @param data
     * @param message
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * Failure result
     * 
     * @param errorCode
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * Failure result
     * 
     * @param errorCode
     * @param message
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<>(errorCode.getCode(), message, null);
    }

    /**
     * Failure result
     * 
     * @param message
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * Failure result
     * 
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * Failure result
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * Failure result
     * 
     * @param message
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }
    /**
     * Failure result
     * 
     * @param message
     */
    public static <T> CommonResult<T> validateFailed2(String message) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED2.getCode(), message, null);
    }

    /**
     * Can't access
     */
    public static <T> CommonResult<T> unavailable(T data) {
        return new CommonResult<>(ResultCode.UNAVAILABLE.getCode(), ResultCode.UNAVAILABLE.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
