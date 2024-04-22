package jp.co.ht.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.ht.model.entity.Customer;
import jp.co.ht.repository.custom.CustomerRepositoryCustom;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

}
