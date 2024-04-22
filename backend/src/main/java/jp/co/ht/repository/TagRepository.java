package jp.co.ht.repository;

import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jp.co.ht.model.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	@Query(value = "SELECT t.* FROM {h-schema}tag t INNER JOIN {h-schema}customer_tag ct ON t.id = ct.tag_id AND t.valid_flag = ct.valid_flag WHERE ct.customer_id = ?1 AND t.valid_flag = ?2", nativeQuery = true)
	public List<Tag> findByCustomerIdAndValidFlag(Long customerId, Short validFlag);

	@Query(value = "SELECT * FROM {h-schema}tag WHERE LOWER(tag_name) LIKE ?1 AND valid_flag = ?2 ORDER BY update_datetime DESC LIMIT ?3", nativeQuery = true)
	public List<Tag> findByTagNameAndLimit(String tagName, Short validFlag, Short limit);
	
	@Query(value = "SELECT ct.customer_id, ct.tag_id, t.tag_name FROM {h-schema}tag t INNER JOIN {h-schema}customer_tag ct ON t.id = ct.tag_id AND t.valid_flag = ct.valid_flag WHERE ct.customer_id IN ?1 AND t.valid_flag = ?2", nativeQuery = true)
	public List<Tuple> findTagsByCustomerIds(List<Long> customerIds, Short validFlag);

}
