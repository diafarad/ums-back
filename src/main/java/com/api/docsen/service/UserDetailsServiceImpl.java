package com.api.docsen.service;

import com.api.docsen.dao.RoleRepository;
import com.api.docsen.dao.UserRepository;
import com.api.docsen.model.Role;
import com.api.docsen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user != null)
        {

            /*final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(user.getUsername());
            String ls =  userDetails.getAuthorities().stream()
                    .map(u->((GrantedAuthority) u)
                            .getAuthority()).collect(Collectors.toList()).get(0);
            Role r = roleRepository.findByName((RoleName.valueOf(ls)));
*/
            ArrayList<Role> l = new ArrayList<>();
            l.add(user.getRole());
            org.springframework.security.core.userdetails.User u =
                    new org.springframework.security.core.userdetails.
                            User(user.getUsername(),user.getPassword(),
                    true,true,
                            true,true,
                            getAuthorities(l));
            return u ;
        }

        return null;
    }

    private Collection getAuthorities(List roles) {
        List authorities = new ArrayList();
        for(Object role : roles)
        {
            Role l = (Role)role;
            authorities.add(new SimpleGrantedAuthority(l.getName().name()));
        }
        return authorities ;
    }
}
