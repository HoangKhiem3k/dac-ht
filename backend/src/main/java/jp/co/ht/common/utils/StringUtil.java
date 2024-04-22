package jp.co.ht.common.utils;

import java.text.BreakIterator;

public class StringUtil {

	private StringUtil() {
	}

	/**
	 * If the argument is null, returns an empty string.
	 *
	 * @param str string text
	 * @return Argument is null: empty string, argument is not null: argument
	 */
	public static String defaultString(String str) {
		if (null == str) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * If the argument is null, returns an empty string.
	 *
	 * @param str string text
	 * @return Argument is null: empty string, argument is not null: argument
	 */
	public static String defaultString(String str, String defaultStr) {
		if (StringUtil.isNullOrEmpty(str)) {
			return defaultStr;
		} else {
			return str;
		}
	}

	/**
	 * Determines whether the specified string is Null or Empty.
	 *
	 * @param string value
	 * @return judgement result
	 */
	public static boolean isNullOrEmpty(String value) {
		return (value == null) || (value.isEmpty());
	}

	/**
	 * Determines whether the specified string is NotNull or notEmpty.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	/**
	 * Determines whether the specified string is Null or Empty.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	/**
	 * If the string is null, returns an empty string.
	 *
	 * @param value
	 * @return Original string or empty string
	 */
	public static String setNullConvEmpty(String value) {
		return (value == null) ? "" : value;
	}

	/**
	 * If Object is null, returns an empty string; if Object is not empty, returns the string of Object.
	 *
	 * @param obj
	 * @return Original string or empty string
	 */
	public static String setNullConvEmptyForObject(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * Combines elements of a string array.
	 *
	 * @param values    string array
	 * @param separator delimited string
	 * @return Processing result
	 */
	public static String join(String[] values, String separator) {
		String sep = "";
		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			sb.append(sep);
			sb.append(value);
			sep = separator;
		}
		return sb.toString();
	}

	/**
	 * Combines elements of a string array.
	 *
	 * @param values    object array
	 * @param separator delimited string
	 * @return Processing result
	 */
	public static String join(Object[] values, String separator) {
		String sep = "";
		StringBuilder sb = new StringBuilder();
		for (Object value : values) {
			sb.append(sep);
			sb.append(setNullConvEmptyForObject(value));
			sep = separator;
		}
		return sb.toString();
	}

	/**
	 * Returns the number of characters (supports surrogate pair characters).
	 *
	 * @param str string
	 * @return word count
	 */
	public static int getLength(String str) {
		BreakIterator bi = BreakIterator.getCharacterInstance();
		bi.setText(str);
		int count = 0;
		while (bi.next() != BreakIterator.DONE) {
			count++;
		}
		return count;
	}
	
	/**
	 * LowerCase and add prefix suffix for search like param.
	 *
	 * @param value
	 * @return like param
	 */
	public static String getFullSearchLikeParam(String value) {
		return (value == null) ? "%%" : "%" + value.toLowerCase() + "%";
	}
}
