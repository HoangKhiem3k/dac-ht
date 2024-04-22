package jp.co.hakuhodo.technologies.common.api;

/**
 * APIエラーコード
 */
public interface IErrorCode {
	long getCode();

	String getMessage();
}
