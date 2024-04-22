package jp.co.ht.common.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import jp.co.ht.common.annotation.Required;

/**
 * RequiredValidator
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {
	
	public static final String MESSAGE = "{error.required}";

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return false;
        }
        return !(object instanceof String) || !object.toString().trim().isEmpty();
    }
}

