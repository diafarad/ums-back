package com.api.docsen.dao;

import com.api.docsen.model.Admin;
import com.api.docsen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin getAdminByUserUsername(String username);
}
