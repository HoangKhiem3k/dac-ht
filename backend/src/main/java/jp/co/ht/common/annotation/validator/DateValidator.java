package jp.co.ht.common.annotation.validator;

import java.text.SimpleDateFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import jp.co.ht.common.annotation.DateFormat;

/**
 * DateValidator
 */
public class DateValidator implements ConstraintValidator<DateFormat, Object> {
	
	public static final String MESSAGE = "{error.dateformat}";

    private String pattern;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            String inputStr = object.toString();
            if (inputStr.isEmpty()) {
                return true;
            }
            if (pattern.length() != inputStr.length()) {
                return false;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat();
                sdf.applyPattern(this.pattern);
                sdf.setLenient(false);
                sdf.parse(inputStr);
            } catch (java.text.ParseException e) {
            	e.printStackTrace();
                return false;
            }
            return true;
        }

        throw new ValidationException();
    }
}

