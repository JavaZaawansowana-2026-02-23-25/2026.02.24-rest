package com.comarch.szkolenia.rest.controllers.rest.api.v1;

import com.comarch.szkolenia.rest.database.CustomerRepository;
import com.comarch.szkolenia.rest.model.Customer;
import com.comarch.szkolenia.rest.model.dto.ListWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/customer")
public class CustomerRestController {
    private final CustomerRepository customerRepository;

    @GetMapping()
    public ListWrapper<Customer> getAllCustomers() {
        return new ListWrapper<>(this.customerRepository.getAllCustomers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        return this.customerRepository.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Customer> updateCustomer(@PathVariable("id") int id,
                                                    @RequestBody Customer customer) {
        return this.customerRepository.updateCustomer(id, customer)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") int id) {
        this.customerRepository.deleteCustomer(id);
    }

    @PostMapping()
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(this.customerRepository.addCustomer(customer));
    }

    @DeleteMapping()
    public void deleteCustomerByDomain(@RequestParam("domain") String domain) {
        this.customerRepository.deleteCustomerByDomain(domain);
    }
}
