package jp.co.hakuhodo.technologies.service;

import java.util.List;

import jp.co.hakuhodo.technologies.dto.request.customer.CustomerRegistRequest;
import jp.co.hakuhodo.technologies.entity.Customer;

public interface ICustomerService {

    List<Customer> getAll();

    Long save(CustomerRegistRequest customerRegistRequest);
}
