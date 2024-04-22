package jp.co.ht.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import jp.co.ht.common.annotation.validator.MaxLengthValidator;

/**
 * Validator for checking max length of input text
 **/
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxLengthValidator.class)
public @interface MaxLength {
	
    String message() default MaxLengthValidator.MESSAGE;

    String label() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int val();
}
