package com.flamexander.springmarket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "temp_users")
public class TempUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    public TempUser(String userName, String email, String phone, String address) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
