package jp.co.ht.common.annotation;

import javax.validation.*;
import jp.co.ht.common.annotation.validator.DateValidator;
import jp.co.ht.common.constant.CommonConst;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Validator for checking the format of the input value. Generates an error if the date is invalid.
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface DateFormat {

    String message() default DateValidator.MESSAGE;

    Class<?>[] groups() default {};

    String label() default "";

    String pattern() default CommonConst.YYYYMMDD;

    Class<? extends Payload>[] payload() default {};
}
