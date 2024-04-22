package jp.co.ht.service.impl;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import jp.co.ht.common.constant.CommonConst;
import jp.co.ht.common.utils.StringUtil;
import jp.co.ht.model.entity.Customer;
import jp.co.ht.model.entity.CustomerTag;
import jp.co.ht.model.entity.Tag;
import jp.co.ht.model.request.customer.CustomerRegistRequest;
import jp.co.ht.model.request.customer.CustomerSearchRequest;
import jp.co.ht.repository.CustomerRepository;
import jp.co.ht.repository.CustomerTagRepository;
import jp.co.ht.repository.TagRepository;
import jp.co.ht.service.CustomerService;

@Service
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private CustomerTagRepository customerTagRepository;

	@Override
	public Page<Customer> search(CustomerSearchRequest customerSearchRequest) {
		Page<Customer> pageCustomers = customerRepository.search(customerSearchRequest);
		if (pageCustomers.isEmpty()) {
			return pageCustomers;
		}
		List<Long> customerIds = pageCustomers.getContent().stream().map(Customer::getId).collect(Collectors.toList());
		// get list tags is assigned by list customer ids then set to each customer object
		// instead of loop and find tags by each customer id
		List<Tuple> listCustomerTag = tagRepository.findTagsByCustomerIds(customerIds, CommonConst.VALID_DATA_FLAG);
		if (listCustomerTag.isEmpty()) {
			return pageCustomers;
		}
		Map<Long, List<Tag>> mapTag = new HashMap<Long, List<Tag>>();
		List<Tag> listTag;
		for (Tuple tuple : listCustomerTag) {
			BigInteger customerId = (BigInteger) tuple.get(0);
			BigInteger tagId = (BigInteger) tuple.get(1);
			String tagName = (String) tuple.get(2);
			if (mapTag.containsKey(customerId.longValue())) {
				listTag = mapTag.get(customerId.longValue());
			} else {
				listTag = new ArrayList<Tag>();
			}
			listTag.add(new Tag(tagId.longValue(), tagName));
			mapTag.put(customerId.longValue(), listTag);
		}
		for (Customer customer : pageCustomers.getContent()) {
			if (mapTag.containsKey(customer.getId())) {
				customer.setTags(mapTag.get(customer.getId()));
			} else {
				customer.setTags(Collections.emptyList());
			}
		}
		return pageCustomers;
	}

	@Transactional
	@Override
	public Long regist(CustomerRegistRequest customerRegistRequest) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerRegistRequest, customer);
		customer = customerRepository.save(customer);

		List<Tag> listTag = customerRegistRequest.getTags();
		if (listTag == null || listTag.size() == 0) {
			return customer.getId();
		}
		List<CustomerTag> listCustomerTag = new ArrayList<CustomerTag>();
		for (Tag tag : listTag) {
			Long tagId = tag.getId();
			// if tag has id value is null or zero then insert new tag to tag table
			if (tagId == null || tagId == CommonConst.ZERO_VALUE) {
				if (StringUtil.isEmpty(tag.getTagName())) {
					continue;
				}
				tagId = tagRepository.save(tag).getId();
			}
			CustomerTag customerTag = new CustomerTag();
			customerTag.setCustomerId(customer.getId());
			customerTag.setTagId(tagId);
			listCustomerTag.add(customerTag);
		}
		if (listCustomerTag.size() > 0) {
			customerTagRepository.saveAll(listCustomerTag);
		}
		return customer.getId();
	}

	@Transactional
	@Override
	public Long update(Long cutomerId, CustomerRegistRequest customerRegistRequest) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerRegistRequest, customer);
		customer.setId(cutomerId);
		customer.setValidFlag(CommonConst.VALID_DATA_FLAG);
		customer = customerRepository.save(customer);

		List<Tag> listTagRequest = customerRegistRequest.getTags();
		if (listTagRequest == null) {
			listTagRequest = new ArrayList<Tag>();
		}
		// get list new tags from request params
		List<Tag> listNewTags = listTagRequest.stream()
				.filter(tagReq -> (tagReq.getId() == null || tagReq.getId() == CommonConst.ZERO_VALUE))
				.collect(Collectors.toList());

		// get list insert/update tags to customer_tag table from request params
		List<Tag> listRequestUpdates = listTagRequest.stream()
				.filter(tagReq -> (tagReq.getId() != null && tagReq.getId() != CommonConst.ZERO_VALUE))
				.collect(Collectors.toList());

		// save list new tags to tag table
		listNewTags = tagRepository.saveAll(listNewTags);

		// compare existing tags assigned to customer from DB and list insert/update tags from request params
		// to identify which is insert/update/logical delete
		List<CustomerTag> listCustomerTags = customerTagRepository.findByCustomerId(cutomerId);
		// list update customer tags, it will change invalid (valid_flag = 0) to valid (valid_flag = 1)
		List<CustomerTag> listUpdate = listCustomerTags.stream()
				.filter(cusTag -> listRequestUpdates.stream().anyMatch(
						req -> cusTag.getTagId().equals(req.getId()) && cusTag.getValidFlag().equals(CommonConst.INVALID_DATA_FLAG)))
				.collect(Collectors.toList());
		// list logical delete customer tags (exist in DB but not exist in request params)
		List<CustomerTag> listDelete = listCustomerTags.stream()
				.filter(cusTag -> !listRequestUpdates.stream().anyMatch(req -> cusTag.getTagId().equals(req.getId())))
				.collect(Collectors.toList());
		// list insert to customer tags (exist in request params but not exist in DB)
		List<Tag> listInsert = listRequestUpdates.stream()
				.filter(req -> !listCustomerTags.stream().anyMatch(cusTag -> cusTag.getTagId().equals(req.getId())))
				.collect(Collectors.toList());

		// save customer tags
		List<CustomerTag> listSaveCustomerTags = new ArrayList<CustomerTag>();
		listInsert.addAll(listNewTags);
		for (Tag tag : listInsert) {
			CustomerTag customerTag = new CustomerTag();
			customerTag.setCustomerId(cutomerId);
			customerTag.setTagId(tag.getId());
			listSaveCustomerTags.add(customerTag);
		}
		for (CustomerTag customerTag : listUpdate) {
			customerTag.setValidFlag(CommonConst.VALID_DATA_FLAG);
			listSaveCustomerTags.add(customerTag);
		}
		for (CustomerTag customerTag : listDelete) {
			customerTag.setValidFlag(CommonConst.INVALID_DATA_FLAG);
			listSaveCustomerTags.add(customerTag);
		}
		if (listSaveCustomerTags.size() > 0) {
			customerTagRepository.saveAll(listSaveCustomerTags);
		}
		return customer.getId();
	}

	@Override
	public Customer detail(Long cutomerId) {
		Customer customer = customerRepository.findById(cutomerId).orElse(null);
		if (customer != null) {
			List<Tag> listTag = tagRepository.findByCustomerIdAndValidFlag(customer.getId(), CommonConst.VALID_DATA_FLAG);
			if (listTag != null) {
				customer.setTags(listTag);
			} else {
				customer.setTags(Collections.emptyList());
			}
		}
		return customer;
	}

	@Transactional
	@Override
	public Boolean invalid(Long cutomerId) {
		Customer customer = customerRepository.findById(cutomerId).orElse(null);
		if (customer == null) {
			return false;
		}
		customer.setValidFlag(CommonConst.INVALID_DATA_FLAG);
		customerRepository.save(customer);
		// logical delete customer tags
		List<CustomerTag> listCustomerTag = customerTagRepository.findByCustomerId(customer.getId());
		List<CustomerTag> listCustomerTagUpdate = listCustomerTag.stream()
				.filter(cusTag -> cusTag.getValidFlag().equals(CommonConst.VALID_DATA_FLAG)).collect(Collectors.toList());
		if (listCustomerTagUpdate.size() > 0) {
			for(CustomerTag customerTag : listCustomerTagUpdate) {
				customerTag.setValidFlag(CommonConst.INVALID_DATA_FLAG);
			}
			customerTagRepository.saveAll(listCustomerTagUpdate);
		}
		return true;
	}
}
