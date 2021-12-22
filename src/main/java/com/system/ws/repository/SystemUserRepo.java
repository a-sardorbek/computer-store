package com.system.ws.repository;

import com.system.ws.domain.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepo extends JpaRepository<SystemUser,Long> {
    SystemUser findUserByUsername(String username);
}
