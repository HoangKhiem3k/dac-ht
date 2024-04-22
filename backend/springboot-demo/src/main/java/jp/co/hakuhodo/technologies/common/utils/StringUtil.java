package jp.co.hakuhodo.technologies.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.text.Normalizer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 文字列Utilityクラス.
 */
public class StringUtil {

	private StringUtil() {
	}

	private static final char[] HANKAKU_KATAKANA = { '｡', '｢', '｣', '､', '･', 'ｦ', 'ｧ', 'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ',
			'ｮ', 'ｯ', 'ｰ', 'ｱ', 'ｲ', 'ｳ', 'ｴ', 'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ', 'ｺ', 'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ', 'ﾀ', 'ﾁ', 'ﾂ',
			'ﾃ', 'ﾄ', 'ﾅ', 'ﾆ', 'ﾇ', 'ﾈ', 'ﾉ', 'ﾊ', 'ﾋ', 'ﾌ', 'ﾍ', 'ﾎ', 'ﾏ', 'ﾐ', 'ﾑ', 'ﾒ', 'ﾓ', 'ﾔ', 'ﾕ', 'ﾖ', 'ﾗ',
			'ﾘ', 'ﾙ', 'ﾚ', 'ﾛ', 'ﾜ', 'ﾝ', 'ﾞ', 'ﾟ' };

	private static final char[] ZENKAKU_KATAKANA = { '。', '「', '」', '、', '・', 'ヲ', 'ァ', 'ィ', 'ゥ', 'ェ', 'ォ', 'ャ', 'ュ',
			'ョ', 'ッ', 'ー', 'ア', 'イ', 'ウ', 'エ', 'オ', 'カ', 'キ', 'ク', 'ケ', 'コ', 'サ', 'シ', 'ス', 'セ', 'ソ', 'タ', 'チ', 'ツ',
			'テ', 'ト', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'ヒ', 'フ', 'ヘ', 'ホ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ヤ', 'ユ', 'ヨ', 'ラ',
			'リ', 'ル', 'レ', 'ロ', 'ワ', 'ン', '゛', '゜' };

	private static final char[] LOWER_SUTEGANA = "ｧｨｩｪｫｯｬｭｮヮ".toCharArray();

	private static final char[] UPPER_SUTEGANA = "ｱｲｳｴｵﾂﾔﾕﾖﾜ".toCharArray();

	/**
	 * 全角アルファベットと半角アルファベットとの文字コードの差.
	 */
	private static final int DIFFERENCE = 'Ａ' - 'A';

	private static char[] signs1 = { '!', '#', '$', '%', '&', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<',
			'=', '>', '?', '@', '[', ']', '^', '_', '{', '|', '}', '~' };

	/**
	 * 半角→全角に変換する.
	 *
	 * @param str 半角文字列
	 * @return 全角文字列
	 */
	public static String hanToZen(String str) {
		return StringUtil.convertKatakanaHanToZen(StringUtil.convertMarkHanToZen(StringUtil.convertEisuHanToZen(str)));
	}

