package jp.co.hakuhodo.technologies.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.hakuhodo.technologies.dto.request.customer.CustomerRegistRequest;
import jp.co.hakuhodo.technologies.entity.Customer;
import jp.co.hakuhodo.technologies.repository.CustomerRepository;
import jp.co.hakuhodo.technologies.service.ICustomerService;


@Service
public class CustomerServiceImpl extends BaseServiceImpl implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	@Override
	public Long save(CustomerRegistRequest customerRegistRequest) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customer, customerRegistRequest);
		customer = customerRepository.save(customer);
		return customer.getId();
	}
}
