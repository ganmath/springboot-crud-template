package com.example.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  @Autowired
  private CustomerRepository repository;

  @PostMapping
  public Customer save(@RequestBody Customer customer) {
    return repository.save(customer);
  }

  @GetMapping
  public List<Customer> getAll() {
    return repository.findAll();
  }
}