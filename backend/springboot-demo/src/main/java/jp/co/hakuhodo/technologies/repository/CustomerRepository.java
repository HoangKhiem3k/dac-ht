package jp.co.hakuhodo.technologies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.hakuhodo.technologies.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
