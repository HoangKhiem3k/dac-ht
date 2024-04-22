package jp.co.hakuhodo.technologies.common.annotation.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.hakuhodo.technologies.dto.common.ValidateInfo;

@Component
public class CommonValidator {
    // バリデーション
    @Autowired
    private Validator validator;

    public List<ValidateInfo> validate(Object bean, Class<?>... groups){
        Set<ConstraintViolation<Object>> constraintViolationSet=validator.validate(bean, groups);
        List<ValidateInfo> errorList = new ArrayList<>();
        if(!constraintViolationSet.isEmpty()){
            constraintViolationSet.forEach(constraintViolation->{
                ValidateInfo validateInfo = new ValidateInfo();
                validateInfo.setMsg(constraintViolation.getMessage());
                validateInfo.setFieldName(constraintViolation.getPropertyPath().toString());
                errorList.add(validateInfo);
            });
        }
        return errorList;
    }
}
