package com.comarch.szkolenia.rest.database;

import com.comarch.szkolenia.rest.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(int id);
    Optional<Customer> updateCustomer(int id, Customer customer);
    void deleteCustomer(int id);
    Customer addCustomer(Customer customer);
    void deleteCustomerByDomain(String domain);
}
