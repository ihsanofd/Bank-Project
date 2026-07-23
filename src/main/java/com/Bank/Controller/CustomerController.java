package com.Bank.Controller;

import com.Bank.Dto.CustomerRequest;
import com.Bank.Dto.CustomerResponse;
import com.Bank.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse customerResponse=customerService.addCustomer(request);
        return new ResponseEntity<>(customerResponse , HttpStatus.CREATED);
    }

}
