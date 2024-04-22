package jp.co.ht.common.annotation;

import javax.validation.*;
import jp.co.ht.common.annotation.validator.RequiredValidator;
import jp.co.ht.common.constant.CommonConst;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Validator for checking mandatory input.
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredValidator.class)
public @interface Required {

    String message() default RequiredValidator.MESSAGE;

    Class<?>[] groups() default {};

    String label() default "";

    String pattern() default CommonConst.YYYYMMDD;

    Class<? extends Payload>[] payload() default {};
}
