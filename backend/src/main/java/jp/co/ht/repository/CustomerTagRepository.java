package jp.co.ht.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.ht.model.entity.CustomerTag;

@Repository
public interface CustomerTagRepository extends JpaRepository<CustomerTag, Long> {
	
	public List<CustomerTag> findByCustomerId(Long customerId);
	
	public List<CustomerTag> findByCustomerIdAndTagId(Long customerId, Long tagId);
}
