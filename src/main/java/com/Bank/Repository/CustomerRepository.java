package com.Bank.Repository;

import com.Bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Long> {

     boolean  existsByUser_Username(String username);

     Optional<Customer> findByUser_Username(String username);
}
