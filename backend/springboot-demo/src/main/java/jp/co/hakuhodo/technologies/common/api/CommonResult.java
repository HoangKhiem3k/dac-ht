package jp.co.hakuhodo.technologies.common.api;

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
	 * 成功結果
	 *
	 * @param data データ
	 */
	public static <T> CommonResult<T> success(T data) {
		return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
	}

	/**
	 * 成功結果
	 *
	 * @param data    データ
	 * @param message メッセージ
	 */
	public static <T> CommonResult<T> success(T data, String message) {
		return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
	}

	/**
	 * 失敗結果
	 * 
	 * @param errorCode エラーコード
	 */
	public static <T> CommonResult<T> failed(IErrorCode errorCode) {
		return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
	}

	/**
	 * 失敗結果
	 * 
	 * @param errorCode エラーコード
	 * @param message   メッセージ
	 */
	public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
		return new CommonResult<>(errorCode.getCode(), message, null);
	}

	/**
	 * 失敗結果
	 * 
	 * @param message メッセージ
	 */
	public static <T> CommonResult<T> failed(String message) {
		return new CommonResult<>(ResultCode.FAILED.getCode(), message, null);
	}

	/**
	 * 失敗結果
	 * 
	 */
	public static <T> CommonResult<T> failed() {
		return failed(ResultCode.FAILED);
	}

	/**
	 * 検証失敗結果
	 */
	public static <T> CommonResult<T> validateFailed() {
		return failed(ResultCode.VALIDATE_FAILED);
	}

	/**
	 * 検証失敗結果
	 * 
	 * @param message メッセージ
	 */
	public static <T> CommonResult<T> validateFailed(String message) {
		return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
	}
	/**
	 * 検証失敗結果
	 * 
	 * @param message メッセージ
	 */
	public static <T> CommonResult<T> validateFailed2(String message) {
		return new CommonResult<>(ResultCode.VALIDATE_FAILED2.getCode(), message, null);
	}

	/**
	 * ログインなし
	 */
	public static <T> CommonResult<T> unauthorized(T data) {
		return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
	}

	/**
	 * 権限なし結果
	 */
	public static <T> CommonResult<T> forbidden(T data) {
		return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
	}

	/**
	 * アクセスできない
	 */
	public static <T> CommonResult<T> unavailable(T data) {
		return new CommonResult<>(ResultCode.UNAVAILABLE.getCode(), ResultCode.UNAVAILABLE.getMessage(), data);
	}


	/**
	 * ログインなし
	 */
	public static <T> CommonResult<T> unauthorized(String message, T data) {
		return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), message, data);
	}

	/**
	 * 権限なし結果
	 */
	public static <T> CommonResult<T> forbidden(String message, T data) {
		return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), message, data);
	}

	/**
	 * アクセスできない
	 */
	public static <T> CommonResult<T> unavailable(String message, T data) {
		return new CommonResult<>(ResultCode.UNAVAILABLE.getCode(), message, data);
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
