package com.flamexander.springmarket.services;

import com.flamexander.springmarket.entities.SystemUser;
import com.flamexander.springmarket.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    boolean save(SystemUser systemUser);
}
