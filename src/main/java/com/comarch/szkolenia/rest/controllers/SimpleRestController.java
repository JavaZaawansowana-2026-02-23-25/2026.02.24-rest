package com.comarch.szkolenia.rest.controllers;

import com.comarch.szkolenia.rest.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class SimpleRestController {


    @GetMapping("/test3")
    public Customer test3() {
        Customer customer = new Customer();
        customer.setName("Jan");
        customer.setSurname("Kowalski");
        customer.setEmail("jan.kowalski@gmail.com");
        customer.setPassword("password");
        customer.setPhone("123456789");
        customer.setDateOfBirth(LocalDateTime.now());

        return customer;
    }

    @PostMapping("/test4")
    public void test4(@RequestBody Customer customer) {
        System.out.println(customer);
    }

    @PostMapping("/test5/{x}/{y}")
    public Customer test5(@PathVariable("x") String x,
                          @PathVariable("y") String y,
                          @RequestParam("cos") int cos,
                          @RequestBody Customer customer,
                          @RequestHeader("header1") String header) {
        System.out.println(x);
        System.out.println(y);
        System.out.println(cos);
        System.out.println(customer);
        System.out.println(header);

        customer.setName("Jakies imie");
        return customer;
    }

    @GetMapping("/test6")
    public ResponseEntity<Customer> test6() {
        Customer customer = new Customer();
        customer.setName("Jan");
        customer.setSurname("Kowalski");
        customer.setEmail("jan.kowalski@gmail.com");
        customer.setPassword("password");
        customer.setPhone("123456789");
        customer.setDateOfBirth(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header("header1", "value1")
                .header("header2", "value2")
                .body(customer);
    }
}
