package jp.co.ht.common.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import jp.co.ht.common.annotation.MaxLength;
import jp.co.ht.common.utils.StringUtil;

/**
 * MaxLengthValidator
 */
public class MaxLengthValidator implements ConstraintValidator<MaxLength, Object> {
	
    public static final String MESSAGE = "{error.length.max}";
    
    private int val;

    public MaxLengthValidator() {
    }

    public MaxLengthValidator(int value) {
        this.val = value;
    }

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        this.val = constraintAnnotation.val();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            return (StringUtil.getLength(object.toString()) <= val);
        }

        throw new ValidationException("An unexpected type has been specified for validation.");
    }
}

