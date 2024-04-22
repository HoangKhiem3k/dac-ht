package jp.co.hakuhodo.technologies.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.hakuhodo.technologies.common.annotation.validator.MaxLengthValidator;

/**
 * 文字列長を検査するバリデーター. 指定桁数よりも大きい場合にエラーとする。<br>
 * ※使用可能フィールド：文字列　と　list<文字列>
 *
 * @author DTVN
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxLengthValidator.class)
public @interface MaxLength {
    /** エラーメッセージ. */
    String message() default MaxLengthValidator.MESSAGE;

    /** 項目名. */
    String label() default "";

    /** バリデーショングループ. */
    Class<?>[] groups() default {};

    /** ペイロード. */
    Class<? extends Payload>[] payload() default {};

    /** 最大桁数. */
    int val();
}
