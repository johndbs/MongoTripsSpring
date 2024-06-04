package com.thinkitdevit.mongotripsspring.services;

import com.thinkitdevit.mongotripsspring.mapper.CustomerMapper;
import com.thinkitdevit.mongotripsspring.models.Customer;
import com.thinkitdevit.mongotripsspring.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(ObjectId id) {
        return customerRepository.findById(id);
    }

    public void delete(ObjectId id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
