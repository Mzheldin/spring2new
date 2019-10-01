package com.flamexander.springmarket.repositories;

import com.flamexander.springmarket.entities.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempUserRepository extends JpaRepository<TempUser, Long> {
}
