package com.geekbrains.septembermarket.repositories;

import com.geekbrains.septembermarket.entities.Role;
import com.geekbrains.septembermarket.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByPhone(String phone);
    boolean existsByPhone(String phone);
    List<User> findAllByRolesContains(Role role);
}
