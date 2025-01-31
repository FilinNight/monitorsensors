package com.agsr.monitorsensors.repository;

import com.agsr.monitorsensors.model.Role;
import com.agsr.monitorsensors.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(RoleType type);
}

