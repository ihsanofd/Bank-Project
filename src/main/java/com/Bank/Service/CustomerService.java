package com.Bank.Service;

import com.Bank.Dto.CustomerRequest;
import com.Bank.Dto.CustomerResponse;
import com.Bank.Repository.CustomerRepository;
import com.Bank.Repository.UserRepository;
import com.Bank.model.Customer;
import com.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    public CustomerResponse addCustomer(CustomerRequest request) {


        if (customerRepository.existsByUser_Username(request.getUsername())) {
            throw new IllegalArgumentException("Customer profile already exists for this user");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));


        Customer customer = new Customer();

        customer.setFullName(request.getFullName());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customer.setUser(user);
        Customer saved = customerRepository.save(customer);

        CustomerResponse dto = new CustomerResponse();
        dto.setUsername(saved.getUser().getUsername());
        dto.setFullName(saved.getFullName());
        dto.setPhone(saved.getPhone());
        dto.setAddress(saved.getAddress());

        return dto;
    }
}