	/**
	 * 半角記号から全角記号に変換する.
	 *
	 * @param str 半角記号文字列
	 * @return 全角記号文字列
	 */
	public static String convertMarkHanToZen(String str) {
		char[] cc = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : cc) {
			char newChar = c;
			if ((('A' <= c) && (c <= 'Z')) || (('a' <= c) && (c <= 'z')) || (('1' <= c) && (c <= '9')) || is1Sign(c)) {
				// 変換対象のcharだった場合に全角文字と半角文字の差分を足す
				newChar = (char) (c + DIFFERENCE);
			}

			sb.append(newChar);
		}
		return sb.toString();
	}

	/**
	 * 半角英数字から全角英数字に変換する.
	 *
	 * @param str 半角英数字の文字列
	 * @return 全角英数字の文字列
	 */
	public static String convertEisuHanToZen(String str) {
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if ((c >= 0x30 && c <= 0x39) || (c >= 0x41 && c <= 0x5A) || (c >= 0x61 && c <= 0x7A)) {
				sb.setCharAt(i, (char) (c + 0xFEE0));
			}
		}
		str = sb.toString();
		return str;
	}

	/**
	 * 半角カタカナを全角カタカナに変換する.
	 *
	 * @param str 対象文字列
	 * @return 変換後の文字列
	 */
	public static String convertKatakanaHanToZen(String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}

		if (str.length() == 1) {
			return katakanaHanToZen(str.charAt(0)) + "";
		}

		StringBuilder sb = new StringBuilder(str);
		int i = 0;
		for (i = 0; i < sb.length() - 1; i++) {
			char originalChar1 = sb.charAt(i);
			char originalChar2 = sb.charAt(i + 1);
			char margedChar = mergeChar(originalChar1, originalChar2);
			if (margedChar != originalChar1) {
				sb.setCharAt(i, margedChar);
				sb.deleteCharAt(i + 1);
			} else {
				char convertedChar = katakanaHanToZen(originalChar1);
				if (convertedChar != originalChar1) {
					sb.setCharAt(i, convertedChar);
				}
			}
		}
		if (i < sb.length()) {
			char originalChar1 = sb.charAt(i);
			char convertedChar = katakanaHanToZen(originalChar1);
			if (convertedChar != originalChar1) {
				sb.setCharAt(i, convertedChar);
			}
		}
		return sb.toString();
	}

	/**
	 * 引数がnullの場合、空文字を返します.
	 *
	 * @param str 文字列
	 * @return 引数がnull:空文字、引数がnull以外:引数
	 */
	public static String defaultString(String str) {
		if (null == str) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 引数がnullの場合、空文字を返します.
	 *
	 * @param str 文字列
	 * @return 引数がnull:空文字、引数がnull以外:引数
	 */
	public static String defaultString(String str, String defaultStr) {
		if (StringUtil.isNullOrEmpty(str)) {
			return defaultStr;
		} else {
			return str;
		}
	}

	/**
	 * キャメルケースの文字列をスネークケースに変換する.
	 *
	 * @param camel キャメルケースの文字列
	 * @return
	 */
	public static final String camelToSnake(final String camel) {
		if (StringUtil.isNullOrEmpty(camel)) {
			return camel;
		}
		final StringBuilder sb = new StringBuilder(camel.length() + camel.length());
		for (int i = 0; i < camel.length(); i++) {
			final char c = camel.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(sb.length() != 0 ? '_' : "").append(Character.toLowerCase(c));
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	/**
	 * スネークケースの文字列をキャメルケースに変換する.
	 *
	 * @param snake スネークケースの文字列
	 * @return
	 */
	public static final String snakeToCamel(final String snake) {
		if (StringUtil.isNullOrEmpty(snake)) {
			return snake;
		}
		final StringBuilder sb = new StringBuilder(snake.length() + snake.length());
		for (int i = 0; i < snake.length(); i++) {
			final char c = snake.charAt(i);
			if (c == '_') {
				sb.append((i + 1) < snake.length() ? Character.toUpperCase(snake.charAt(i + 1)) : "");
			} else {
				sb.append(sb.length() == 0 ? Character.toUpperCase(c) : Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	/**
	 * Windowsでファイル名に使用できない語を除去して返します.
	 *
	 * @param fileName ファイル名
	 * @return 削除後の文字列(最終的に空文字になる可能性があるので呼び出し側で考慮必要)
	 */
	public static String deleteFileForbiddenWordForWindows(String fileName) {

		if (StringUtil.isNullOrEmpty(fileName)) {
			return fileName;
		}
		// ファイル名に禁止される文字「\ / : * ? " < > |」
		fileName = fileName.replace("\\", "");
		fileName = fileName.replace("/", "");
		fileName = fileName.replace(":", "");
		fileName = fileName.replace("*", "");
		fileName = fileName.replace("?", "");
		fileName = fileName.replace("\"", "");
		fileName = fileName.replace("<", "");
		fileName = fileName.replace(">", "");
		fileName = fileName.replace("|", "");
		return fileName;
	}

	/**
	 * 指定された文字列がNullまたはEmptyであるか判定します.
	 *
	 * @param value 対象の文字列
	 * @return 判定結果
	 */
	public static boolean isNullOrEmpty(String value) {
		return (value == null) || (value.isEmpty());
	}

	/**
	 * 指定された文字列がNotNull notEmptyであるか判定します.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	/**
	 * 指定された文字列がNullまたはEmptyであるか判定します.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	/**
	 * 文字列がnullなら空文字を返します.
	 *
	 * @param value 対象の文字列
	 * @return 元の文字列か、空文字
	 */
	public static String setNullConvEmpty(String value) {
		return (value == null) ? "" : value;
	}

	/**
	 * Objectがnullなら空文字を返します、空文字以外Objectの文字列を返します.
	 *
	 * @param obj 対象
	 * @return 元の文字列か、空文字
	 */
	public static String setNullConvEmptyForObject(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * 文字列配列の要素を結合します.
	 *
	 * @param values    文字列
	 * @param separator 区切り文字列
	 * @return 処理結果
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
	 * 文字列配列の要素を結合します.
	 *
	 * @param values    文字列
	 * @param separator 区切り文字列
	 * @return 処理結果
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
	 * 指定された文字列の末尾の部分文字列を返します.
	 *
	 * @param value  対象の文字列
	 * @param length 戻り値の長さ
	 * @return 末尾の文字列
	 */
	public static String right(String value, int length) {
		String ret = null;

		if (value != null) {
			int valueLength = value.length();
			ret = (valueLength <= length) ? value : value.substring(valueLength - length);
		}

		return ret;
	}

	/**
	 * 対象の文字列が指定の文字セットでエンコーディングされた時のバイト長を返します.
	 *
	 * @param value 対象の文字列
	 * @param cs    文字セット
	 * @return バイト長
	 */
	public static int getByteLength(String value, Charset cs) {
		return value.getBytes(cs).length;
	}

	/**
	 * 文字数を返す（サロゲートペア文字対応）.
	 *
	 * @param str 文字列
	 * @return 文字数
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
	 * URLエンコードした文字列を返す.
	 *
	 * @param str URLエンコード対象文字列
	 * @return URLエンコードされた文字列
	 */
	public static String urlEncode(String str) {
		if (isNullOrEmpty(str)) {
			return "";
		}

		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException ee) {
			return null;
		}
	}

	/**
	 * 変換対象半角記号かを判定する.
	 *
	 * @param pc 文字
	 * @return 変換対象半角記号の場合はtrue
	 */
	private static boolean is1Sign(char pc) {
		for (char c : signs1) {
			if (c == pc) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 半角カタカナから全角カタカナに変換する.
	 *
	 * @param c 対象文字
	 * @return 変換後の文字
	 */
	private static char katakanaHanToZen(char c) {
		if (c >= HANKAKU_KATAKANA[0] && c <= HANKAKU_KATAKANA[HANKAKU_KATAKANA.length - 1]) {
			return ZENKAKU_KATAKANA[c - HANKAKU_KATAKANA[0]];
		} else {
			return c;
		}
	}

	/**
	 * 2文字目が濁点・半濁点の場合、合成して1文字に変換する.
	 *
	 * @param c1 1文字目
	 * @param c2 2文字目
	 * @return 変換後の文字
	 */
	private static char mergeChar(char c1, char c2) {
		if (c2 == 'ﾞ') {
			if ("ｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾊﾋﾌﾍﾎ".indexOf(c1) != -1) {
				switch (c1) {
				case 'ｶ':
					return 'ガ';
				case 'ｷ':
					return 'ギ';
				case 'ｸ':
					return 'グ';
				case 'ｹ':
					return 'ゲ';
				case 'ｺ':
					return 'ゴ';
				case 'ｻ':
					return 'ザ';
				case 'ｼ':
					return 'ジ';
				case 'ｽ':
					return 'ズ';
				case 'ｾ':
					return 'ゼ';
				case 'ｿ':
					return 'ゾ';
				case 'ﾀ':
					return 'ダ';
				case 'ﾁ':
					return 'ヂ';
				case 'ﾂ':
					return 'ヅ';
				case 'ﾃ':
					return 'デ';
				case 'ﾄ':
					return 'ド';
				case 'ﾊ':
					return 'バ';
				case 'ﾋ':
					return 'ビ';
				case 'ﾌ':
					return 'ブ';
				case 'ﾍ':
					return 'ベ';
				case 'ﾎ':
					return 'ボ';
				default:
					break;
				}
			}
		} else if (c2 == 'ﾟ' && "ﾊﾋﾌﾍﾎ".indexOf(c1) != -1) {
			switch (c1) {
			case 'ﾊ':
				return 'パ';
			case 'ﾋ':
				return 'ピ';
			case 'ﾌ':
				return 'プ';
			case 'ﾍ':
				return 'ペ';
			case 'ﾎ':
				return 'ポ';
			default:
				break;
			}
		}
		return c1;
	}

	public static String convertToBase64(String str) {
		if (isNullOrEmpty(str)) {
			str = "";
		}
		return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * 全角文字を半角文字に変換を行う。
	 *
	 * @param prm 変換対象文字列
	 * @return 変換結果文字列
	 */
	public static String cnvZentoHan(String prm) {
		if (null != prm && !"".equals(prm)) {
			// 全角→半角
			prm = convFulltoHalf(prm);
		}
		return prm;
	}

	/**
	 * 全角文字を半角文字に変換を行う。
	 *
	 * @param prm 変換対象文字列
	 * @return 変換結果文字列
	 */
	private static String convFulltoHalf(String prm) {
		String normalizedText = "";
		if (prm != null) {
			normalizedText = Normalizer.normalize(prm, Normalizer.Form.NFKC);
			normalizedText = toHankakuKatakana(normalizedText);
		}
		return normalizedText;
	}

	/**
	 * 全角ひらがなをカタカナに変換を行うためのマップ。
	 *
	 */

	private static final Map<String, String> HIRAGANA_KATAKANA_MAP = new HashMap<>();
	static {
		for (char hiragana = 'ぁ'; hiragana <= 'ゖ'; hiragana++) {
			String katakana = String.valueOf((char) (hiragana - 'ぁ' + 'ァ'));
			HIRAGANA_KATAKANA_MAP.put(String.valueOf(hiragana), katakana);
		}
	}
	/**
	 * 全角カタカナ文字を半角カタカナ文字に変換を行うためのマップ。
	 *
	 */
	private static final Map<String, String> FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP = new HashMap<>();
	static {
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("゛", "ﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("゜", "ﾟ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ァ", "ｧ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ア", "ｱ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ィ", "ｨ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("イ", "ｲ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ゥ", "ｩ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ウ", "ｳ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ェ", "ｪ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("エ", "ｴ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ォ", "ｫ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("オ", "ｵ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("カ", "ｶ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ガ", "ｶﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("キ", "ｷ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ギ", "ｷﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ク", "ｸ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("グ", "ｸﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ケ", "ｹ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ゲ", "ｹﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("コ", "ｺ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ゴ", "ｺﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("サ", "ｻ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ザ", "ｻﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("シ", "ｼ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ジ", "ｼﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ス", "ｽ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ズ", "ｽﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("セ", "ｾ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ゼ", "ｾﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ソ", "ｿ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ゾ", "ｿﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("タ", "ﾀ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ダ", "ﾀﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("チ", "ﾁ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヂ", "ﾁﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ッ", "ｯ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ツ", "ﾂ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヅ", "ﾂﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("テ", "ﾃ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("デ", "ﾃﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ト", "ﾄ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ド", "ﾄﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ナ", "ﾅ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ニ", "ﾆ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヌ", "ﾇ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ネ", "ﾈ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ノ", "ﾉ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ハ", "ﾊ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("バ", "ﾊﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("パ", "ﾊﾟ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヒ", "ﾋ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ビ", "ﾋﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ピ", "ﾋﾟ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("フ", "ﾌ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ブ", "ﾌﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("プ", "ﾌﾟ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヘ", "ﾍ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ベ", "ﾍﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ペ", "ﾍﾟ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ホ", "ﾎ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ボ", "ﾎﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ポ", "ﾎﾟ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("マ", "ﾏ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ミ", "ﾐ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ム", "ﾑ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("メ", "ﾒ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("モ", "ﾓ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ャ", "ｬ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヤ", "ﾔ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ュ", "ｭ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ユ", "ﾕ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ョ", "ｮ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヨ", "ﾖ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ラ", "ﾗ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("リ", "ﾘ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ル", "ﾙ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("レ", "ﾚ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ロ", "ﾛ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヮ", "ﾜ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ワ", "ﾜ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヰ", "ｳｨ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヱ", "ｳｪ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヲ", "ｦ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ン", "ﾝ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヴ", "ｳﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヵ", "ｶ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヶ", "ｹ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヷ", "ﾜﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヸ", "ｳﾞｨ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヹ", "ｳﾞｪ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ヺ", "ｦﾞ");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("・", "･");
		FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP.put("ー", "ｰ");
	}

	/**
	 * 全角カタカナ文字を半角カタカナ文字に変換を行う。
	 *
	 * @param prm 変換対象文字列
	 * @param map 変換用マップ
	 * @return 変換結果文字列
	 */
	private static String convert(String prm, Map<String, String> map) {
		if (prm == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		char[] array = prm.toCharArray();
		for (int i = 0; i < array.length; i++) {
			char c1 = array[i];
			String key1 = String.valueOf(c1);
			String key2 = null;
			if ((i + 1) < array.length) {
				char c2 = array[i + 1];
				key2 = String.valueOf(new char[] { c1, c2 });
			}
			if (map.containsKey(key2)) {
				sb.append(map.get(key2));
			} else if (map.containsKey(key1)) {
				sb.append(map.get(key1));
			} else {
				sb.append(c1);
			}
		}
		return sb.toString();
	}

	/**
	 * 全角カタカナ文字を半角カタカナ文字に変換を行う。
	 *
	 * @param prm 変換対象文字列
	 * @return 変換結果文字列
	 */
	public static String zenkakuKatakanaToHankakuKatakana(String prm) {
		return convert(prm, FULLWIDTH_KATAKANA_HALFWIDTH_KATAKANA_MAP);
	}

	/**
	 * 全角ひらがなをカタカナに変換を行う。
	 *
	 * @param prm 変換対象文字列
	 * @return 変換結果文字列
	 */
	public static String hiraganaToKatakana(String prm) {
		return convert(prm, HIRAGANA_KATAKANA_MAP);
	}

	/**
	 * 全角カタカナ文字を半角カタカナ文字に変換を行う。
	 *
	 * @param prm 変換対象文字列
	 * @return 変換結果文字列
	 */
	public static String toHankakuKatakana(String prm) {
		String str = prm;
		str = hiraganaToKatakana(str);
		str = zenkakuKatakanaToHankakuKatakana(str);
		return str;
	}

	/**
	 * 半角拗音・促音を大文字に変換を行う。
	 *
	 * @param str 変換対象文字列
	 * @return 変換結果文字列
	 */
	public static String toUpperSutegana(String str) {
		char[] c = str.toCharArray();
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < LOWER_SUTEGANA.length; j++) {
				if (c[i] == LOWER_SUTEGANA[j]) {
					sb.append(UPPER_SUTEGANA[j]);
					break;
				} else if (j == LOWER_SUTEGANA.length - 1) {
					sb.append(c[i]);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * URLからパラメータ値を取得する。
	 *
	 * @param url    URL
	 * @param pramNm パラメータ名
	 * @return パラメータ値
	 */
	public static String getPramValueFromUrl(String url, String pramNm) {
		if (!isNullOrEmpty(url) && !isNullOrEmpty(pramNm)) {
			String[] pramater = url.split(Pattern.quote("?"));
			if (pramater != null && pramater.length > 1) {
				String[] prams = pramater[1].split(Pattern.quote("&"));
				for (int index = 0; index < prams.length; index++) {
					if (prams[index].startsWith(pramNm + "=") && prams[index].split(Pattern.quote("=")).length > 1) {
						return prams[index].split(Pattern.quote("="))[1];
					}
				}
			}
		}
		return "";
	}
}
