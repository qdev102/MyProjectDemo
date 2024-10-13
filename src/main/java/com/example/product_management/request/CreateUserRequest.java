package com.example.product_management.request;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
@Data
public class CreateUserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
