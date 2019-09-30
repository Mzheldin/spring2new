package com.flamexander.springmarket.repositories;

import com.flamexander.springmarket.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findOneByName(String theRoleName);
}
