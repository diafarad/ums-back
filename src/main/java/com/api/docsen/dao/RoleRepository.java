package com.api.docsen.dao;


import com.api.docsen.model.Role;
import com.api.docsen.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(RoleName name);

}
