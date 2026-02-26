package com.comarch.szkolenia.rest.database.impl;

import com.comarch.szkolenia.rest.database.CustomerRepository;
import com.comarch.szkolenia.rest.model.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();
    private int lastId = 0;

    public CustomerRepositoryImpl() {
        seedCustomers();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(this.customers);
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        if (id < 0) {
            return Optional.empty();
        }
        return this.customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<Customer> updateCustomer(int id, Customer customer) {
        int index = findIndexById(id);
        if (index < 0) {
            return Optional.empty();
        }
        customer.setId(id);
        this.customers.set(index, customer);
        return Optional.of(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        int index = findIndexById(id);
        if (index < 0) {
            return;
        }
        this.customers.remove(index);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        customer.setId(++lastId);
        this.customers.add(customer);
        return customer;
    }

    @Override
    public void deleteCustomerByDomain(String domain) {
        if (domain == null || domain.isBlank()) {
            return;
        }
        String normalizedDomain = normalizeDomain(domain);
        this.customers.removeIf(customer -> hasDomain(customer, normalizedDomain));
    }

    private void seedCustomers() {
        this.customers.add(new Customer(++lastId, "Jan", "Kowalski", "jan.kowalski@example.com", "pass1", "500100200", LocalDateTime.of(1985, 3, 12, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Anna", "Nowak", "anna.nowak@example.com", "pass2", "500100201", LocalDateTime.of(1990, 7, 5, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Piotr", "Wisniewski", "piotr.wisniewski@acme.com", "pass3", "500100202", LocalDateTime.of(1979, 11, 23, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Kasia", "Zielinska", "kasia.zielinska@acme.com", "pass4", "500100203", LocalDateTime.of(1995, 1, 17, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Tomasz", "Wojcik", "tomasz.wojcik@corp.pl", "pass5", "500100204", LocalDateTime.of(1988, 9, 9, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Ewa", "Kaczmarek", "ewa.kaczmarek@corp.pl", "pass6", "500100205", LocalDateTime.of(1992, 6, 30, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Marek", "Mazur", "marek.mazur@demo.org", "pass7", "500100206", LocalDateTime.of(1983, 4, 2, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Alicja", "Krawczyk", "alicja.krawczyk@demo.org", "pass8", "500100207", LocalDateTime.of(1998, 12, 14, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Pawel", "Dabrowski", "pawel.dabrowski@test.net", "pass9", "500100208", LocalDateTime.of(1975, 8, 26, 0, 0), Collections.EMPTY_LIST));
        this.customers.add(new Customer(++lastId, "Magda", "Lewandowska", "magda.lewandowska@test.net", "pass10", "500100209", LocalDateTime.of(1987, 2, 8, 0, 0), Collections.EMPTY_LIST));
    }

    private int findIndexById(int id) {
        for (int i = 0; i < this.customers.size(); i++) {
            if (this.customers.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private boolean hasDomain(Customer customer, String normalizedDomain) {
        if (customer == null || customer.getEmail() == null) {
            return false;
        }
        String email = customer.getEmail();
        int atIndex = email.indexOf('@');
        if (atIndex < 0 || atIndex == email.length() - 1) {
            return false;
        }
        String emailDomain = email.substring(atIndex + 1).trim().toLowerCase();
        return emailDomain.equals(normalizedDomain);
    }

    private String normalizeDomain(String domain) {
        String normalized = domain.trim().toLowerCase();
        if (normalized.startsWith("@")) {
            return normalized.substring(1);
        }
        return normalized;
    }
}
