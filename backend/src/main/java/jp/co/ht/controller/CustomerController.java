package jp.co.ht.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.co.ht.model.common.CommonPage;
import jp.co.ht.model.common.CommonResult;
import jp.co.ht.common.annotation.validator.ValidateInfo;
import jp.co.ht.common.constant.CommonConst;
import jp.co.ht.model.entity.Customer;
import jp.co.ht.model.request.customer.CustomerRegistRequest;
import jp.co.ht.model.request.customer.CustomerSearchRequest;
import jp.co.ht.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value="/search")
    public CommonResult<CommonPage<Customer>> search(@RequestBody CustomerSearchRequest customerSearchRequest){
    	List<ValidateInfo> errorList = super.validator.validate(customerSearchRequest);
		StringBuilder errorMessage = new StringBuilder();
		if (!errorList.isEmpty()) {
			errorList.forEach(info -> errorMessage.append(info.getMsg()).append(CommonConst.VERTICAL_BAR));
			if (errorMessage.indexOf(CommonConst.VERTICAL_BAR) > 0) {
                return CommonResult.validateFailed(errorMessage.toString().substring(0, errorMessage.length() - 1));
            }
            return CommonResult.validateFailed(errorMessage.toString());
		}
    	Page<Customer> customerList = customerService.search(customerSearchRequest);
        return CommonResult.success(CommonPage.restPage(customerList));
    }
    
    @PostMapping()
    public CommonResult<Long> regist(@RequestBody CustomerRegistRequest customerRegistRequest) {
    	List<ValidateInfo> errorList = super.validator.validate(customerRegistRequest);
        if (!errorList.isEmpty()) {
            var errorMessage = new StringBuilder();
            errorList.forEach(info -> errorMessage.append(info.getMsg()).append(CommonConst.VERTICAL_BAR));
            if (errorMessage.indexOf(CommonConst.VERTICAL_BAR) > 0) {
                return CommonResult.validateFailed(errorMessage.toString().substring(0, errorMessage.length() - 1));
            }
            return CommonResult.validateFailed(errorMessage.toString());
        }
        Long customerId = customerService.regist(customerRegistRequest);
    	return CommonResult.success(customerId);
    }
    
    @PutMapping(value="/{customerId}")
    public CommonResult<Long> update(@PathVariable(value="customerId") Long id, @RequestBody CustomerRegistRequest customerRegistRequest) {
    	List<ValidateInfo> errorList = super.validator.validate(customerRegistRequest);
        if (!errorList.isEmpty()) {
            var errorMessage = new StringBuilder();
            errorList.forEach(info -> errorMessage.append(info.getMsg()).append(CommonConst.VERTICAL_BAR));
            if (errorMessage.indexOf(CommonConst.VERTICAL_BAR) > 0) {
                return CommonResult.validateFailed(errorMessage.toString().substring(0, errorMessage.length() - 1));
            }
            return CommonResult.validateFailed(errorMessage.toString());
        }
        Long customerId = customerService.update(id, customerRegistRequest);
    	return CommonResult.success(customerId);
    }
    
    @GetMapping(value="/{customerId}")
    public CommonResult<Customer> detail(@PathVariable(value="customerId") Long id) {
    	Customer customer = customerService.detail(id);
        return CommonResult.success(customer);
    }
    
    @DeleteMapping(value="/{customerId}")
    public CommonResult<Boolean> invalid(@PathVariable(value="customerId") Long id) {
    	Boolean result = customerService.invalid(id);
        return CommonResult.success(result);
    }
}
