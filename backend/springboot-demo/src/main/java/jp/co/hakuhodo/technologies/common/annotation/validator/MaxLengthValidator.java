package jp.co.hakuhodo.technologies.common.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import jp.co.hakuhodo.technologies.common.annotation.MaxLength;
import jp.co.hakuhodo.technologies.common.utils.StringUtil;

/**
 * @author DTVN
 */
public class MaxLengthValidator  implements ConstraintValidator<MaxLength, Object> {
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

        throw new ValidationException("バリデーション対象に想定外の型が指定されています。");
    }
}

