package jp.co.ht.repository.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import jp.co.ht.common.constant.CommonConst;
import jp.co.ht.common.utils.StringUtil;
import jp.co.ht.model.common.BasePageRequest.SortRequest;
import jp.co.ht.model.entity.Customer;
import jp.co.ht.model.request.customer.CustomerSearchRequest;
import jp.co.ht.repository.custom.CustomerRepositoryCustom;

public class CustomerRepositoryImpl extends BaseRepositoryImpl implements CustomerRepositoryCustom {
	
	private final List<String> SORT_COLUMNS = Arrays.asList("id", "customer_name", "insert_datetime", "update_datetime");

	@SuppressWarnings("unchecked")
	@Override
	public Page<Customer> search(CustomerSearchRequest customerSearchRequest) {
		List<Object> listParams = new ArrayList<Object>();
		int pageSize = customerSearchRequest.getPageSize();
		if (pageSize == 0) {
			pageSize = CommonConst.DEFAULT_PAGE_SIZE;
		}
		int pageNum = customerSearchRequest.getPageNum();
		int offset = pageNum * pageSize;
		List<Customer> customers;
		// select count for paging
		String countQueryString = "SELECT COUNT(*) FROM {h-schema}customer c";
		StringBuilder whereString = buildWhereClauseAndParams(customerSearchRequest, listParams);

		Query query = entityManager.createNativeQuery(countQueryString + whereString.toString());
		if (listParams.size() > 0) {
			for (int i = 1; i <= listParams.size(); i++) {
				query.setParameter(i, listParams.get(i - 1));
			}
		}
		BigInteger countResult = (BigInteger) query.getSingleResult();
		long total = countResult.longValue();
		if (total == 0) {
			customers = new ArrayList<Customer>();
			return createPageable(customers, pageNum, pageSize, 0);
		}

		String selectQueryString = "SELECT c.id, c.customer_name, c.insert_datetime, c.update_datetime, c.valid_flag FROM {h-schema}customer c";
		// check sort
		if (customerSearchRequest.getSort() != null) {
			SortRequest sortOrder = customerSearchRequest.getSort();
			if (StringUtil.isNotEmpty(sortOrder.getProperty()) && SORT_COLUMNS.contains(sortOrder.getProperty())) {
				whereString.append(" ORDER BY c." + sortOrder.getProperty());
			} else {
				whereString.append(" ORDER BY c.id");
			}
			if (sortOrder.getAsc() != null && sortOrder.getAsc()) {
				whereString.append(" ASC");
			} else {
				whereString.append(" DESC");
			}
		}
		whereString.append(" LIMIT " + pageSize);
		whereString.append(" OFFSET " + offset);
		query = entityManager.createNativeQuery(selectQueryString + whereString.toString(), Customer.class);
		if (listParams.size() > 0) {
			for (int i = 1; i <= listParams.size(); i++) {
				query.setParameter(i, listParams.get(i - 1));
			}
		}
		customers = query.getResultList();
		return createPageable(customers, pageNum, pageSize, total);
	}

	private StringBuilder buildWhereClauseAndParams(CustomerSearchRequest customerSearchRequest,
			List<Object> listParams) {
		int paramIndex = 1;
		StringBuilder whereString = new StringBuilder(" WHERE c.valid_flag = " + CommonConst.VALID_DATA_FLAG);
		if (StringUtil.isNotEmpty(customerSearchRequest.getCustomerName())) {
			whereString.append(" AND LOWER(c.customer_name) LIKE ?" + (paramIndex++));
			listParams.add(StringUtil.getFullSearchLikeParam(customerSearchRequest.getCustomerName()));
		}
		if (customerSearchRequest.getTagIds() != null && customerSearchRequest.getTagIds().size() > 0) {
			whereString.append(" AND c.id IN (");
			whereString.append(" SELECT ct.customer_id FROM {h-schema}customer_tag ct");
			whereString.append(" INNER JOIN {h-schema}tag t ON ct.tag_id = t.id AND ct.valid_flag = " + CommonConst.VALID_DATA_FLAG);
			whereString.append(" WHERE t.id IN ?" + (paramIndex++) + " AND t.valid_flag = " + CommonConst.VALID_DATA_FLAG);
			whereString.append(")");
			listParams.add(customerSearchRequest.getTagIds());
		}
		if (StringUtil.isNotEmpty(customerSearchRequest.getInsertDateFrom())) {
			whereString.append(" AND TO_CHAR(c.insert_datetime,'" + CommonConst.YYYYMMDD + "') >= ?"+ (paramIndex++));
			listParams.add(customerSearchRequest.getInsertDateFrom());
		}
		if (StringUtil.isNotEmpty(customerSearchRequest.getInsertDateTo())) {
			whereString.append(" AND TO_CHAR(c.insert_datetime,'" + CommonConst.YYYYMMDD + "') <= ?" + (paramIndex++));
			listParams.add(customerSearchRequest.getInsertDateTo());
		}
		if (StringUtil.isNotEmpty(customerSearchRequest.getUpdateDateFrom())) {
			whereString.append(" AND TO_CHAR(c.update_datetime,'" + CommonConst.YYYYMMDD + "') >= ?" + (paramIndex++));
			listParams.add(customerSearchRequest.getUpdateDateFrom());
		}
		if (StringUtil.isNotEmpty(customerSearchRequest.getUpdateDateTo())) {
			whereString.append(" AND TO_CHAR(c.update_datetime,'" + CommonConst.YYYYMMDD + "') <= ?" + (paramIndex++));
			listParams.add(customerSearchRequest.getUpdateDateTo());
		}
		return whereString;
	}
}
