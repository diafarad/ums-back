package com.api.docsen;


import com.api.docsen.dao.RoleRepository;
import com.api.docsen.model.Role;
import com.api.docsen.model.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocsenApplication /*implements CommandLineRunner*/ {

    public static void main(String[] args) {
        SpringApplication.run(DocsenApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {
        Role r = roleRepository.findByName(RoleName.ROLE_USER);
        System.out.println("Id : " + r.getId() + "Role :" + r.getName());
        //System.out.println(encoder.encode("passer"));
    }*/
}
