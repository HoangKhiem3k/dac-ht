package jp.co.ht.service;

import org.springframework.data.domain.Page;
import jp.co.ht.model.entity.Customer;
import jp.co.ht.model.request.customer.CustomerRegistRequest;
import jp.co.ht.model.request.customer.CustomerSearchRequest;

public interface CustomerService {
	
	Page<Customer> search(CustomerSearchRequest customerSearchRequest);

    Long regist(CustomerRegistRequest customerRegistRequest);
    
    Long update(Long cutomerId, CustomerRegistRequest customerRegistRequest);
    
    Customer detail(Long cutomerId);
    
    Boolean invalid(Long cutomerId);
}
