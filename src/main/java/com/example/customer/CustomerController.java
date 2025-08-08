package com.example.customer;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerIdSequenceService sequenceService;

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        // Generate business customerId
        String today = sequenceService.getTodayString();
        long seq = sequenceService.getNextSequence();
        String customerId = String.format("CUST-%s-%05d", today, seq);
        customer.setCustomerId(customerId);
        return repository.save(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // Delete all customers
    @DeleteMapping
    public void deleteAllCustomers() {
        repository.deleteAll();
    }

    // Delete by customer ID (already present, but for clarity)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-customerId/{customerId}")
    public ResponseEntity<?> deleteCustomerByCustomerId(@PathVariable String customerId) {
        Optional<Customer> customerOpt = repository.findByCustomerId(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Customer not found"));
        }
        repository.delete(customerOpt.get());
        return ResponseEntity.ok(Map.of("message", "Customer deleted successfully"));
    }
}
