package com.comarch.szkolenia.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tcustomer")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String email;
    //@JsonIgnore
    private String password;
    private String phone;
    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateOfBirth;
    @OneToMany(fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Address> addresses;

    public Customer(Integer id) {
        this.id = id;
    }
}
