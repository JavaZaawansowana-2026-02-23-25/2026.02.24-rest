package com.comarch.szkolenia.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    //@JsonIgnore
    private String password;
    private String phone;

    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateOfBirth;
}
