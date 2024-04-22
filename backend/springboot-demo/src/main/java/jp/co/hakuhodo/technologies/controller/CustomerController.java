package jp.co.hakuhodo.technologies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.hakuhodo.technologies.common.api.CommonResult;
import jp.co.hakuhodo.technologies.common.constant.CommonConst;
import jp.co.hakuhodo.technologies.common.exeption.BusinessException;
import jp.co.hakuhodo.technologies.dto.common.ValidateInfo;
import jp.co.hakuhodo.technologies.dto.request.customer.CustomerRegistRequest;
import jp.co.hakuhodo.technologies.entity.Customer;
import jp.co.hakuhodo.technologies.service.ICustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends BaseController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping(value="/get-all")
    public String getCustomer() {
        List<Customer> customerList = customerService.getAll();
        return "OK " + customerList.size();
    }

    @PostMapping(value="/regist-customer")
    public CommonResult<String> registCustomer(@RequestBody CustomerRegistRequest customerRegistRequest) throws BusinessException {
        List<ValidateInfo> errorList = super.validator.validate(customerRegistRequest);
        // Check error
        if (!errorList.isEmpty()) {
            var errorMessage = new StringBuilder();
            errorList.forEach(info -> errorMessage.append(info.getMsg()).append(CommonConst.VERTICAL_BAR));
            if (errorMessage.indexOf(CommonConst.VERTICAL_BAR) > 0) {
                return CommonResult.validateFailed(errorMessage.toString().substring(0, errorMessage.length() - 1));
            }
            // Return abnormal message
            return CommonResult.validateFailed(errorMessage.toString());
        }
        Long customerId = customerService.save(customerRegistRequest);
        return CommonResult.success(customerId.toString());
    }
}
