package jp.co.ht.repository.custom;

import org.springframework.data.domain.Page;
import jp.co.ht.model.entity.Customer;
import jp.co.ht.model.request.customer.CustomerSearchRequest;

public interface CustomerRepositoryCustom {
	
	public Page<Customer> search(CustomerSearchRequest customerSearchRequest);
}
