package com.api.docsen.dao;


import com.api.docsen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    //Query("SELECT u FROM User u JOIN u.role r WHERE r.name =:name")
    public User findByUsername(String username);
    public User findByEmail(String mail);
    public User findUserByUsernameOrEmail(String username, String Email);
}
