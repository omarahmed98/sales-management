package com.vendor.system.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "LastName cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Mobile cannot be empty")
    @Pattern(regexp="\\+?[0-9]{10,15}", message = "Invalid mobile number")
    @Column(name = "mobile")
    private String mobile;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Address cannot be empty")
    @Column(name = "address")
    private String address;

}

