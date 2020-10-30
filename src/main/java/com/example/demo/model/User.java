package com.example.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Type username")
    private String username;
    @NotBlank(message = "Type password")
    private String password;
    @Email
    @NotEmpty(message = "Write email")
    private String email;
    private Instant created;
    private boolean enabled;

}
